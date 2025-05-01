import java.util.Scanner;

public class TicketManagementSystem {
    private static final int MAX_TICKETS = 5;
    private static Ticket[] tickets = new Ticket[MAX_TICKETS];
    private static int ticketCount = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            displayMenu();
            choice = getValidIntInput();

            switch (choice) {
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
        } while (choice != 5);
        scanner.close();
    }

    static void displayMenu() {
        System.out.println("\n=== IT Ticket System ===");
        System.out.println("1. Add Ticket");
        System.out.println("2. Update Ticket Status");
        System.out.println("3. Show All Tickets");
        System.out.println("4. Generate Report");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    static int getValidIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
        }
        return scanner.nextInt();
    }

    static void addTicket() {
        if (ticketCount >= MAX_TICKETS) {
            System.out.println("Maximum number of tickets reached.");
            return;
        }

        scanner.nextLine();

        String description = "";
        while (description.trim().isEmpty()) {
            System.out.print("Enter issue description: ");
            description = scanner.nextLine();
            if (description.trim().isEmpty()) {
                System.out.println("Description cannot be empty. Please enter a valid description.");
            }
        }

        String urgency = "";
        boolean validUrgency = false;
        while (!validUrgency) {
            System.out.print("Enter urgency level (Low, Medium, High): ");
            urgency = scanner.nextLine().trim().toLowerCase();

            switch (urgency) {
                case "low":
                case "medium":
                case "high":
                    validUrgency = true;
                    break;
                default:
                    System.out.println("Invalid urgency level. Please enter one of the following: Low, Medium, or High.");
            }
        }

        tickets[ticketCount] = new Ticket(ticketCount + 1, description, urgency, "Pending");
        ticketCount++;
        System.out.println("Ticket added successfully.");
    }

    static void updateTicketStatus() {
        if (!showAllTickets()) {
            System.out.print("Enter the ticket number to update: ");
            int ticketNumber = scanner.nextInt();

            boolean validTicketNumber = false;
            while (!validTicketNumber) {
                if (ticketNumber < 1 || ticketNumber > ticketCount || tickets[ticketNumber - 1].getStatus().equals("Resolved")) {
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

                if (newStatus.equals("in progress") || newStatus.equals("resolved")) {
                    validStatus = true;
                    tickets[ticketNumber - 1].setStatus(newStatus.substring(0, 1).toUpperCase() + newStatus.substring(1)); // Capitalize the first letter
                    System.out.println("Ticket status updated successfully.");
                } else {
                    System.out.println("Invalid input. Please enter either 'In Progress' or 'Resolved'.");
                }
            }
        }
    }


    static boolean showAllTickets() {
        boolean isEmpty = true;
        if (ticketCount == 0) {
            System.out.println("No tickets in the system.");
        } else {
            isEmpty = false;
            System.out.println("\nAll Tickets:");
            for (int i = 0; i < ticketCount; i++) {
                if (!tickets[i].getStatus().equals("Resolved")) {
                    System.out.println(tickets[i]);
                }
            }
        }
        return isEmpty;
    }

    static void generateReport() {
        if (ticketCount == 0) {
            System.out.println("No tickets to generate report from.");
            return;
        }
        int pending = 0;
        int resolved = 0;
        for (int i = 0; i < ticketCount; i++) {
            if (tickets[i].getStatus().equals("Pending") || tickets[i].getStatus().equals("In Progress")) {
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

    static class Ticket {
        private int ticketNumber;
        private String description;
        private String urgency;
        private String status;

        public Ticket(int ticketNumber, String description, String urgency, String status) {
            this.ticketNumber = ticketNumber;
            this.description = description;
            this.urgency = urgency;
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "[" + urgency + "] - " + description + " - Status: " + status;
        }
    }
}
