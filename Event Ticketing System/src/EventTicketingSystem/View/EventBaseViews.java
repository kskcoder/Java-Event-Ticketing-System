package EventTicketingSystem.View;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import EventTicketingSystem.Helpers.InputHelper;
import EventTicketingSystem.Model.Event;
import EventTicketingSystem.Model.Event.EventStatus;

public abstract class EventBaseViews {
        public int viewEventMenu() {
        while(true) {
            System.out.println("\nSelect an option to continue: \n1. View All Events \n2. View Events under Price \n3. View Events by Name \n4. View Events by Id \n5. View Events by Status \n6. View Events by Date \n7. Exit \n");            
            int option = InputHelper.getInt("Enter Choice: ");
            System.out.println();
    
            switch (option) {
                case 1,2,3,4,5,6,7:
                    return option;
                default:
                    System.out.println("Please enter a valid choice!");
                    System.out.println();
            }
        }
    }    

    public int viewEventsUnderPrice() {
        System.out.println();
        int price = InputHelper.getInt("Enter price(in Rupees): ");
        return price;
    }

    public String viewEventsByName() {
        System.out.println();
        String name = InputHelper.getNonEmptyString("Enter name: ");
        return name;
    }

    public int viewEventsById() {
        System.out.println();
        int id = InputHelper.getInt("Enter Id: ");
        return id;
    }

    public EventStatus viewEventsByStatus() {
        System.out.println();
        System.out.print("Enter Event Status: ");
        Event.EventStatus eventStatus = null;

        while (eventStatus == null) {
            System.out.println("Select Event Status: \n1. Upcoming \n2. Cancelled \n3. Completed \n4. Postponed");
            int input = InputHelper.getInt("Enter choice: ");
            switch (input) {
                case 1:
                    return EventStatus.UPCOMING;
                case 2:
                    return EventStatus.CANCELLED;
                case 3:
                    return EventStatus.COMPLETED;
                case 4:
                    return EventStatus.POSTPONED;
                default:
                    System.out.println("Invalid status please enter Valid choice");
            }
        }
        return EventStatus.UPCOMING;
    }

    public LocalDateTime viewEventsByDate() {
        LocalDateTime dateTime = null;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        while (dateTime == null) {            
            String input = InputHelper.getNonEmptyString("Enter Event Date(dd/MM/yyyy HH:mm): ");
            try {
                dateTime = LocalDateTime.parse(input, dateFormat);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please enter again");
            }
        }

        return dateTime;
    }

    public void showSingleEvent(Event event, int totalEvents) {
        System.out.println("Following are the results:");
        System.out.println();

        System.out.println(event);
        System.out.println("Showing 1 out of total "+totalEvents+" events.");
        return;
    }

    public void showEventsList(List<Event> events, int totalEvents) {
        System.out.println("Following are the results:");
        System.out.println();

        for (Event event: events) {
            System.out.println(event);
        }
        System.out.println("Showing "+events.size()+" out of total "+totalEvents+" events.");
        return;
    }

    //Show Events Menu
    public void noEventFound() {
        System.out.println("No Events Found!");
        return;
    }

    public abstract int showMainMenu();
}
