import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Date;
import java.time.LocalDate;
import java.sql.Time;
public class UserDb {
    public void insertUser(User user){
        try{
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(QueriesContainer.Queries.INSERT_USER.getQuery());
            pst.setString(1, user.getName());
            pst.setLong(2, user.getMobile());
            pst.setString(3, user.getEmail());
            pst.executeUpdate();
            int userId = getUserIdByEmail(user.getEmail());
            insertUserCredentials(userId, user.getUsername(), user.getPassword());
        }
        catch (SQLException e) {
            System.out.println("Error while inserting user records.");
        }
    }
    private void insertUserCredentials(int userId, String username, String password) throws SQLException {
        try {
             Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(QueriesContainer.Queries.INSERT_USER_CRED.getQuery()); 
             pst.setInt(1, userId);
             pst.setString(2, username);
             pst.setString(3, password);
             pst.executeUpdate();
        }  
        catch (SQLException e) {
            System.out.println("Error while inserting user credentials");
        }
    }
    public int login(String username, String password)  {
        try{
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(QueriesContainer.Queries.LOGIN_QUERY.getQuery());
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                System.out.println("Login successful! Welcome, " + username);
                return rs.getInt("id");
            } else {
                System.out.println("Invalid username or password.");
                return 0;
            }
        }
        catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
        return 0; 
    }
    public void updateUser(String column, String newValue, int userId) {
        String query = "UPDATE users SET " + column + " = ? WHERE id = ?";
        try{
             Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
            if ("mobile".equals(column)) {
                pst.setLong(1, Long.parseLong(newValue));
            } else {
                pst.setString(1, newValue);
            }
            pst.setInt(2, userId);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful: " + rowsAffected + " row(s) affected.");
            } else {
                System.out.println("No rows matched the condition. Update failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error during update: " + e.getMessage());
        }
    }
    public void updateUserCredentials(int userId, String newPassword) {
        String query = "UPDATE usercred SET password = ? WHERE id = ?";
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, newPassword);
            pst.setInt(2, userId);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Password updated successfully  " );
            } 
        } catch (SQLException e) {
            System.out.println("Error during password update: " + e.getMessage());
        }
    }
    private int getUserIdByEmail(String email)  {
        try {
             Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(QueriesContainer.Queries.SELECT_ID.getQuery());
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
            catch (SQLException e) {
            	System.out.println("Error during login: " + e.getMessage());
        }
        
        return 0;  
    }
public String viewTicket(int userId) {
    StringBuilder result = new StringBuilder();
    try {
        PreparedStatement pst = DatabaseConnection.getConnection().prepareStatement(QueriesContainer.Queries.SELECT_BOOKING.getQuery());
        pst.setInt(1, userId);
        ResultSet rs = pst.executeQuery();  
        boolean bookingsFound = false;
        while (rs.next()) {
            bookingsFound = true;
            result.append("-------------------------------------------------\n");
            result.append("Booking ID        : ").append(rs.getInt("booking_id")).append("\n");
            result.append("Tickets Booked    : ").append(rs.getInt("Tickets_Booked")).append("\n");
            result.append("Booked Date       : ").append(rs.getDate("Booked_Date")).append("\n");
            result.append("Travel Date       : ").append(rs.getDate("Travel_date")).append("\n");
            result.append("Bus Number        : ").append(rs.getString("Bus_Number")).append("\n");
            boolean isAc = rs.getBoolean("Type");
            result.append("Bus Type (AC)     : ").append(isAc ? "AC" : "Non-AC").append("\n");
            boolean isSleeper = rs.getBoolean("Sleeper");
            result.append("Sleeper Type      : ").append(isSleeper ? "Sleeper" : "Semi-Sleeper").append("\n");
            result.append("Travels Name      : ").append(rs.getString("Travels_Name")).append("\n");
            Time departureTime = rs.getTime("Departure_Time");
            result.append("Departure Time    : ").append(departureTime != null ? departureTime.toString() : "N/A").append("\n");
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
   public boolean cancelTicket(int bookingId) {
    try {
        String selectSQL = "SELECT travel_date, bus.departure_time FROM booking JOIN bus ON booking.bus_id = bus.id WHERE booking.id = ?";
        PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(selectSQL);
        preparedStatement.setInt(1, bookingId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            java.sql.Date travelDate = resultSet.getDate("travel_date");
            Time departureTime = resultSet.getTime("departure_time");
            if (travelDate != null && departureTime != null) {
                java.time.LocalDate localTravelDate = travelDate.toLocalDate();
                java.time.LocalTime localDepartureTime = departureTime.toLocalTime();
                java.time.LocalDateTime departureDateTime = java.time.LocalDateTime.of(localTravelDate, localDepartureTime);
                java.time.LocalDateTime currentDateTime = java.time.LocalDateTime.now();
                java.time.Duration duration = java.time.Duration.between(currentDateTime, departureDateTime);
                if (duration.toHours() >= 4) {
                    String deleteSQL = "DELETE FROM booking WHERE id = ?";
                    PreparedStatement deleteStatement = DatabaseConnection.getConnection().prepareStatement(deleteSQL);
                    deleteStatement.setInt(1, bookingId);
                    int rowsAffected = deleteStatement.executeUpdate();
                    
                    if (rowsAffected > 0) {
                        System.out.println("Booking ID " + bookingId + " has been successfully canceled.");
                        return true;
                    } else {
                        System.out.println("Error: Could not cancel booking. Please try again.");
                        return false;
                    }
                } else {
                    System.out.println("Cancellation is not allowed within 4 hours of departure.");
                    return false;
                }
            } else {
                System.out.println("Invalid travel date or departure time.");
                return false;
            }
        } else {
            System.out.println("Booking not found.");
            return false;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    
}

