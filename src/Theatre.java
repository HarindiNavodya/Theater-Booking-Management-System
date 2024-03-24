import java.io.*;
import java.util.*;

public class Theatre {
    private static final int ROW1_SEATS = 12;
    private static final int ROW2_SEATS = 16;
    private static final int ROW3_SEATS = 20;
    private static ArrayList<Ticket> tickets = new ArrayList<>();


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int[][] seatingArea = new int[3][];
        seatingArea[0] = new int[ROW1_SEATS];
        seatingArea[1] = new int[ROW2_SEATS];
        seatingArea[2] = new int[ROW3_SEATS];

        System.out.println("Welcome to the New Theatre");

        int option = -1;
        while (option != 0) {
            System.out.println("----------------------------------------------------------------");
            System.out.println("Please select an option:");
            System.out.println("1) Buy a ticket");
            System.out.println("2) Print seating area");
            System.out.println("3) Cancel ticket");
            System.out.println("4) List available seats");
            System.out.println("5) Save to file");
            System.out.println("6) Load from file");
            System.out.println("7) Print ticket information and total price");
            System.out.println("8) Sort tickets by price");
            System.out.println("0) Quit");
            System.out.println("-----------------------------------------------------------------");
            System.out.print("Enter option: ");

            option = scanner.nextInt();
            switch (option) {
                case 0:
                    System.out.println("Thank you for using the New Theatre program.");
                    break;
                case 1:
                    buy_ticket(seatingArea, tickets);
                    break;
                case 2:
                    print_seating_area(seatingArea);
                    break;
                case 3:
                    cancel_ticket(seatingArea,tickets);
                    break;
                case 4:
                    show_available(seatingArea);
                    break;
                case 5:
                    save(seatingArea,tickets);
                    break;
                case 6:
                    load(seatingArea,tickets);
                    break;
                case 7:
                    show_tickets_info(tickets);
                    break;
                case 8:
                    sort_tickets(tickets);
                    break;
                default:
                    System.out.println("Invalid option selected. Please try again.");
                    break;
            }
        }
    }

    private static void buy_ticket(int[][] seats, ArrayList<Ticket> tickets) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Buying a ticket...");

        // get row and seat number
        int row = 0, seat = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Enter row number (1-3): ");
                row = scanner.nextInt() - 1;
                if (row < 0 || row > 2) {
                    System.out.println("Invalid row number. Please enter a number between 1 and 3.");
                    continue;
                }
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                scanner.nextLine(); // consume the invalid input
            }
        }
        validInput = false;
        while (!validInput) {
            try {
                System.out.print("Enter seat number (1-" + seats[row].length + "): ");
                seat = scanner.nextInt() - 1;
                if (seat < 0 || seat >= seats[row].length) {
                    System.out.println("Invalid seat number. Please enter a number between 1 and " + seats[row].length + ".");
                    continue;
                } else if (seats[row][seat] == 1) {
                    System.out.println("Sorry, this seat is already occupied. Please choose another seat.");
                    continue;
                }
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and " + seats[row].length + ".");
                scanner.nextLine(); // consume the invalid input
            }
        }

        // ask for person information
        scanner.nextLine(); // consume the newline character
        System.out.print("Enter person's name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter person's surname: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter person's email: ");
        String email = scanner.nextLine();

        // create new person object
        Person person = new Person(firstName, lastName, email);

        // calculate ticket price
        int price;
        switch (row) {
            case 0:
                price = 100;
                break;
            case 1:
                price = 80;
                break;
            default:
                price = 60;
        }

        // mark seat as occupied
        seats[row][seat] = 1;

        // create new ticket object
        Ticket ticket = new Ticket(row + 1, seat + 1, price, person);

        // add ticket to array list of tickets
        tickets.add(ticket);

        System.out.println("Ticket successfully purchased!");
    }



    public static void print_seating_area(int[][] seating_plan) {
        System.out.println("       ***********");
        System.out.println("       *  STAGE  *");
        System.out.println("       ***********");

        int number = 3;
        int count = number;
        for (int k = 0; k <= number - 1; k++) {
            for (int i = 1; i <= count; i++)
                System.out.print(" " + " ");
            int spaceCount = 4 * k + 12;
            count--;
            for (int i = 0; i < spaceCount; i++)
                if (seating_plan[k][i] == 0) {
                    System.out.print("O");
                    if ( i == (spaceCount - 1)/2){
                        System.out.print(" ");
                    }
                } else {
                    System.out.print("X");
                    if ( i == (spaceCount - 1)/2){
                        System.out.print(" ");
                    }
                }
            System.out.println();

        }
    }
    public static void cancel_ticket(int[][] seats, ArrayList<Ticket> tickets) {
        Scanner input = new Scanner(System.in);
        int row = 0;
        int seat = 0;

        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Enter the row number of the seat to cancel: ");
                row = input.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number for the row number.");
                input.next();
            }
        }
        do {
            try {
                System.out.print("Enter the seat number of the seat to cancel: ");
                seat = input.nextInt();

                // Check that the seat number is valid
                if (seat < 1 || seat > seats[row - 1].length) {
                    System.out.println("Invalid seat number.");
                    validInput = false;
                } else if (seats[row - 1][seat - 1] == 0) {
                    System.out.println("Seat is already available.");
                    validInput = false;
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number for the seat number.");
                input.next();
                validInput = false;
            }
        } while (!validInput);
        // Cancel the ticket by making the seat available
        seats[row - 1][seat - 1] = 0;

        // Remove the ticket from the ArrayList
        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            if (ticket.getRow() == row && ticket.getSeat() == seat) {
                tickets.remove(i);
                break;
            }
        }

        System.out.println("Ticket canceled successfully.");
}






    public static void show_available(int[][] seats) {
        for (int row = 1; row <= 3; row++) {
            System.out.print("Seats available in row " + row + ": ");
            List<Integer> availableSeats = new ArrayList<Integer>();
            for (int seat = 1; seat <= seats[row-1].length; seat++) {
                if (seats[row-1][seat-1] == 0) {
                    availableSeats.add(seat);
                }
            }
            System.out.println(availableSeats);
        }
    }


    public static void save(int[][] seats, ArrayList<Ticket> tickets) {
        try {
            FileWriter writer = new FileWriter("seats.txt");
            for (int i = 0; i < tickets.size(); i++) {
                Ticket ticket = tickets.get(i);
                writer.write("Row " + ticket.getRow() + ",Seat " + ticket.getSeat()+",");
                writer.write("Price " + ticket.getPrice() + ",");
                writer.write("FirstName: " + ticket.getPerson().getName() + ",surName: " + ticket.getPerson().getSurname() + ",");
                writer.write("Email: " + ticket.getPerson().getEmail() + "\n");
            }
            writer.close();
            System.out.println("Seats saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving seats to file.");
            e.printStackTrace();
        }
    }


    public static void load(int[][] seatingPlan, ArrayList<Ticket> tickets) {
        try {
            File file = new File("seats.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int row = Integer.parseInt(parts[0].substring(4));
                int seat = Integer.parseInt(parts[1].substring(5));
                int price = Integer.parseInt(parts[2].substring(6));
                String firstName = parts[3].substring(11);
                String surName = parts[4].substring(9);
                String email = parts[5].substring(7);
                Person person = new Person(firstName, surName, email);
                Ticket ticket = new Ticket(row, seat, price, person);
                // mark seat as occupied
                seatingPlan[row-1][seat-1] = 1;
                tickets.add(ticket);
            }

            System.out.println("Seating plan and ticket details loaded from file.");
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }



    private static void show_tickets_info(ArrayList<Ticket> tickets) {
        double totalPrice = 0;
        if (tickets.isEmpty()){
            System.out.println("NO TICKET INFORMATION AVAILABLE");
        }
        else {
            System.out.println("TICKET INFORMATION:\n");
            for (int i = 0; i < tickets.size(); i++) {
                Ticket ticket = tickets.get(i);
                ticket.print();
                totalPrice += ticket.getPrice();
            }
            System.out.printf("Total price: £%.2f\n", totalPrice);
        }
    }

    //uses nested for loops to compare each pair of tickets in the list and swap them if they are not in the correct order (i.e., if the first ticket's price is greater than the second ticket's price).
    //The outer loop runs from the first ticket to the second-to-last ticket
    //inner loop runs from the next ticket after the outer loop variable to the last ticket
    private static void sort_tickets(ArrayList<Ticket> tickets) {
        ArrayList<Ticket> sortedTickets = new ArrayList<>(tickets);
        if (sortedTickets.isEmpty()){
            System.out.println("No Tickets purchased.");
        }
        else {
            for (int i = 0; i < sortedTickets.size() - 1; i++) {
                for (int j = i + 1; j < sortedTickets.size(); j++) {
                    if (sortedTickets.get(i).getPrice() > sortedTickets.get(j).getPrice()) {
                        Ticket temp = sortedTickets.get(i);
                        sortedTickets.set(i, sortedTickets.get(j));
                        sortedTickets.set(j, temp);
                    }
                }
            }
            System.out.println("Sorted tickets by price (cheapest first):\n");
            for (Ticket ticket : sortedTickets) {
                ticket.print(); // print ticket info
            }
        }
    }
}