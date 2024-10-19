import java.time.LocalDate;

public class Booking {
    private int userId;
    private int busId;
    private int seatCount;
    private LocalDate travelDate; // Added field to store the travel date

    // Constructor with travel date
    public Booking(int userId, int busId, int seatCount, LocalDate travelDate) {
        this.userId = userId;
        this.busId = busId;
        this.seatCount = seatCount;
        this.travelDate = travelDate;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public int getBusId() {
        return busId;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }
}

