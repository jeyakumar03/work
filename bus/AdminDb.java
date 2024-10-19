import java.sql.*;
import java.util.List;
import java.util.ArrayList;
public class AdminDb {
    private Connection con;
    public AdminDb() throws SQLException{
        this.con = DatabaseConnection.getConnection();
    }
    public int addAdmin(Admin admin) throws SQLException {
        int rowsAffected = 0;
        try (PreparedStatement pst = con.prepareStatement(QueriesContainer.Queries.INSERT_ADMIN.getQuery())) {
            pst.setString(1, admin.getName());
            pst.setLong(2, admin.getMobile());
            pst.setString(3, admin.getMail());
            pst.setString(4, admin.getTravels());
            rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                int userId = getId(admin.getMail());
                insertAdminCredentials(userId, admin.getUsername(), admin.getPassword());
            }
        }
        return rowsAffected;
    }
    public static int adminLogin(String username, String password) {
        int userId=-1;
        try {
            PreparedStatement pst = DatabaseConnection.getConnection().prepareStatement(QueriesContainer.Queries.ADMIN_LOGIN_QUERY.getQuery());
            pst.setString(1, username);
            pst.setString(2, password);  
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                 userId = rs.getInt("id");
                System.out.println("Login successful! welcome: " + username);
                return userId;
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            System.out.println("Admin Not Found");
        }
        return userId; 
    }
    private void insertAdminCredentials(int userId, String username, String password) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(QueriesContainer.Queries.INSERT_ADMIN_CRED.getQuery())) {
            pst.setInt(1, userId);
            pst.setString(2, username);
            pst.setString(3, password);
            pst.executeUpdate();
        }
    }
    public int getId(String email) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(QueriesContainer.Queries.SELECT_ADMIN_ID.getQuery())) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        throw new SQLException("No user found with email: " + email);
    }
    public void deleteAdmin(String mail) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(QueriesContainer.Queries.ADMIN_DELETE.getQuery())) {
            pst.setString(1, mail);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No admin found with the provided email.");
            }
        }
    }
   public List<String> displayAdmin() throws SQLException {
    List<String> admins = new ArrayList<>();
    StringBuilder formattedAdmins = new StringBuilder();
    try (PreparedStatement pst = con.prepareStatement(QueriesContainer.Queries.DISPLAY_ADMIN.getQuery());
         ResultSet rs = pst.executeQuery()) {
        formattedAdmins.append("+-----+---------------------------+-----------------+------------------------------+---------------------+\n");
        formattedAdmins.append("| ID  | Name                      | Mobile          | Email                        | Travels             |\n");
        formattedAdmins.append("+-----+---------------------------+-----------------+------------------------------+---------------------+\n");
        while (rs.next()) {
            formattedAdmins.append(String.format("| %-3d | %-25s | %-15d | %-28s | %-19s |\n",
                    rs.getInt(1), 
                    rs.getString(2), 
                    rs.getLong(3), 
                    rs.getString(4), 
                    rs.getString(5))); 
        }
        formattedAdmins.append("+-----+---------------------------+-----------------+------------------------------+---------------------+\n");
    }
    admins.add(formattedAdmins.toString());
    return admins;
}
    public List<String> displayFromRoute() throws SQLException {
    List<String> routes = new ArrayList<>();
    StringBuilder formattedRoutes = new StringBuilder();
    try (PreparedStatement pst = con.prepareStatement(QueriesContainer.Queries.DISPLAY_ROUTE.getQuery());
         ResultSet rs = pst.executeQuery()) {
        formattedRoutes.append("+-----+---------------------+\n");
        formattedRoutes.append("| ID  | From                |\n");
        formattedRoutes.append("+-----+---------------------+\n");
        while (rs.next()) {
            formattedRoutes.append(String.format("| %-3d | %-19s |\n",
                    rs.getInt(1),  
                    rs.getString(2) 
            ));
        }
        formattedRoutes.append("+-----+---------------------+\n");
    }
    routes.add(formattedRoutes.toString());
    return routes;
}

public List<String> displayToRoute() throws SQLException {
    List<String> routes = new ArrayList<>();
    StringBuilder formattedRoutes = new StringBuilder();
    try (PreparedStatement pst = con.prepareStatement(QueriesContainer.Queries.DISPLAY_ROUTE_TO.getQuery());
         ResultSet rs = pst.executeQuery()) {
        formattedRoutes.append("+-----+---------------------+\n");
        formattedRoutes.append("| ID  | To                 |\n");
        formattedRoutes.append("+-----+---------------------+\n");
        while (rs.next()) {
            formattedRoutes.append(String.format("| %-3d | %-19s |\n",
                    rs.getInt(1),  
                    rs.getString(2) 
            ));
        }
        formattedRoutes.append("+-----+---------------------+\n");
    }
    routes.add(formattedRoutes.toString());
    return routes;
}

    public void addBus(Bus bus) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(QueriesContainer.Queries.INSERT_BUS.getQuery())) {
            pst.setInt(1, bus.getBusnum());
            pst.setInt(2, bus.getSeat());
            pst.setBoolean(3, bus.getAc());
            pst.setBoolean(4, bus.getSleeper());
            pst.setInt(5, bus.getAdmin());
            pst.setInt(6, bus.getFromRoute());
            pst.setInt(7, bus.getToRoute());
            pst.setTime(8, bus.getDepartureTime());    
            pst.executeUpdate();
        }
    }
public List<String> displayBus(int userId) throws SQLException {
    List<String> buses = new ArrayList<>();
    try (PreparedStatement pst = con.prepareStatement(QueriesContainer.Queries.DISPLAY_BUS.getQuery())) {
        pst.setInt(1, userId); 
        try (ResultSet rs = pst.executeQuery()) {  
        
            while (rs.next()) {
                StringBuilder busDetails = new StringBuilder();
                busDetails.append("+---------------------+-----------------------------+\n");
                busDetails.append("| Bus Detail           | Value                       |\n");
                busDetails.append("+---------------------+-----------------------------+\n");
                busDetails.append(String.format("| %-19s | %-27d |\n", "Bus ID", rs.getInt("id")));
                busDetails.append(String.format("| %-19s | %-27d |\n", "Bus Number", rs.getInt("Bus_Number")));
                busDetails.append(String.format("| %-19s | %-27s |\n", "Travels", rs.getString("Travels_Name")));
                busDetails.append(String.format("| %-19s | %-27d |\n", "Seat Capacity", rs.getInt("Seat")));
                String acType = rs.getBoolean("AC") ? "AC" : "Non-AC";
                busDetails.append(String.format("| %-19s | %-27s |\n", "AC Type", acType));
                String sleeperType = rs.getBoolean("Sleeper") ? "Sleeper" : "Semi-Sleeper";
                busDetails.append(String.format("| %-19s | %-27s |\n", "Sleeper", sleeperType));
                busDetails.append(String.format("| %-19s | %-27s |\n", "Starting Place", rs.getString("Starting_Place")));
                busDetails.append(String.format("| %-19s | %-27s |\n", "Destination", rs.getString("Destination")));
                busDetails.append("+---------------------+-----------------------------+\n");
                buses.add(busDetails.toString());
            }
        }
    }
    return buses;
}
    public void updateAdmin(String column, String newValue,  int adminId) throws SQLException {
        String query = "UPDATE admin SET " + column + " = ? WHERE  id = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            if ("mobile".equals(column)) {
                pst.setLong(1, Long.parseLong(newValue));
            } else {
                pst.setString(1, newValue);
            }
            pst.setInt(3, adminId);
            pst.executeUpdate();
        }
    }
    public void updateBus(String column, Object newValue, int busId) throws SQLException {
    String query = "UPDATE bus SET " + column + " = ? WHERE id = ?";
    try (PreparedStatement pst = con.prepareStatement(query)) {
        if (newValue instanceof String) {
            pst.setString(1, (String) newValue); 
        } else if (newValue instanceof Integer) {
            pst.setInt(1, (Integer) newValue); 
        } else if (newValue instanceof Boolean) {
            pst.setBoolean(1, (Boolean) newValue); 
        }
        pst.setInt(2, busId);
        int rowsUpdated = pst.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Bus details updated successfully.");
        } else {
            System.out.println("No bus found with the provided ID.");
        }
    }
}

}

