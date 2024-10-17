import java.util.Scanner;
import java.sql.SQLException;

public class AdminInfo {
    private Scanner ob = new Scanner(System.in);
    private AdminDb adminDb;

    // Constructor for dependency injection
    public AdminInfo(AdminDb adminDb) {
        this.adminDb = adminDb;
    }

    // Show menu and handle the choices
    public void show(int userId) throws SQLException {
        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("1. Add Bus\n2. Display Bus Details\n3. Edit Bus Details\n4. View Booked Tickets\n5. Exit");
            System.out.print("Enter your choice: ");
            int choice = ob.nextInt();

            switch (choice) {
                case 1:
                    new AdminHelper(adminDb).addBus(userId);  // Calls method to add bus
                    break;
                case 2:
                    System.out.println(adminDb.displayBus(userId));  // Display buses for the user
                    break;
                case 3:
                    new AdminHelper(adminDb).editAdmin(userId);  // Call method to edit admin details
                    break;
                case 5:
                    continueLoop = false;  // Exit the loop
                    break;
                default:
                    System.out.println("Please enter a valid choice.");
                    break;
            }
        }
    }
}

