import java.util.Scanner;
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

            System.out.println("Select The Route Id:");
            System.out.println(adminDb.displayRoute());
            int route = ob.nextInt();
            Bus bus = new Bus(number, seat, isAc, isSleeper, route, userId);
            adminDb.addBus(bus);
        } catch (Exception e) {
            System.out.println("Error occurred while adding bus: " + e.getMessage());
        }
    }

    // Add the missing editAdmin method
    public void editAdmin(int userId) throws SQLException {
        System.out.println("Enter the details you want to edit:\n1. Username\n2. Travels Name\n3. Mobile\n4. Mail");
        int choice = ob.nextInt();
        ob.nextLine(); // Consume newline

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
}

