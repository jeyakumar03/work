import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

public class BookingDb {
    private static Scanner scanner = new Scanner(System.in);
    private List<String> displayRoute(String query) throws SQLException {
        List<String> routes = new ArrayList<>();
        StringBuilder formattedRoutes = new StringBuilder();
        try (PreparedStatement pst = DatabaseConnection.getConnection().prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            formattedRoutes.append("+-----+---------------------+\n");
            formattedRoutes.append("| ID  | Route               |\n");
            formattedRoutes.append("+-----+---------------------+\n");        
            while (rs.next()) {
                formattedRoutes.append(String.format("| %-3d | %-19s |\n", rs.getInt(1), rs.getString(2)));
            }
            formattedRoutes.append("+-----+---------------------+\n");
        }
        routes.add(formattedRoutes.toString());
        return routes;
    }
    public List<String> displayFromRoute() throws SQLException {
        return displayRoute(QueriesContainer.Queries.DISPLAY_ROUTE.getQuery());
    }

    // Display 'To' routes
    public List<String> displayToRoute() throws SQLException {
        return displayRoute(QueriesContainer.Queries.DISPLAY_ROUTE_TO.getQuery());
    }
    public static List<String> getBusdetails(int from_id, int to_id) throws SQLException {
        List<String> busDetails = new ArrayList<>();  
        Connection con=DatabaseConnection.getConnection();
        try (PreparedStatement pst =con.prepareStatement(QueriesContainer.Queries.DISPLAY_AVAILABLE_BUS.getQuery())) {
            pst.setInt(1, from_id);
            pst.setInt(2, to_id);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Time departureTime = rs.getTime("Starting_Time");
                    String busDetail = String.format("%d|%s|%d|%s|%s|%s|%s|%s|%s", 
                        rs.getInt("id"),
                        rs.getString("bus_number"),
                        rs.getInt("seat_capacity"),
                        rs.getBoolean("isac") ? "AC" : "Non-AC",
                        rs.getBoolean("issleeper") ? "Sleeper" : "Semi-Sleeper",
                        rs.getString("travels_name"),
                        rs.getString("from_place"),
                        rs.getString("to_place"),
                        departureTime != null ? departureTime.toString() : "N/A");
                    busDetails.add(busDetail);
                }
            }
        }

        return busDetails;
    }
    public int seatCapacity(int id) {
        try (PreparedStatement pst = DatabaseConnection.getConnection().prepareStatement(QueriesContainer.Queries.DISPLAY_SEAT.getQuery())) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("seat_capacity");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching seat capacity.");
        }
        return 0;
    }
    public int available_seat(int bus_id) {
        try (PreparedStatement pst = DatabaseConnection.getConnection().prepareStatement(QueriesContainer.Queries.AVAILABLE_SEAT.getQuery())) {
            pst.setInt(1, bus_id);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching available seats.");
        }
        return 0;
    }
    public void bookTicket(Booking booking) throws SQLException {
        LocalDate today = LocalDate.now(); 
        Date sqlDate = Date.valueOf(today);
        Date travelSqlDate = Date.valueOf(booking.getTravelDate()); // Travel date
        String query = "INSERT INTO booking(user_id, bus_id, tickets_booked, booked_date, travel_date) VALUES (?, ?, ?, ?, ?)";
        Connection con = DatabaseConnection.getConnection();
        try (
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, booking.getUserId());  
            pst.setInt(2, booking.getBusId());   // Bus ID
            pst.setInt(3, booking.getSeatCount()); // Seat Count
            pst.setDate(4, sqlDate);             // Today's Date (Booking Date)
            pst.setDate(5, travelSqlDate);      // Selected Travel Date

            System.out.println("Booking for Bus ID: " + booking.getBusId() + " on Travel Date: " + booking.getTravelDate());
            pst.executeUpdate();
        }
    }
}

