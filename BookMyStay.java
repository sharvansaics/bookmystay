import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents a guest's intent to book a specific room.
 * This is a "Request" object, not a confirmed booking yet.
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Requested: " + roomType;
    }
}

/**
 * Manages incoming booking requests using a Queue (LinkedList).
 * Ensures First-Come-First-Served fairness.
 */
class BookingQueue {
    private Queue<Reservation> requestQueue;

    public BookingQueue() {
        // LinkedList implements the Queue interface in Java
        this.requestQueue = new LinkedList<>();
    }

    /**
     * Adds a new reservation request to the end of the line.
     */
    public void addRequest(Reservation request) {
        requestQueue.add(request);
        System.out.println("[INTAKE] " + request + " - Added to Queue.");
    }

    /**
     * Displays the current state of the queue without removing items.
     */
    public void showQueueStatus() {
        System.out.println("\n--- Current Booking Request Queue ---");
        if (requestQueue.isEmpty()) {
            System.out.println("No pending requests.");
        } else {
            for (Reservation req : requestQueue) {
                System.out.println(">> " + req);
            }
        }
        System.out.println("-------------------------------------\n");
    }
}

/**
 * --- APPLICATION ENTRY POINT ---
 */
public class BookMyStay {
    public static void main(String[] args) {
        // 1. Initialize the Queue
        BookingQueue queueManager = new BookingQueue();

        System.out.println("Hotel Booking System: Accepting Requests...\n");

        // 2. Simulate guests submitting requests at nearly the same time
        queueManager.addRequest(new Reservation("Alice", "Suite"));
        queueManager.addRequest(new Reservation("Bob", "Single"));
        queueManager.addRequest(new Reservation("Charlie", "Suite"));

        // 3. Show the order of requests
        queueManager.showQueueStatus();

        System.out.println("Ready for processing. No rooms have been allocated yet.");
    }
}