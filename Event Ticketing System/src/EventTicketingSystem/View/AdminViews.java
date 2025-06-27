package EventTicketingSystem.View;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import EventTicketingSystem.Model.Event;
import EventTicketingSystem.Model.Event.EventStatus;

public class AdminViews {
    Scanner sc = new Scanner(System.in);

    public int showAdminMenu() {
        while (true) {
            System.out.println("\n\nSelect an option to continue: \n\n1. Create Admin \n2. Events Management \n3. Bookings Management \n4. User Management \n5. Log Out \n");
            System.out.print("Enter Choice: ");

            int option = sc.nextInt();
    
            switch (option) {
                case 1,2,3,4,5:
                    return option;
                default:
                    System.out.println("Please enter a valid choice!");
                    System.out.println();
            }
        }
    }

    public void adminLoggedOutMessage() {
        System.out.println();
        System.out.println("Admin logged out successfully!");
        System.out.println();
    }    

    public int showEventManagementMenu() {
        while (true) {
            System.out.println("\nSelect an option to continue: \n1. Create Event \n2. View Events Menu \n3. Update Event Menu \n4. Delete Event \n5. Exit\n");
            System.out.print("Enter Choice: ");

            int option = sc.nextInt();
    
            switch (option) {
                case 1,2,3,4:
                    return option;
                case 5:
                    break;
                default:
                    System.out.println("Please enter a valid choice!");
                    System.out.println();
            }
        }
    }

    public int viewEventMenu() {
        while(true) {
            System.out.println("\n Select an option to continue: \n1. View Events by Under Price \n2. View All Events \n3. View Events by Name \n4. View Events by Id \n5. View Events by Status \n6. View Events by Date \n7. View Events by Under Price \n8. Exit");
            
            int option = sc.nextInt();
    
            switch (option) {
                case 1,2,3,4,5,6,7:
                    return option;
                case 8:
                    break;
                default:
                    System.out.println("Please enter a valid choice!");
                    System.out.println();
            }
        }
    }

    public int updateEventMenu() {
        while(true) {
            System.out.println("\n Select an option to continue: \n1. Update Event Name \n2. Update Event Venue \n3. Update Event Date \n4. Update Event Price \n5. View Events by Status \n6. Exit");
            
            int option = sc.nextInt();
    
            switch (option) {
                case 1,2,3,4,5:
                    return option;
                case 6:
                    break;
                default:
                    System.out.println("Please enter a valid choice!");
                    System.out.println();
            }
        }
    }

    public Event createNewEvent() {        
        sc.nextLine(); 
        System.out.println();
        System.out.print("Enter Event Name: ");
        String eventName = sc.nextLine();

        System.out.print("Enter Event Venue: ");
        String eventVenue = sc.nextLine();
        
        LocalDateTime dateTime = null;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        while (dateTime == null) {
            System.out.print("Enter Event Date(dd/MM/yyyy HH:mm): ");
            String input = sc.nextLine();
            try {
                dateTime = LocalDateTime.parse(input, dateFormat);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format please enter again!");
                System.out.println();
            }
        }

        System.out.print("Enter Event price: ");
        int setEventPrice = sc.nextInt();

        System.out.print("Enter Event Tickets: ");
        int eventTotalTickets = sc.nextInt();

        System.out.print("Enter Event Tickets Available: ");
        int eventTotalTicketsAvailable = sc.nextInt();

        System.out.print("Enter Event Status: ");
        Event.EventStatus eventStatus = null;

        while (eventStatus == null) {
            System.out.println("Select Event Status: \n1. Upcoming \n2. Cancelled \n3. Completed \n4. Postponed");
            System.out.print("Enter choice: ");
            int input = sc.nextInt();
            switch (input) {
                case 1:
                    eventStatus = EventStatus.UPCOMING;
                    break;
                case 2:
                    eventStatus = EventStatus.CANCELLED;
                    break;
                case 3:
                    eventStatus = EventStatus.COMPLETED;
                    break;
                case 4:
                    eventStatus = EventStatus.POSTPONED;
                    break;
                default:
                    System.out.println("Invalid status please enter Valid choice");
            }
        }

        return new Event(eventName, eventVenue, dateTime, setEventPrice, eventTotalTickets, eventTotalTicketsAvailable, eventStatus);
    }

    public void eventAddedSuccessfullyMessage() {
        System.out.println();
        System.out.println("Event Added Successfully!");
        System.out.println();
    }

    public void eventExistsMessage() {
        System.out.println();
        System.out.println("Event Already Exists!");
        System.out.println();
    }
}

