import java.util.*;

/**
 * --- DOMAIN LAYER ---
 */
class AddOnService {
    private String name;
    private double price;

    public AddOnService(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() { return name + " ($" + price + ")"; }
}

/**
 * --- SERVICE MANAGER ---
 * Manages the association between a Reservation ID and a List of Add-Ons.
 */
class AddOnManager {
    // Map of Reservation ID -> List of selected Services
    private Map<String, List<AddOnService>> selections = new HashMap<>();

    /**
     * Attaches a service to a specific reservation ID.
     */
    public void addService(String reservationId, AddOnService service) {
        // If the ID isn't in the map, create a new list for it
        selections.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
        System.out.println("[ADD-ON] Attached " + service.getName() + " to " + reservationId);
    }

    /**
     * Calculates the total cost of all add-ons for a specific reservation.
     */
    public double calculateTotalExtra(String reservationId) {
        List<AddOnService> services = selections.getOrDefault(reservationId, new ArrayList<>());
        double total = 0;
        for (AddOnService s : services) {
            total += s.getPrice();
        }
        return total;
    }

    public void displayAddOns(String reservationId) {
        List<AddOnService> services = selections.get(reservationId);
        if (services != null && !services.isEmpty()) {
            System.out.println("Add-ons for " + reservationId + ": " + services);
        }
    }
}

/**
 * --- APPLICATION ENTRY POINT ---
 */
public class BookMyStay {
    public static void main(String[] args) {
        // 1. Setup our "Core" (from previous use cases)
        String resId = "SUITE-101"; // Assume this was generated in Use Case 6
        AddOnManager addOnManager = new AddOnManager();

        // 2. Define available services
        AddOnService breakfast = new AddOnService("Buffet Breakfast", 25.0);
        AddOnService spa = new AddOnService("Spa Treatment", 120.0);
        AddOnService wifi = new AddOnService("Premium WiFi", 15.0);

        System.out.println("Welcome! Customizing Reservation: " + resId + "\n");

        // 3. Guest selects multiple services (One-to-Many relationship)
        addOnManager.addService(resId, breakfast);
        addOnManager.addService(resId, spa);

        // 4. Display and Calculate
        System.out.println("\n--- Final Summary ---");
        addOnManager.displayAddOns(resId);

        double extraCost = addOnManager.calculateTotalExtra(resId);
        System.out.println("Total Additional Cost: $" + extraCost);

        System.out.println("\nCore booking logic remained untouched during this selection.");
    }
}