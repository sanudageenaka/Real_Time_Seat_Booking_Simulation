
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    private TicketPool ticketPool;
    private ExecutorService executorService;
    private AtomicBoolean isTicketingActive;

    public Configuration() {
        this.isTicketingActive = new AtomicBoolean(false);
    }

    // Method to configure system and validate inputs
    public void startSystem() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Please configure the ticketing system:");

            // Validate totalTickets input
            this.totalTickets = getValidInput("Enter Total Number of Tickets (totalTickets): ", "Total Tickets must be a positive number.");

            // Validate ticketReleaseRate input
            this.ticketReleaseRate = getValidInput("Enter Ticket Release Rate (ticketReleaseRate): ", "Ticket Release Rate must be a positive number.");

            // Validate customerRetrievalRate input
            this.customerRetrievalRate = getValidInput("Enter Customer Retrieval Rate (customerRetrievalRate): ", "Customer Retrieval Rate must be a positive number.");

            // Validate maxTicketCapacity input
            this.maxTicketCapacity = getValidInput("Enter Maximum Ticket Capacity (maxTicketCapacity): ", "Maximum Ticket Capacity must be a positive number.");

            // Check if totalTickets is greater than or equal to maxTicketCapacity
            if (this.totalTickets < this.maxTicketCapacity) {
                System.out.println("Error: Total Tickets cannot be less than Maximum Ticket Capacity. Please try again.");
                continue; // Re-prompt user for input
            }

            // Create a new TicketPool with the configured max capacity
            this.ticketPool = new TicketPool(maxTicketCapacity);
            System.out.println("System configuration completed successfully!");

            // Save configuration to a file
            writeSettingsToFile("settings.txt");
            break; // Exit the loop once configuration is valid
        }
    }

    // Method to write settings to a text file
    private void writeSettingsToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Total Tickets: " + totalTickets);
            writer.newLine();
            writer.write("Ticket Release Rate: " + ticketReleaseRate);
            writer.newLine();
            writer.write("Customer Retrieval Rate: " + customerRetrievalRate);
            writer.newLine();
            writer.write("Maximum Ticket Capacity: " + maxTicketCapacity);
            writer.newLine();
            System.out.println("Settings have been saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing settings to file: " + e.getMessage());
        }
    }

    // Helper method to handle valid input and ensure positive integer values
    private int getValidInput(String prompt, String errorMessage) {
        Scanner scanner = new Scanner(System.in);
        int value = -1;

        while (value <= 0) {
            try {
                System.out.print(prompt);
                value = scanner.nextInt();

                if (value <= 0) {
                    System.out.println(errorMessage);
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine(); // Clear the buffer
            }
        }

        return value;
    }

    public void startTicketing() {
        if (isTicketingActive.get()) {
            System.out.println("Ticketing is already active.");
            return;
        }

        isTicketingActive.set(true);
        executorService = Executors.newFixedThreadPool(4);

        // Synchronization using CountDownLatch to start customer threads only after the vendor finishes
        CountDownLatch latch = new CountDownLatch(1);

        // Start vendor thread (releases tickets)
        Vendor vendor = new Vendor(ticketPool, ticketReleaseRate, totalTickets, latch);
        executorService.submit(vendor);

        // Start customer threads after vendor finishes releasing tickets
        executorService.submit(() -> {
            try {
                latch.await(); // Wait for the vendor to finish
                for (int i = 0; i < 3; i++) { // Example: 3 customers
                    Customer customer = new Customer(ticketPool, customerRetrievalRate);
                    executorService.submit(customer);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        System.out.println("Ticketing started successfully!");
    }

    public void stopTicketing() {
        if (!isTicketingActive.get()) {
            System.out.println("Ticketing is not active.");
            return;
        }

        isTicketingActive.set(false);
        executorService.shutdown();
        System.out.println("Ticketing stopped successfully!");
    }

    public void exitSystem() {
        stopTicketing();
        System.out.println("Exiting the system. Goodbye!");
    }
}

