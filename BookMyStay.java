import java.util.*;

/**
 * --- DOMAIN LAYER ---
 */
abstract class Room {
    private String type;
    private double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }
    public String getType() { return type; }
}

class SingleRoom extends Room { public SingleRoom() { super("Single", 100.0); } }
class DoubleRoom extends Room { public DoubleRoom() { super("Double", 180.0); } }
class SuiteRoom  extends Room { public SuiteRoom()  { super("Suite",  350.0); } }

/**
 * --- DATA & STATE LAYER ---
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String name, String type) {
        this.guestName = name;
        this.roomType = type;
    }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

class RoomInventory {
    private Map<String, Integer> stock = new HashMap<>();

    public void addStock(String type, int count) { stock.put(type, count); }
    public int getCount(String type) { return stock.getOrDefault(type, 0); }
    public void updateStock(String type, int delta) {
        stock.put(type, getCount(type) + delta);
    }
}

/**
 * --- SERVICE LAYER ---
 */
class AllocationService {
    private RoomInventory inventory;
    private Queue<Reservation> queue = new LinkedList<>();
    // Set ensures no two guests get the same Room ID
    private Set<String> assignedRoomIds = new HashSet<>();

    public AllocationService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void enqueueRequest(Reservation res) {
        queue.add(res);
        System.out.println("[QUEUE] Added request for " + res.getGuestName());
    }

    public void processAllocations() {
        System.out.println("\n--- Processing Reservations ---");
        while (!queue.isEmpty()) {
            Reservation res = queue.poll(); // FIFO: First in, first out
            String type = res.getRoomType();

            if (inventory.getCount(type) > 0) {
                // Logic: Generate a unique ID based on current Set size
                String roomId = type.toUpperCase() + "-" + (100 + assignedRoomIds.size());

                if (assignedRoomIds.add(roomId)) {
                    inventory.updateStock(type, -1);
                    System.out.println("SUCCESS: " + res.getGuestName() + " booked " + roomId);
                }
            } else {
                System.out.println("FAILED: No " + type + " rooms left for " + res.getGuestName());
            }
        }
        System.out.println("--- Processing Complete ---\n");
    }
}

/**
 * --- MAIN ENTRY POINT ---
 */
public class BookMyStay {
    public static void main(String[] args) {
        // 1. Setup Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addStock("Single", 2);
        inventory.addStock("Suite", 1); // Only one luxury suite!

        // 2. Setup Service
        AllocationService allocationService = new AllocationService(inventory);

        // 3. Simulate High-Demand Intake
        allocationService.enqueueRequest(new Reservation("Alice", "Suite"));
        allocationService.enqueueRequest(new Reservation("Bob", "Suite")); // Should fail
        allocationService.enqueueRequest(new Reservation("Charlie", "Single"));
        allocationService.enqueueRequest(new Reservation("David", "Single"));

        // 4. Run Allocation
        allocationService.processAllocations();

        // 5. Final Status
        System.out.println("Final Suite Stock: " + inventory.getCount("Suite"));
    }
}