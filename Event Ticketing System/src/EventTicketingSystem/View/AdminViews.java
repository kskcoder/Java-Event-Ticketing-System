package EventTicketingSystem.View;

import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import EventTicketingSystem.Helpers.InputHelper;
import EventTicketingSystem.Model.Event;
import EventTicketingSystem.Model.Event.EventStatus;

public class AdminViews extends EventBaseViews{

    public int showMainMenu() {
        while (true) {
            System.out.println("\n\nSelect an option to continue: \n\n1. Create Admin \n2. Events Management \n3. Bookings Management \n4. User Management \n5. Log Out \n");
            int option = InputHelper.getInt("Enter Choice: ");
    
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
            int option = InputHelper.getInt("Enter Choice: ");
    
            switch (option) {
                case 1,2,3,4,5:
                    return option;
                default:
                    System.out.println("Please enter a valid choice!");
                    System.out.println();
            }
        }
    }

    //Show Events Menu

    public void deletedSuccessfully() {
        System.out.println("Event Deleted Successfully!");
        return;
    }

    //Update event functions

    public int updateEventMenu() {
        while(true) {
            System.out.println("\n Select an option to continue: \n1. Update Event Name \n2. Update Event Venue \n3. Update Event Date \n4. Update Event Price \n5. View Events by Status \n6. Exit");
            
            int option = InputHelper.getInt("Enter Choice: ");
    
            switch (option) {
                case 1,2,3,4,5,6:
                    return option;
                default:
                    System.out.println("Please enter a valid choice!");
                    System.out.println();
            }
        }
    }

    public int getEventId() {
        return InputHelper.getInt("Enter Event Id: ");
    }

    public String getEventNameToUpdate() {
        return InputHelper.getNonEmptyString("Enter new name for event: ");
    }

    public String getEventVenueToUpdate() {
        return InputHelper.getNonEmptyString("Enter new venue for event: ");
    }

    public LocalDateTime getEventDateToUpdate() {
        LocalDateTime dateTime = null;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        while (dateTime == null) {
            String input = InputHelper.getNonEmptyString("Enter new date for event(dd/MM/yyyy HH:mm): ");
            try {
                dateTime = LocalDateTime.parse(input, dateFormat);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format! Please enter again");
            }
        }
        return dateTime;
    }

    public int getEventPriceToUpdate() {
        return InputHelper.getInt("Enter new price for event(in Rupees): ");
    }

    public Event.EventStatus getEventStatusToUpdate() {
        Event.EventStatus eventStatus = null;

        while (eventStatus == null) {
            System.out.println("Select New Status for the event: \n1. Upcoming \n2. Cancelled \n3. Completed \n4. Postponed");
            int input = InputHelper.getInt("Enter choice: ");
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
        return eventStatus;
    }

    public void showUpdatedMessage() {
        System.out.println("Event updated successfull! \nUpdated Event below: ");
    }

    //Create Event
    public Event createNewEvent() {        
        System.out.println();
        String eventName = InputHelper.getNonEmptyString("Enter Event Name: ");

        String eventVenue = InputHelper.getNonEmptyString("Enter Event Venue: ");
        
        LocalDateTime dateTime = null;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        while (dateTime == null) {
            String input = InputHelper.getNonEmptyString("Enter Event Date(dd/MM/yyyy HH:mm): ");
            try {
                dateTime = LocalDateTime.parse(input, dateFormat);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format! Please enter again");
            }
        }

        int setEventPrice = InputHelper.getInt("Enter Event price: ");

        int eventTotalTickets = InputHelper.getInt("Enter Event Tickets: ");

        int eventTotalTicketsAvailable = InputHelper.getInt("Enter Event Tickets Available: ");

        System.out.print("Enter Event Status: ");
        Event.EventStatus eventStatus = null;

        while (eventStatus == null) {
            System.out.println("Select Event Status: \n1. Upcoming \n2. Cancelled \n3. Completed \n4. Postponed");
            int input = InputHelper.getInt("Enter choice: ");
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

