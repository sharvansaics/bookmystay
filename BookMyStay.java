/**
 * Abstract class representing a generic Room.
 * Defines the shared structure for all specialized room types.
 */
abstract class Room {
    private String roomType;
    private int numBeds;
    private double pricePerNight;

    public Room(String roomType, int numBeds, double pricePerNight) {
        this.roomType = roomType;
        this.numBeds = numBeds;
        this.pricePerNight = pricePerNight;
    }

    public void displayDetails() {
        System.out.println("Type: " + roomType + " | Beds: " + numBeds + " | Price: $" + pricePerNight);
    }
}

// Concrete Implementations
class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 1, 100.0); }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 2, 150.0); }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Luxury Suite", 3, 350.0); }
}