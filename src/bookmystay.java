import java.util.Scanner;

public class BookMyStay {

    public static void main(String[] args) {
        // 1️⃣ Display welcome banner
        System.out.println("===================================");
        System.out.println(" Welcome to BookMyStay Application ");
        System.out.println(" Version: 1.0.0 ");
        System.out.println("===================================");

        // 2️⃣ Ask for user name
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your name: ");
        String userName = scanner.nextLine();

        // 3️⃣ Greet user
        System.out.println("Hello, " + userName + "! Thank you for choosing BookMyStay.");

        // 4️⃣ Show main menu
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. View Hotels");
        System.out.println("2. Book a Room");
        System.out.println("3. Exit");

        System.out.print("Enter your choice (1-3): ");
        int choice = scanner.nextInt();

        // 5️⃣ Handle menu choice
        switch (choice) {
            case 1:
                System.out.println("Fetching available hotels...");
                // Future: call method to display hotels
                break;
            case 2:
                System.out.println("Redirecting to booking module...");
                // Future: call booking logic
                break;
            case 3:
                System.out.println("Thank you! Exiting application.");
                break;
            default:
                System.out.println("Invalid choice! Please restart the application.");
        }

        // 6️⃣ Close scanner and terminate
        scanner.close();
        System.out.println("Application has terminated.");
    }
}