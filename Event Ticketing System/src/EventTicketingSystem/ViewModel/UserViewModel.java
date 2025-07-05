package EventTicketingSystem.ViewModel;

import java.time.LocalDateTime;
import java.util.List;

import EventTicketingSystem.Model.Event;
import EventTicketingSystem.Model.Ticket;
import EventTicketingSystem.Model.UserManager;
import EventTicketingSystem.Model.EventManager;
import EventTicketingSystem.View.UserViews;

public class UserViewModel {
    UserManager userManager;
    UserViews userViews;
    EventManager eventManager;

    public UserViewModel(UserManager userManager, UserViews userViews, EventManager eventManager) {
        this.userManager = userManager;
        this.userViews = userViews;
        this.eventManager = eventManager;
    }

    public void showUserFlow() {
        while (true) {
            switch (userViews.showMainMenu()) {
                case 1:
                    showEventsControl(userViews.viewEventMenu());
                    break;
                case 2:
                    userViews.showTicketsList(userManager.getAllUserBookedEvents(), userManager.getUserTicketsCount());
                    if (userManager.getUserTicketsCount() > 0 && userViews.askForCancellation()) {
                        Ticket ticket = userManager.getEventByTicketId(userViews.viewEventsById());
                        if (ticket != null) {
                            eventManager.updateBookedEvent(ticket.getRelatedEventId(), ticket.getBookedQuantity(), true);
                            userManager.cancelTicketOfUser(ticket);
                            userViews.ticketCancelledSuccessfully();
                        } else {
                            userViews.noEventFound();
                        }
                    }
                    break;
                case 3:
                    userManager.logOutUser();
                    userViews.userLoggedOutMessage();
                    return;
            }
        }
    }

        public void showEventsControl(int choice) {
        switch (choice) {
            case 1: {
                List<Event> events = eventManager.getAllEvents();
                userViews.showEventsList(events, events.size());
                showBookingOptionControl();
                break;
            }
            case 2: {
                int price = userViews.viewEventsUnderPrice();
                List<Event> events = eventManager.getEventsUnderPrice(price);
                userViews.showEventsList(events, eventManager.getTotalEventsCount());
                showBookingOptionControl();
                break;
            }
            case 3: {
                String name = userViews.viewEventsByName();
                Event event = eventManager.getEventByName(name);
                userViews.showSingleEvent(event, eventManager.getTotalEventsCount());
                showBookingOptionControl();
                break;
            }
            case 4: {
                int id = userViews.viewEventsById();
                Event event = eventManager.getEventById(id);
                userViews.showSingleEvent(event, eventManager.getTotalEventsCount());
                showBookingOptionControl();
                break;
            }
            case 5: {
                Event.EventStatus status = userViews.viewEventsByStatus();
                List<Event> events = eventManager.getEventByStatus(status);
                userViews.showEventsList(events, eventManager.getTotalEventsCount());
                showBookingOptionControl();
                break;
            }
            case 6: {
                LocalDateTime date = userViews.viewEventsByDate();
                List<Event> events = eventManager.getEventsByDate(date);
                userViews.showEventsList(events, eventManager.getTotalEventsCount());
                showBookingOptionControl();
                break;
            }          
        }
    }
    
    public int inputEventId() {
        int Id = userViews.getEventId();
        while (Id != -1 && eventManager.getEventById(Id) == null) {
            userViews.noEventFound();
            Id = userViews.getEventId();
        }
        return Id;
    }

    public void showBookingOptionControl() {
        int Id = inputEventId();

        if (Id != -1) {
            Event event = eventManager.getEventById(Id);
            
            if (event.getQuantity() <= 0 || event.getStatus() == Event.EventStatus.CANCELLED) {
                userViews.cannotBookTickets();
                return;
            }
            userViews.showSingleEvent(event, eventManager.getTotalEventsCount());

            int quantity = userViews.getQuantity(event.getQuantity(), false);

            while (event.getQuantity() < quantity) {
                quantity = userViews.getQuantity(event.getQuantity(), true);
            }
            
            eventManager.updateBookedEvent(Id, quantity, false);
            userManager.attachTicketToUser(new Ticket(userManager.getLoggedInUser(), event, quantity));
            userViews.eventBookedSuccessfullyMessage();            
        }
        return;
    }
}
