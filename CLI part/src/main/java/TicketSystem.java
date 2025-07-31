
import java.util.InputMismatchException;
import java.util.Scanner;

public class TicketSystem {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        Scanner userInput = new Scanner(System.in);



        while (true) {
            try {
                System.out.println("------------------------------------------");
                System.out.println("* Welcome to Real-Time ticket simulation *");
                System.out.println("------------------------------------------");
                System.out.println();
                configuration.startSystem();  // Configuring the system

                while (true) {
                    System.out.println("--------------------------------------");
                    System.out.println("1.) 'Start' to start the ticketing: ");
                    System.out.println("2.) 'Stop' to stop the ticketing: ");
                    System.out.println("3.) 'Exit' to exit the system: ");
                    System.out.println("--------------------------------------");
                    System.out.print("Your choice: ");
                    String choice = userInput.next().toLowerCase();

                    switch (choice) {
                        case "start":
                            configuration.startTicketing();  // Start the ticketing system
                            break;
                        case "stop":
                            configuration.stopTicketing();  // Stop ticketing
                            break;
                        case "exit":
                            configuration.exitSystem();  // Exit the system
                            return;
                        default:
                            System.out.println("Invalid choice! Please try again.");
                    }
                }
            } catch (InputMismatchException ime) {
                System.out.println("Invalid input! Please try again.");
                userInput.nextLine();  // Clear the buffer
            }
        }
    }
}
