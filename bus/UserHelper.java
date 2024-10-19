import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.*;
public class UserHelper {
    private static Scanner scanner = new Scanner(System.in);
    private static UserDb userdb = new UserDb();
        public static void signup() throws SQLException {
        String name = getValidName();
        String username = getValidUsername();
        long mobile = getValidMobile();
        String email = getValidEmail();
        String password = getValidPassword();
        User user = new User(name, username, mobile, email, password);
        userdb.insertUser(user);
        System.out.println("Signup successful!");
    }
    private static String getValidName() {
        String name;
        while (true) {
            System.out.println("Enter the name:");
            name = scanner.nextLine();
            if (isValidName(name)) {
                return name;
            } else {
                System.out.println("Invalid name! Name should only contain letters and spaces.");
            }
        }
    }
    private static String getValidUsername() {
        String username;
        while (true) {
            System.out.println("Enter the username:");
            username = scanner.nextLine();
            if (isValidUsername(username)) {
                return username;
            } else {
                System.out.println("Invalid username! Username should be between 3 and 20 characters.");
            }
        }
    }
    private static long getValidMobile() {
        long mobile;
        while (true) {
            System.out.println("Enter the mobile number:");
            mobile = scanner.nextLong();
            scanner.nextLine(); 
            if (isValidMobile(mobile)) {
                return mobile;
            } else {
                System.out.println("Invalid mobile number! Please enter a valid 10-digit mobile number.");
            }
        }
    }
    private static String getValidEmail() {
        String email;
        while (true) {
            System.out.println("Enter the email id:");
            email = scanner.nextLine();
            if (isValidEmail(email)) {
                return email;
            } else {
                System.out.println("Invalid email format! Please enter a valid email.");
            }
        }
    }
    private static String getValidPassword() {
        String password;
        while (true) {
            System.out.println("Enter the password:");
            password = scanner.nextLine();
            if (isValidPassword(password)) {
                return password;
            } else {
                System.out.println("Invalid password! Password must be at least 8 characters long and contain a mix of letters, numbers, and special characters.");
            }
        }
    }
    private static boolean isValidName(String name) {
        return name != null && name.matches("^[a-zA-Z\\s]+$");
    }
    private static boolean isValidUsername(String username) {
        return username != null && username.length() >= 3 && username.length() <= 20;
    }
    private static boolean isValidMobile(long mobile) {
        String mobileStr = String.valueOf(mobile);
        return mobileStr.length() == 10;
    }
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }
    private static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        return pattern.matcher(password).matches();
    }
    public static int login() throws SQLException {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        int userId = userdb.login(username, password);
        if (userId != 0) {
           UserInfo.activity(userId); 
        }
        return userId;
    }
    public static void updateUser(int id)throws SQLException {
        while (true) {
            System.out.println("-----------------------------------------------");
            System.out.println("Enter the choice for update:\n1.Name\n2.Mobile\n3.Mail\n4.Password\n5.Exit");
            System.out.println("-----------------------------------------------");
            int choice = scanner.nextInt();
            scanner.nextLine();  
            switch (choice) {
                case 1:
                    System.out.println("Enter the new name:");
                    String newName = scanner.nextLine();
                     userdb.updateUser("name", newName, id);
                    break;
                case 2:
                    System.out.println("Enter the new mobile number:");
                    long newMobile = scanner.nextLong();
                    scanner.nextLine();  
                    userdb.updateUser("mobile", String.valueOf(newMobile), id);
                    break;
                case 3:
                    System.out.println("Enter the new email:");
                    String newEmail = scanner.nextLine();
                     userdb.updateUser("mail", newEmail, id);
                    break;
                case 4:
                    System.out.println("Enter the new password:");
                    String newPassword = scanner.nextLine();
                    userdb.updateUserCredentials(id, newPassword);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public static void viewTicket(int userId){
    	System.out.println(userdb.viewTicket(userId));
    }
    public static void  cancelTicket(int userId){
    	System.out.println(userdb.viewTicket(userId));
    	System.out.println("Enter the Booking Id for Cancel Ticket:");
    	int book_id=scanner.nextInt();
    	if(userdb.cancelTicket(book_id)){
    		 System.out.println("Ticket with booking ID " + book_id + " has been canceled.");
    	}
    	else{
    		 System.out.println("Cannot cancel .");
    	}
    	
    }
    

}

