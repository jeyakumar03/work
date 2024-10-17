import java.sql.SQLException;
import java.util.Scanner;

public class SuperAdmin {
    private static final String username = "jeyakumar";
    private static final String password = "jeyakumar@123";
    private static Scanner ob = new Scanner(System.in);

    // Create an instance of AdminDb once and reuse it throughout the class
    private static AdminDb adminDb;

    // Show the login menu for SuperAdmin
    public static void show() throws SQLException {
        adminDb = new AdminDb();  // Create AdminDb instance here
        System.out.println("Admin Login");
        System.out.print("Enter username: ");
        String enteredUsername = ob.next();
        System.out.print("Enter password: ");
        String enteredPassword = ob.next();
        login(enteredUsername, enteredPassword);
    }
    public static void login(String enteredUsername, String enteredPassword) throws SQLException {
        if (username.equals(enteredUsername) && password.equals(enteredPassword)) {
            System.out.println("Login successful!");
            ad();
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    // Admin operations
    public static void ad() throws SQLException {
        boolean s = true;
        while (s) {
            System.out.println("------------------------------------------------");
            System.out.println("1. Add Admin\n2. Delete Admin\n3. Display Admin\n4. Exit");
            System.out.println("------------------------------------------------");
            System.out.print("Enter your choice: ");
            int choice = ob.nextInt();
            ob.nextLine(); // Consume the newline character
            System.out.println("------------------------------------------------");
            switch (choice) {
                case 1:
                    addAdmin();
                    break;
                case 2:
                    deleteAdmin();
                    break;
                case 3:
                    displayAdmin();
                    break;
                case 4:
                    s = false; // Exit the loop
                    break;
                default:
                    System.out.println("Enter a valid choice:");
                    break;
            }
        }
    }

    // Add Admin logic
    private static void addAdmin() throws SQLException {
        System.out.print("Enter the admin name: ");
        String name = ob.nextLine();
        System.out.print("Enter the admin username: ");
        String username = ob.nextLine();
        System.out.print("Enter the travels name: ");
        String travelsname = ob.nextLine();
        System.out.print("Enter the mail id: ");
        String mail = ob.nextLine();
        System.out.print("Enter the mobile: ");
        long mobile = ob.nextLong();
        ob.nextLine(); // Consume the newline character
        System.out.print("Enter the password: ");
        String password = ob.nextLine();
        Admin admin = new Admin(name, username, travelsname, mail, mobile, password);

        if (adminDb.addAdmin(admin) != 0) {
            System.out.println("Admin added successfully.");
        } else {
            System.out.println("Admin addition failed.");
        }
    }

    // Delete Admin logic
    private static void deleteAdmin() throws SQLException {
        System.out.print("Enter the admin mail to delete: ");
        String adminMail = ob.nextLine();
        adminDb.deleteAdmin(adminMail);
    }

    // Display all Admins logic
    private static void displayAdmin() throws SQLException {
        System.out.println("Admin List: ");
        for (String admin : adminDb.displayAdmin()) {
            System.out.println(admin);
        }
    }
}

