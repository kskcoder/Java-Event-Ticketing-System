package EventTicketingSystem.View;

import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import EventTicketingSystem.Helpers.InputHelper;
import EventTicketingSystem.Model.Event;
import EventTicketingSystem.Model.User;
import EventTicketingSystem.Model.Event.EventStatus;

public class AdminViews extends EventBaseViews{

    public int showMainMenu() {
        while (true) {
            System.out.println("\n\nSelect an option to continue: \n\n1. Create Admin \n2. Events Management \n3. User Management \n4. Log Out \n5. Delete Account\n");
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

    //User Management Menu

    public int showUserManagementMenu() {
        System.out.println("Select an option to continue: \n\n1. Show All Users(Admin & Users) \n2. Show All Admin \n3. Show All Users \n4. Exit\n");

        while(true) {            
            int option = InputHelper.getInt("Enter Choice: ");
    
            switch (option) {
                case 1,2,3,4:
                    return option;
                default:
                    System.out.println("Please enter a valid choice!");
                    System.out.println();
            }
        }
    }

    public void showUsersList(List<User> users) {
        System.out.println("Below are the results: ");
        System.out.println();

        System.out.println("---------------------------");
        for (int i = 0; i < users.size(); i++) {
            System.out.println(i+1+".\n"+users.get(i));
            System.out.println("---------------------------");
        }
        System.out.println("Showing "+users.size()+" out of total users.");
        System.out.println();
        return;
    }

    public boolean askToShowTickets() {
        System.out.println();
        return InputHelper.getNonEmptyString("View Tickets of users?(Y/y for Yes) ").toLowerCase().equals("y");
    }

    public boolean askToDeleteUsers() {
        System.out.println();
        return InputHelper.getNonEmptyString("Delete users?(Y/y for Yes) ").toLowerCase().equals("y");
    }

    public String enterUserName() {
        System.out.println();
        return InputHelper.getNonEmptyString("Enter exact user name without spaces): ");
    }

    public void userNotFound() {
        System.out.println();
        System.out.println("User Not Found!");
        System.out.println();
        return;
    }

    public void cannotPerformActionMessage() {
        System.out.println();
        System.out.println("Cannot Perform This Action!");
        System.out.println();
        return;
    }
}

