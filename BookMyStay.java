import java.util.HashMap;
import java.util.Map;

/**
 * Manages the availability of different room types using a centralized HashMap.
 * Provides O(1) lookup and update performance.
 */
class RoomInventory {
    // Key: Room Type Name, Value: Number of rooms available
    private Map<String, Integer> inventory;

    public RoomInventory() {
        this.inventory = new HashMap<>();
    }

    /**
     * Registers a room type with an initial stock count.
     */
    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    /**
     * Updates the count for a specific room type (e.g., after a booking).
     */
    public void updateAvailability(String type, int change) {
        if (inventory.containsKey(type)) {
            int currentCount = inventory.get(type);
            inventory.put(type, currentCount + change);
        }
    }

    /**
     * Retrieves the current count for a room type.
     */
    public int getCount(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void displayInventory() {
        System.out.println("--- Current Inventory State ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " available");
        }
    }
}