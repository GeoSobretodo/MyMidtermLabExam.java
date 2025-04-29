import java.util.Arrays; // Import the Array class for user input.
import java.util.Scanner; // Import the Scanner class.

public class TicketManagementSystem { // // Our Main class declaration for the ticket management system.

    private static final int MAX_TICKETS = 5; // Maximum number of tickets allowed that user to input.
    private static Ticket[] tickets = new Ticket[MAX_TICKETS]; // Array to hold ticket objects
    private static int ticketCount = 0; // To track the numbers of ticket that currently in the system.
    private static Scanner scanner = new Scanner(System.in); // Scanner object for user input.

    public static void main(String[] args) { // Main method.
        int choice; // Variable to store user's choice.
        // Main menu loop using a do-while loop.
        do {
            displayMenu(); // Display the main menu options.
            choice = scanner.nextInt(); // Read user's choice.
            scanner.nextLine(); // Consume the newline.

            switch (choice) { // Switch statement to handle different choices.
                case 1:
                    addTicket(); // Call the addTicket() method.
                    break;
                case 2:
                    updateTicketStatus(); // Call the updateTicketStatus() method.
                    break;
                case 3:
                    showAllTickets(); // Call the showAllTickets() method.
                    break;
                case 4:
                    generateReport(); // Call the generateReport() method.
                    break;
                case 5:
                    System.out.println("Exiting program. Goodbye!"); // Prints exit message.
                    break; // Exit the switch statement.
                default:
                    System.out.println("Invalid menu option. Please try again."); // Handle invalid input.
            }
        } while (choice != 5); // Continues loop until the user chooses to exit.
        scanner.close(); // Close the scanner to release resources.
    }

    // Method to display the main menu
    static void displayMenu() {
        System.out.println("\n=== IT Ticket System ==="); // Prints the menu title.
        // Printing options (1-5).
        System.out.println("1. Add Ticket");
        System.out.println("2. Update Ticket Status"); 
        System.out.println("3. Show All Tickets"); 
        System.out.println("4. Generate Report"); 
        System.out.println("5. Exit"); 
        System.out.print("Enter your choice: "); // Prompt the user for input.
    }

    static void addTicket() { // Method to add a new ticket.
        if (ticketCount >= MAX_TICKETS) { // Check if the maximum number of tickets is reached.
            System.out.println("Maximum number of tickets reached."); // Prints the message.
            return; 
        }

        System.out.print("Enter issue description: "); // Prompt for issue description.
        String description = scanner.nextLine(); // Read the issue description.
        System.out.print("Enter urgency level (Low, Medium, High): "); // Prompt for urgency level.
        String urgency = scanner.nextLine(); // Read the urgency level.

        tickets[ticketCount] = new Ticket(ticketCount + 1, description, urgency, "Pending"); // Create a new ticket object.
        ticketCount++; // Increment the ticket count
        System.out.println("Ticket added successfully."); // Print success message.
    }

    static void updateTicketStatus() { // Method to update ticket status.
        showAllTickets(); // Display all tickets first.
        System.out.print("Enter the ticket number to update: ");
         int ticketNumber = scanner.nextLine(); // Consume newline.

        if (ticketNumber < 1 || ticketNumber > ticketCount || tickets[ticketNumber - 1].getStatus().equals("Resolved")) { // Validate the user's input.
            System.out.println("Invalid ticket number or status."); // Prints error message for invalid input.
            return; 
        }

        System.out.print("Enter new status (In Progress, Resolved): "); 
        String newStatus = scanner.nextLine();
        tickets[ticketNumber - 1].setStatus(newStatus); // Update the ticket status.
        System.out.println("Ticket status updated successfully.");
    }

    static void showAllTickets() { // Method to show all tickets.
        if (ticketCount == 0) { // Check if there are any tickets.
            System.out.println("No tickets in the system."); // Prints message if no tickets.
            return;
        }
        System.out.println("\nAll Tickets:"); // Printing our header.
        for (int i = 0; i < ticketCount; i++) { // Loop through the tickets array.
            System.out.println(tickets[i]); // Print each ticket's details.
        }
    }

    static void generateReport() { // Method to generate report.
        if (ticketCount == 0) { // Check again if there are any tickets.
            System.out.println("No tickets to generate report from."); // Prints message if no tickets.
            return;
        }
        int pending = 0; // A Variable to count pending tickets.
        int resolved = 0; // A Variable to count resolved tickets.
        for (int i = 0; i < ticketCount; i++) { 
            if (tickets[i].getStatus().equals("Pending") || tickets[i].getStatus().equals("In Progress")) { // Checking for pending tickets.
                pending++; 
            } else if (tickets[i].getStatus().equals("Resolved")) { // Checking for resolved tickets.
                resolved++; 
            }
        }
        System.out.println("\nTicket Report:"); // Prints report header.
        System.out.println("Total Tickets: " + ticketCount); // Prints the total number of tickets.
        System.out.println("Pending Tickets: " + pending); // Prints the number of pending tickets.
        System.out.println("Resolved Tickets: " + resolved); // Prints the number of resolved tickets.
    }

    // Inner class to represent a ticket.
    static class Ticket { // Inner class declaration
        private int ticketNumber; 
        private String description;
        private String urgency; 
        private String status;

        public Ticket(int ticketNumber, String description, String urgency, String status) { // Constructor for the Ticket class.
            // Initialize ticket number, issue description, urgency level, and ticket current status.
            this.ticketNumber = ticketNumber; 
            this.description = description; 
            this.urgency = urgency;
            this.status = status;
        }

        public String getStatus() { // Getter method for ticket status.
            return status; // Return the ticket status.
        }

        public void setStatus(String status) { // Setter method.
            this.status = status; // Set the ticket status.
        }

        @Override
        public String toString() { // Override the toString() method for easy printing ticket details.
            return "Ticket #" + ticketNumber + ": Description = " + description + ", Urgency = " + urgency + ", Status = " + status; // Return a string representation of the ticket.
        }
    }
}
