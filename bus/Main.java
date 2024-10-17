import java.util.Scanner;
import java.sql.SQLException;
public class Main {
    private static Scanner ob = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        boolean s = true;
        while (s) {
            System.out.println("-------------------------------");
            System.out.println("1. SuperAdmin\n2. Admin\n3. User");
            System.out.println("-------------------------------");
            System.out.print("Enter your choice: ");
            int choice = ob.nextInt();
            ob.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    SuperAdmin.show();
                    break;
                case 2:
                    // Create instance of AdminHelper and call signin
                    AdminDb adminDb = new AdminDb(); // You need to pass an instance of AdminDb
                    new AdminHelper(adminDb).signin();
                    break;
                case 3:
                    UserInfo.show();
                    break;
                default:
                    System.out.println("Please enter a valid choice:");
                    break;
            }
            System.out.println("-------------------------------");
        }
    }
}

