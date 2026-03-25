import java.util.*;

/**
 * --- CUSTOM EXCEPTIONS ---
 * Domain-specific errors for better code readability and debugging.
 */
class InvalidRoomTypeException extends Exception {
    public InvalidRoomTypeException(String message) { super(message); }
}

class InsufficientInventoryException extends Exception {
    public InsufficientInventoryException(String message) { super(message); }
}

/**
 * --- VALIDATOR SERVICE ---
 */
class BookingValidator {
    /**
     * Fail-Fast Validation: Check constraints before any state changes.
     */
    public static void validateRequest(String type, Map<String, Integer> inventory)
            throws InvalidRoomTypeException, InsufficientInventoryException {

        // Rule 1: Room type must exist (Case Sensitive)
        if (!inventory.containsKey(type)) {
            throw new InvalidRoomTypeException("Error: Room type '" + type + "' does not exist.");
        }

        // Rule 2: Room must be in stock
        if (inventory.get(type) <= 0) {
            throw new InsufficientInventoryException("Error: No vacancy for '" + type + "'.");
        }
    }
}

/**
 * --- CORE SYSTEM ---
 */
class HotelSystem {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addInventory(String type, int count) { inventory.put(type, count); }

    public void processBooking(String guest, String type) {
        System.out.println("[PROCESSING] " + guest + " requesting " + type + "...");

        try {
            // Step 1: Validate (Guarding System State)
            BookingValidator.validateRequest(type, inventory);

            // Step 2: Update State (Only if validation passes)
            inventory.put(type, inventory.get(type) - 1);
            System.out.println("SUCCESS: Booking confirmed for " + guest);

        } catch (InvalidRoomTypeException | InsufficientInventoryException e) {
            // Graceful Failure: Print message but keep the app running
            System.err.println("REJECTED: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("CRITICAL: An unexpected error occurred.");
        }
        System.out.println("-------------------------------------------");
    }
}

/**
 * --- APPLICATION ENTRY POINT ---
 */
public class BookMyStay {
    public static void main(String[] args) {
        HotelSystem hotel = new HotelSystem();
        hotel.addInventory("Suite", 1);
        hotel.addInventory("Single", 5);

        System.out.println("Hotel Booking System with Error Handling\n");

        // Scenario 1: Valid Booking
        hotel.processBooking("Alice", "Suite");

        // Scenario 2: Insufficient Inventory (Double booking the last suite)
        hotel.processBooking("Bob", "Suite");

        // Scenario 3: Invalid Room Type (Case Sensitive error or typo)
        hotel.processBooking("Charlie", "suite"); // Lowercase 's' fails validation

        // Scenario 4: Non-existent Room Type
        hotel.processBooking("David", "Penthouse");

        System.out.println("\nSystem remains stable. All errors were caught and handled.");
    }
}