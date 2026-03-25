import java.util.*;

/**
 * --- CANCELLATION SERVICE ---
 * Manages the logic for rolling back confirmed bookings and updating state.
 */
class CancellationService {
    private Map<String, Integer> inventory;
    private List<String> confirmedBookings; // Our history from Use Case 8
    private Stack<String> cancellationLogs = new Stack<>(); // To track rollback history

    public CancellationService(Map<String, Integer> inventory, List<String> history) {
        this.inventory = inventory;
        this.confirmedBookings = history;
    }

    /**
     * Performs a controlled rollback of a booking.
     */
    public void cancelBooking(String reservationId, String roomType) {
        System.out.println("[CANCELLATION] Attempting to cancel: " + reservationId);

        // 1. Validation: Ensure the reservation exists in our history
        if (confirmedBookings.contains(reservationId)) {

            // 2. State Reversal: Remove from History
            confirmedBookings.remove(reservationId);

            // 3. Inventory Restoration: Increment the count
            int currentCount = inventory.getOrDefault(roomType, 0);
            inventory.put(roomType, currentCount + 1);

            // 4. LIFO Tracking: Push to cancellation stack
            cancellationLogs.push(reservationId);

            System.out.println("SUCCESS: " + reservationId + " cancelled. Inventory restored.");
        } else {
            // 5. Defensive Guard: Prevent non-existent or double cancellations
            System.err.println("ERROR: Cancellation failed. Reservation ID not found.");
        }
        System.out.println("-------------------------------------------");
    }

    public void showRollbackHistory() {
        System.out.println("Recent Rollbacks (Last-In-First-Out): " + cancellationLogs);
    }
}

/**
 * --- APPLICATION ENTRY POINT ---
 */
public class BookMyStay {
    public static void main(String[] args) {
        // Initialize State
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Suite", 0); // Currently sold out

        List<String> history = new ArrayList<>();
        history.add("SUITE-101");
        history.add("SUITE-102");

        CancellationService cancelService = new CancellationService(inventory, history);

        System.out.println("Initial Suite Inventory: " + inventory.get("Suite"));
        System.out.println("Active Bookings: " + history + "\n");

        // Scenario 1: Valid Cancellation
        cancelService.cancelBooking("SUITE-102", "Suite");

        // Scenario 2: Invalid Cancellation (ID doesn't exist)
        cancelService.cancelBooking("SUITE-999", "Suite");

        // Scenario 3: Double Cancellation (Trying to cancel 102 again)
        cancelService.cancelBooking("SUITE-102", "Suite");

        // Final State Check
        System.out.println("Final Suite Inventory: " + inventory.get("Suite"));
        cancelService.showRollbackHistory();
    }
}