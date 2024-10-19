import java.util.List;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
public class BookingHelper {
    private static Scanner scanner = new Scanner(System.in);
    private static BookingDb bookingDb = new BookingDb();


public static void bookTicket(int userId) throws SQLException {
    // Step 1: Ask for the date of travel first (from the next 7 days)
    System.out.println("Select the date of travel (You can select from the next 7 days):");
    
    LocalDate today = LocalDate.now();
    for (int i = 0; i < 7; i++) {
        LocalDate travelDate = today.plusDays(i);
        System.out.println((i + 1) + ". " + travelDate);
    }

    System.out.println("Enter the number of the date you want to select (1-7):");
    int dateChoice = scanner.nextInt();
    scanner.nextLine(); // Consume the newline character after the int input

    if (dateChoice < 1 || dateChoice > 7) {
        System.out.println("Invalid choice. Please select a number between 1 and 7.");
        return;
    }
    
    // Step 2: Get the selected travel date
    LocalDate selectedDate = today.plusDays(dateChoice - 1);
    
    // Step 3: Ask for the starting and destination places
    System.out.println("Select the starting place:");
    System.out.println(bookingDb.displayFromRoute());
    int from_id = scanner.nextInt();
    scanner.nextLine(); // Consume the newline

    System.out.println("Select the destination place:");
    System.out.println(bookingDb.displayToRoute());
    int to_id = scanner.nextInt();
    scanner.nextLine();
    List<String> busDetails = bookingDb.getBusdetails(from_id, to_id);
    if (busDetails.isEmpty()) {
        System.out.println("No buses available for this route on " + selectedDate + ". Please select another location or date.");
        return;
    }
    System.out.println("+--------+-----------------+--------------------+------------------+---------------+-------------+------------------+-------------+-------------------+");
    System.out.println("| ID     | Bus Number      | Seat Capacity      | AC Type          | Sleeper       | Travels     | From Place       | To Place    | Departure Time   |");
    System.out.println("+--------+-----------------+--------------------+------------------+---------------+-------------+------------------+-------------+-------------------+");
    for (String bus : busDetails) {
        String[] details = bus.split("\\|");
        System.out.printf("| %-6s | %-15s | %-18s | %-16s | %-13s | %-11s | %-16s | %-10s | %-17s |\n", 
            details[0], details[1], details[2], details[3], details[4], details[5], details[6], details[7], details[8]);
    }
    System.out.println("+--------+-----------------+--------------------+------------------+---------------+-------------+------------------+-------------+-------------------+");
    System.out.println("Select the Bus ID:");
    int bus_id = scanner.nextInt();
    scanner.nextLine(); 
    int busCapacity = bookingDb.seatCapacity(bus_id);
    int booked_seat = bookingDb.available_seat(bus_id);
    int available_seat = busCapacity - booked_seat;
    System.out.println("Available Seats: " + available_seat);
    System.out.println("Enter the number of seats you want to book:");
    int seat_count = scanner.nextInt();
    scanner.nextLine(); 
    
    if (available_seat >= seat_count) {
        Booking booking = new Booking(userId, bus_id, seat_count, selectedDate);  // Pass selectedDate
        bookingDb.bookTicket(booking);
        System.out.println("Ticket booked successfully for " + selectedDate + "!");
    } else {
        System.out.println("Not enough seats available on this bus. Please select another bus.");
    }
}



   
}

