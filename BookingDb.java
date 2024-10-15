package Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class BookingDb{ 
    public static String displayTheatre()throws SQLException {
        StringBuilder sb = new StringBuilder();
        try{
            ResultSet rs = DatabaseConnection.getConnection().prepareStatement(Queries.display_theatre).executeQuery();
            while(rs.next()){
                sb.append("-------------------------------------------------\n");
                sb.append("Theatre Name       : ").append(rs.getString("Theatre")).append("\n");
                sb.append("Theatre Location   : ").append(rs.getString("Location")).append("\n");
                sb.append("Movie              : ").append(rs.getString("Movie")).append("\n");
                boolean isAc = rs.getBoolean("Type");
                sb.append("Type               : ").append(isAc ? "AC" : "Non-AC").append("\n");
                sb.append("-------------------------------------------------\n");
            }
        }catch (SQLException e) {
            System.out.println("Error occurred:"+e.getMessage());
        }
        return sb.toString();
    }
    public static String displayTheatresForMovie(int movieid) {
    StringBuilder result = new StringBuilder();
    try {
        PreparedStatement pst = DatabaseConnection.getConnection().prepareStatement(Queries.displaytheatre);
        pst.setInt(1, movieid);
        ResultSet rs = pst.executeQuery();
        boolean theatreFound = false;
        while (rs.next()) {
            theatreFound = true;
            result.append("-------------------------------------------------\n");
            result.append("Theatre ID     : ").append(rs.getInt("id")).append("\n");
            result.append("Theatre Name   : ").append(rs.getString("name")).append("\n");
            result.append("Location       : ").append(rs.getString("location")).append("\n");
            result.append("Capacity       : ").append(rs.getInt("capacity")).append("\n");
            boolean isAc = rs.getBoolean("isac");
            result.append("Type           : ").append(isAc ? "AC" : "Non-AC").append("\n");
            result.append("-------------------------------------------------\n");
        }
        if (!theatreFound) {
            result.append("No theatres available for the selected movie with enough seats.\n");
            return result.toString();
        }
    } catch (SQLException e) {
        result.append("Error occurred: ").append(e.getMessage()).append("\n");
    }

    return result.toString();
   }
    public static String displayMoviesInTheatre(String theatrename) {
    StringBuilder result = new StringBuilder();
    try {
        PreparedStatement pst = DatabaseConnection.getConnection().prepareStatement(Queries.displayMoviesInTheatre);
        pst.setString(1, theatrename);
        ResultSet rs = pst.executeQuery();
        boolean movieFound = false;
        while (rs.next()) {
            movieFound = true;
            result.append("-------------------------------------------\n");
            result.append("Movie        : ").append(rs.getString("Movie")).append("\n");
            result.append("Language     : ").append(rs.getString("Language")).append("\n");
            result.append("Duration     : ").append(rs.getLong("Duration")).append(" minutes\n");
            result.append("-------------------------------------------\n");
        }
        if (!movieFound) {
            result.append("No movies found for the selected theatre.\n");
        }

    } catch (SQLException e) {
        result.append("Error occurred: ").append(e.getMessage()).append("\n");
    }

    return result.toString();

    }
    public static void bookTicket(int userId, int theatreid, int count, int sessionid){
        int movieid = movie(theatreid);
        try {
            LocalDate today = LocalDate.now();
            java.sql.Date sqlDate = java.sql.Date.valueOf(today);
           PreparedStatement pst = DatabaseConnection.getConnection().prepareStatement(Queries.movieQuery);
            pst.setInt(1, movieid);
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) {
                System.out.println("Movie not found.");
                return;
            }
            pst = DatabaseConnection.getConnection().prepareStatement(Queries.theatreQuery);
            pst.setInt(1, theatreid);
            pst.setInt(2, movieid);
            rs = pst.executeQuery();
            if (!rs.next()){
                System.out.println("Theatre not found, or the selected movie is not showing at this session.");
                return;
            }
            int theatreCapacity = rs.getInt("capacity");
            int bookedSeats = rs.getInt("booked_seats");
            int remainingSeats = theatreCapacity - bookedSeats;
            if (remainingSeats < count) {
                System.out.println("Not enough seats available in the theatre.");
                return;
            }
            pst = DatabaseConnection.getConnection().prepareStatement(Queries.insertBookingQuery);
            pst.setInt(1, userId);
            pst.setInt(2, movieid);
            pst.setInt(3, theatreid);
            pst.setInt(4, count);
            pst.setInt(5, sessionid);
            pst.setDate(6, sqlDate);
            int rowsAffected = pst.executeUpdate();
                System.out.println("Booking successful! Tickets booked: " + count);
            } else {
                System.out.println("Error: Booking could not be processed.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while booking tickets: " + e.getMessage());
        }
    }
    public static String displayBookings(int userId) {
    StringBuilder result = new StringBuilder();
    try {
        PreparedStatement pst = DatabaseConnection.getConnection().prepareStatement(Queries.display_Bookings);
        pst.setInt(1, userId);
        ResultSet rs = pst.executeQuery();
        boolean bookingsFound = false;
        while (rs.next()) {
            bookingsFound = true;
            result.append("-------------------------------------------------\n");
            result.append("Booking ID        : ").append(rs.getInt("id")).append("\n");
            result.append("Movie Name        : ").append(rs.getString("movie_name")).append("\n");
            result.append("Theatre Name      : ").append(rs.getString("theatre_name")).append("\n");
            result.append("Theatre Location  : ").append(rs.getString("theatre_location")).append("\n");
            result.append("Tickets Booked    : ").append(rs.getInt("tickets_booked")).append("\n");
            result.append("Session Timing    : ").append(rs.getString("timing")).append("\n");
            result.append("Booking Date      : ").append(rs.getDate("booking_date")).append("\n");
            result.append("-------------------------------------------------\n");
        }

        if (!bookingsFound) {
            result.append("No bookings found for the user.\n");
        }

    } catch (SQLException e) {
        result.append("Error occurred while retrieving bookings: ").append(e.getMessage()).append("\n");
    }

    return result.toString();
  }
    public static int seatCapacity(int id){
        try {
            PreparedStatement pst = DatabaseConnection.getConnection().prepareStatement(Queries.seatCapacity);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("capacity");
            } else {
            System.out.println("No theatre found");
            }
        } catch (SQLException e) {
            System.out.println("Error during fetching ID.");
        }
        return 0;
    }
    public static int available_seat(int theatre_id, int session_id){
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Queries.current_seat_available);
            pst.setInt(1, theatre_id);
            pst.setInt(2, session_id);
            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } 
        }catch (SQLException e) {
            System.out.println("Error during fetching available seats.");
        }
        return 0;
    }
    public static int displayMovie(String location){
        try {
            if (location == null || location.trim().isEmpty()) {
                System.out.println("Location is invalid.");
                return 0; 
            }
            if (DatabaseConnection.getConnection() == null) {
                System.out.println("Error: Unable to connect to the database.");
                return 0; 
            }
            PreparedStatement pst = DatabaseConnection.getConnection().prepareStatement(Queries.displaymovieonloctaion);
            pst.setString(1, location);
            ResultSet rs = pst.executeQuery();
            boolean theatre = false;
            while (rs.next()) {
                theatre = true;
                System.out.println("-------------------------------------------------");
                System.out.println("Theatre ID     : " + rs.getInt("id"));
                System.out.println("Theatre Name   : " + rs.getString("name"));
                System.out.println("Movie          : " + rs.getString("movie"));
                System.out.println("Location       : " + rs.getString("location"));
                System.out.println("Capacity       : " + rs.getInt("capacity"));
                boolean isAc = rs.getBoolean("isac");
                System.out.println("Type           : " + (isAc ? "AC" : "Non-AC"));
                System.out.println("-------------------------------------------------");
            }
            if (!theatre) {
                System.out.println("No theatres available for the selected location.");
                return 0; 
            }
        } catch (SQLException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
        return 1;
    }
    public static int movie(int theatreid){
        try {
            PreparedStatement pst = DatabaseConnection.getConnection().prepareStatement(Queries.movie);
            pst.setInt(1, theatreid);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("movie");
            }
        } catch (SQLException e) {
		System.out.println("Error during fetching ID");
        }
        return 0;
    }
  public static String displaySession() {
    StringBuilder result = new StringBuilder();
    try {
        PreparedStatement pst = DatabaseConnection.getConnection().prepareStatement(Queries.session);
        ResultSet rs = pst.executeQuery();
        result.append("------------------------------------------\n");
        while (rs.next()) {
            result.append(String.format("| %-16s : %-20d |\n", "Id", rs.getInt("id")));
            String sessionTime = rs.getString("name");
            result.append(String.format("| %-16s : %-20s |\n", "Session Time", sessionTime));
            result.append("------------------------------------------\n");
        }
    } catch (SQLException e) {
        result.append("Error occurred while displaying sessions: ").append(e.getMessage()).append("\n");
    }
    return result.toString();
   }
   public static String chooseId(int userId) {
    StringBuilder result = new StringBuilder();
    boolean bookingsFound = false;

    try {
        PreparedStatement pst = DatabaseConnection.getConnection().prepareStatement(Queries.choose_id);
        pst.setInt(1, userId);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            bookingsFound = true;
            result.append("-------------------------------------------------\n");
            result.append("Booking ID        : ").append(rs.getInt("id")).append("\n");
            result.append("Movie Name        : ").append(rs.getString("movie_name")).append("\n");
            result.append("Theatre Name      : ").append(rs.getString("theatre_name")).append("\n");
            result.append("Theatre Location  : ").append(rs.getString("theatre_location")).append("\n");
            result.append("Tickets Booked    : ").append(rs.getInt("tickets_booked")).append("\n");
            result.append("Session Timing    : ").append(rs.getString("timing")).append("\n");
            result.append("Booking Date      : ").append(rs.getDate("booking_date")).append("\n");
            result.append("-------------------------------------------------\n");
        }

        if (!bookingsFound) {
            result.append("No bookings found for the user.\n");
        }

    } catch (SQLException e) {
        result.append("Error occurred while retrieving bookings: ").append(e.getMessage()).append("\n");
    }
    return bookingsFound ? result.toString() : "0";
}


  public static void cancelTicket(int userId, int id) {
    String query = "DELETE FROM booking WHERE id = ? AND user_id = ? AND booking_date = CURRENT_DATE";
    try {
        PreparedStatement pst = DatabaseConnection.getConnection().prepareStatement(query);
        pst.setInt(1, id);
        pst.setInt(2, userId);

        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Booking canceled successfully.");
        } else {
            System.out.println("No booking found for the given user on today's date.");
        }

    } catch (SQLException e) {
        System.out.println("Error occurred while canceling the ticket: " + e.getMessage());
    }
  }   
}
