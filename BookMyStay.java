import java.util.HashMap;
import java.util.Map;

/**
 * --- DOMAIN LAYER ---
 * Abstract class representing the blueprint of a Room.
 */
abstract class Room {
    private String type;
    private int beds;
    private double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() { return type; }

    public void displayDetails() {
        System.out.println("[" + type + "] Beds: " + beds + " | Rate: $" + price + "/night");
    }
}

class SingleRoom extends Room { public SingleRoom() { super("Single", 1, 100.0); } }
class DoubleRoom extends Room { public DoubleRoom() { super("Double", 2, 180.0); } }
class SuiteRoom  extends Room { public SuiteRoom()  { super("Suite",  3, 350.0); } }

/**
 * --- INVENTORY LAYER ---
 * Manages the state (how many rooms are left).
 */
class RoomInventory {
    private Map<String, Integer> stock = new HashMap<>();

    public void addStock(String type, int count) {
        stock.put(type, count);
    }

    public int getAvailableCount(String type) {
        return stock.getOrDefault(type, 0);
    }
}

/**
 * --- SERVICE LAYER ---
 * Logic for searching without mutating state.
 */
class SearchService {
    private RoomInventory inventory;
    private Map<String, Room> catalog;

    public SearchService(RoomInventory inventory, Map<String, Room> catalog) {
        this.inventory = inventory;
        this.catalog = catalog;
    }

    public void showAvailableOptions() {
        System.out.println("\n--- Live Room Availability ---");
        boolean found = false;

        for (Room room : catalog.values()) {
            int count = inventory.getAvailableCount(room.getType());

            // Only show rooms that are actually in stock
            if (count > 0) {
                room.displayDetails();
                System.out.println(">>> Vacancy: " + count + " rooms left.");
                found = true;
            }
        }

        if (!found) System.out.println("No rooms available at this time.");
        System.out.println("-------------------------------\n");
    }
}

/**
 * --- APPLICATION ENTRY POINT ---
 */
public class UseCase4RoomSearch {
    public static void main(String[] args) {
        // 1. Initialize Objects (The "What")
        Map<String, Room> catalog = new HashMap<>();
        catalog.put("Single", new SingleRoom());
        catalog.put("Double", new DoubleRoom());
        catalog.put("Suite", new SuiteRoom());

        // 2. Initialize Inventory (The "How Many")
        RoomInventory inventory = new RoomInventory();
        inventory.addStock("Single", 5);
        inventory.addStock("Double", 0); // Sold out!
        inventory.addStock("Suite", 2);

        // 3. Initialize Search (The "Access")
        SearchService search = new SearchService(inventory, catalog);

        // 4. Execute
        System.out.println("Welcome to Grand Stay Manager v1.0");
        search.showAvailableOptions();

        System.out.println("Search complete. No inventory was modified.");
    }
}