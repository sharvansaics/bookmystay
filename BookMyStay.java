import java.util.Scanner;

public class BookMyStay {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("     Welcome to Book My Stay     ");
        System.out.println("=================================");

        // Get user input
        System.out.print("Enter Location: ");
        String location = sc.nextLine();

        System.out.print("Enter Check-in Date (dd-mm-yyyy): ");
        String checkIn = sc.nextLine();

        System.out.print("Enter Check-out Date (dd-mm-yyyy): ");
        String checkOut = sc.nextLine();

        System.out.print("Enter Number of Guests: ");
        int guests = sc.nextInt();

        // Display search summary
        System.out.println("\nSearching available stays...");
        System.out.println("---------------------------------");
        System.out.println("Location: " + location);
        System.out.println("Check-in: " + checkIn);
        System.out.println("Check-out: " + checkOut);
        System.out.println("Guests: " + guests);

        // Dummy hotel data
        System.out.println("\nAvailable Stays:");
        System.out.println("1. Luxury Hotel - ₹4500/night");
        System.out.println("2. Beach Resort - ₹6200/night");
        System.out.println("3. City Apartment - ₹3000/night");

        System.out.print("\nSelect a hotel (1-3): ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                System.out.println("You selected Luxury Hotel.");
                break;
            case 2:
                System.out.println("You selected Beach Resort.");
                break;
            case 3:
                System.out.println("You selected City Apartment.");
                break;
            default:
                System.out.println("Invalid choice.");
        }

        System.out.println("\nBooking successful! Thank you for using Book My Stay.");
        sc.close();
    }
}