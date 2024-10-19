import java.util.Scanner;
import java.util.List;
import java.sql.Time;
import java.sql.SQLException;
public class AdminHelper {
    private Scanner ob = new Scanner(System.in);
    private AdminDb adminDb;
    public AdminHelper(AdminDb adminDb) {
        this.adminDb = adminDb;
    }
    public void signin() throws SQLException {
        System.out.println("Enter the username:");
        String username = ob.nextLine();
        System.out.println("Enter the password:");
        String password = ob.nextLine();
        int userId = adminDb.adminLogin(username, password);
        if (userId != -1) {
            new AdminInfo(adminDb).show(userId);
        } else {
            System.out.println("Invalid credentials. Please try again.");
            signin();
        }
    }
   public void addBus(int userId) {
    try {
        System.out.println("Enter the bus number:");
        int number = ob.nextInt();
        System.out.println("Enter the seat capacity:");
        int seat = ob.nextInt();
        System.out.println("Enter the bus type AC(true) or NON-AC(false):");
        boolean isAc = ob.nextBoolean();
        System.out.println("Enter the bus type Sleeper(true) or SemiSleeper(false):");
        boolean isSleeper = ob.nextBoolean();
        System.out.println("Select The Starting point of your Bus:");
        System.out.println(adminDb.displayFromRoute());
        int fromRoute = ob.nextInt();
        System.out.println("Select The Destination point of your Bus:");
        System.out.println(adminDb.displayToRoute());
        int toRoute = ob.nextInt();
        System.out.println("Enter the departure time (HH:mm):");
        String timeString = ob.next(); 
        Time departureTime = Time.valueOf(timeString + ":00"); 
        Bus bus = new Bus(number, seat, isAc, isSleeper, fromRoute, toRoute, departureTime, userId);
        adminDb.addBus(bus);
    } catch (Exception e) {
        System.out.println("Error occurred while adding bus: " + e.getMessage());
    }
}
    public void editAdmin(int userId) throws SQLException {
        System.out.println("Enter the details you want to edit:\n1. Username\n2. Travels Name\n3. Mobile\n4. Mail");
        int choice = ob.nextInt();
        ob.nextLine(); 

        switch (choice) {
            case 1:
                
                System.out.println("Enter new Name:");
                String newUsername = ob.nextLine();
                adminDb.updateAdmin("name", newUsername,userId);
                break;
            case 2:
                
                System.out.println("Enter new travels name:");
                String newTravelsName = ob.nextLine();
                adminDb.updateAdmin("travels_name", newTravelsName,userId);
                break;
            case 3:
                
                System.out.println("Enter new mobile number:");
                long newMobile = ob.nextLong();
                adminDb.updateAdmin("mobile", String.valueOf(newMobile),userId);
                break;
            case 4:
                
                System.out.println("Enter new mail:");
                String newMail = ob.nextLine();
                adminDb.updateAdmin( "mail",newMail,userId);
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }
    public void editBus(int userId) throws SQLException {
    System.out.println("Select The bus id:");
    List<String> busDetails = adminDb.displayBus(userId);
    busDetails.forEach(System.out::println); 
    int bus_id = ob.nextInt(); 
    ob.nextLine(); 
    System.out.println("Select the detail you want to edit:");
    System.out.println("1. Bus Number");
    System.out.println("2. Seat Capacity");
    System.out.println("3. AC Type");
    System.out.println("4. Sleeper Type");
    System.out.println("5. Starting Place");
    System.out.println("6. Destination Place");
    System.out.print("Enter your choice: ");
    int choice = ob.nextInt();
    ob.nextLine(); 
    switch (choice) {
        case 1:
            System.out.print("Enter the new bus number: ");
	 int newBusNumber = ob.nextInt();
            adminDb.updateBus("bus_number", newBusNumber, bus_id);
            break;
        case 2:
            System.out.print("Enter the new seat capacity: ");
            int newSeatCapacity = ob.nextInt();
            adminDb.updateBus("seat_capacity", newSeatCapacity, bus_id);
            break;
        case 3:
            System.out.print("Is the bus AC? (true/false): ");
            boolean isAc = ob.nextBoolean();
            adminDb.updateBus("isac", isAc, bus_id); 
            break;
        case 4:
            System.out.print("Is the bus a sleeper? (true/false): ");
            boolean isSleeper = ob.nextBoolean();
            adminDb.updateBus("issleeper", isSleeper, bus_id);
            break;
        case 5:
             System.out.println("Select The Starting point of your Bus:");
            System.out.println(adminDb.displayFromRoute());
            int fromRoute = ob.nextInt();
            adminDb.updateBus("from_place", fromRoute, bus_id); 
            break;
        case 6:
            System.out.println("Select The Destination point of your Bus:");
            System.out.println(adminDb.displayToRoute());
            int toRoute = ob.nextInt();
            adminDb.updateBus("to_place", toRoute, bus_id); 
            break;
        default:
            System.out.println("Invalid choice. Please try again.");
            break;
    }
}

}

