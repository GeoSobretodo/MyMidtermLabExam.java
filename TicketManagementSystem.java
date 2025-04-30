import java.util.Scanner; // Imports the scanner class.

public class TicketManagementSystem { // Declaring our Main class.
    private static final int MAX_TICKETS = 5; // Maximum number of tickets allowed that user to input.
    private static Ticket[] tickets = new Ticket[MAX_TICKETS]; // Array to hold ticket objects
    private static int ticketCount = 0;  // To track the numbers of ticket that currently in the system.
    private static Scanner scanner = new Scanner(System.in); // Scanner object for user input.
    

    public static void main(String[] args) {
        int choice; // Variable to store user's choice.
        do { // Main menu loop using a do-while loop.
            displayMenu();  // Display the main menu options.
            choice = getValidIntInput(); // Getting a valid integer input from the user.

            switch (choice) { // Using Switch Statement to handle different munu options.
                case 1:
                    addTicket();
                    break;
                case 2:
                    updateTicketStatus();
                    break;
                case 3:
                    showAllTickets();
                    break;
                case 4:
                    generateReport();
                    break;
                case 5:
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid menu option. Please try again.");
            }
        } while (choice != 5);  // Continues loop until the user chooses to exit.
        scanner.close(); // Closing the scanner to release resources.
    }

    static void displayMenu() { // A Method to display meunu Option.
        System.out.println("\n=== IT Ticket System ===");
        System.out.println("1. Add Ticket");
        System.out.println("2. Update Ticket Status");
        System.out.println("3. Show All Tickets");
        System.out.println("4. Generate Report");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    static int getValidIntInput() { // Getting a valid integer input from the user. 
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
        }
        return scanner.nextInt(); // Return the valid integer input.
    }

    static void addTicket() { 
        if (ticketCount >= MAX_TICKETS) { // Checking if the maximum number of tickets has been reached.
            System.out.println("Maximum number of tickets reached."); // Prints the message if the user reached the limit.
            return;
        }

        scanner.nextLine();

        String description = "";
        while (description.trim().isEmpty()) { // Using a while loop to ensure the description is not empty.
            System.out.print("Enter issue description: ");
            description = scanner.nextLine();
            if (description.trim().isEmpty()) { // Checks if the description is empty.
                System.out.println("Description cannot be empty. Please enter a valid description."); // Print an error message  if it is empty.
            }
        }

        String urgency = "";
        boolean validUrgency = false;
        while (!validUrgency) {  // Using a while loop to ensure the urgency level is valid.
            System.out.print("Enter urgency level (Low, Medium, High): ");
            urgency = scanner.nextLine().trim().toLowerCase();

            switch (urgency) { // We use again switch statement to check the validity of the urgency level.
                case "low":
                case "medium":
                case "high":
                    validUrgency = true;
                    break;
                default:
                    System.out.println("Invalid urgency level. Please enter one of the following: Low, Medium, or High."); // prints a succes message.
            }
        }

        tickets[ticketCount] = new Ticket(ticketCount + 1, description, urgency, "Pending"); // Create a new ticket object and add it to the array.
        ticketCount++; // Increment the ticket.
        System.out.println("Ticket added successfully."); // Prints success message.
    }

    static void updateTicketStatus() { // Using this Method to update tickets status.
        if (!showAllTickets()) { // Checking if there are any tickets in the system.
            System.out.print("Enter the ticket number to update: "); 
            int ticketNumber = scanner.nextInt();

            boolean validTicketNumber = false;
            while (!validTicketNumber) {
                if (ticketNumber < 1 || ticketNumber > ticketCount || tickets[ticketNumber - 1].getStatus().equals("Resolved")) { // Checks if the ticket number is valid and the ticket status is not "Resolved".
                    System.out.println("Invalid ticket number or status.");
                } else {
                    validTicketNumber = true;
                }
            }

            scanner.nextLine();

            String newStatus = "";
            boolean validStatus = false;

            while (!validStatus) {
                System.out.print("Enter new status (In Progress, Resolved): ");
                newStatus = scanner.nextLine().toLowerCase();

                if (newStatus.equals("in progress") || newStatus.equals("resolved")) { // Checks if the new status is valid and update the ticket status.
                    validStatus = true;
                    tickets[ticketNumber - 1].setStatus(newStatus.substring(0, 1).toUpperCase() + newStatus.substring(1)); // Capitalize the first letter.
                    System.out.println("Ticket status updated successfully.");
                } else {
                    System.out.println("Invalid input. Please enter either 'In Progress' or 'Resolved'.");
                }
            }
        }
    }


    static boolean showAllTickets() { // Display all tickets.
        boolean isEmpty = true;
        if (ticketCount == 0) { // Checks if there are any tickets in the system.
            System.out.println("No tickets in the system.");
        } else {
            isEmpty = false;
            System.out.println("\nAll Tickets:");
            for (int i = 0; i < ticketCount; i++) { // Iterate through the tickets array and prints each tickets. 
                System.out.println(tickets[i]);
            }
        }
        return isEmpty; // Return true if there are no tickets false otherwise.
    }

    static void generateReport() {
        if (ticketCount == 0) {
            System.out.println("No tickets to generate report from.");
            return;
        }
        int pending = 0;
        int resolved = 0;
        for (int i = 0; i < ticketCount; i++) { 
            if (tickets[i].getStatus().equals("Pending") || tickets[i].getStatus().equals("In Progress")) { // Checks the ticket status and increment the appropriate counter.
                pending++;
            } else if (tickets[i].getStatus().equals("Resolved")) {
                resolved++;
            }
        }
        System.out.println("\nTicket Report:");
        System.out.println("Total Tickets: " + ticketCount);
        System.out.println("Pending Tickets: " + pending);
        System.out.println("Resolved Tickets: " + resolved);
    }

    static class Ticket { // Inner class to represent a ticket.
        private int ticketNumber;
        private String description;
        private String urgency;
        private String status;

        public Ticket(int ticketNumber, String description, String urgency, String status) { // Constructor for the ticket class.
            this.ticketNumber = ticketNumber;
            this.description = description;
            this.urgency = urgency;
            this.status = status;
        }

        public String getStatus() { //Getter Method for ticket status.
            return status;
        }

        public void setStatus(String status) { // Setter Method for ticket status.
            this.status = status;
        }

        @Override // Override the toString() method for easy printing ticket details.
        public String toString() {
            return "Ticket #" + ticketNumber + ": Description = " + description + ", Urgency = " + urgency + ", Status = " + status;
        }
    }
}
