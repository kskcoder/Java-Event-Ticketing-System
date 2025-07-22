package EventTicketingSystem.ViewModel;

import java.time.LocalDateTime;
import java.util.List;

import EventTicketingSystem.View.UserViews;
import EventTicketingSystem.Model.User;
import EventTicketingSystem.Model.Event;
import EventTicketingSystem.Model.Ticket;
import EventTicketingSystem.Model.UserManager;
import EventTicketingSystem.Model.EventManager;
import EventTicketingSystem.Model.TicketManager;

public class UserViewModel {
    UserManager userManager;
    UserViews userViews;
    EventManager eventManager;
    TicketManager ticketManager;
    User currentLoggedInUser;

    public UserViewModel(UserManager userManager, UserViews userViews, EventManager eventManager, TicketManager ticketManager) {
        this.userManager = userManager;
        this.userViews = userViews;
        this.eventManager = eventManager;
        this.ticketManager = ticketManager;
        this.currentLoggedInUser = userManager.getLoggedInUser();
    }

    public void showUserFlow() {        
        while (true) {
            switch (userViews.showMainMenu()) {
                case 1:
                    showEventsControl(userViews.viewEventMenu());
                    break;
                case 2:
                    userViews.showTicketsList(userManager.getAllUserBookedEvents(currentLoggedInUser), userManager.getUserTicketsCount(currentLoggedInUser));
                    if (userManager.getUserTicketsCount(currentLoggedInUser) > 0 && userViews.askForCancellation()) {
                        Ticket ticket = userManager.getEventByTicketId(currentLoggedInUser, userViews.getEventsId());
                        if (ticket != null) {
                            eventManager.updateBookedEvent(ticket.getRelatedEventId(), ticket.getBookedQuantity(), true);
                            ticketManager.removeTicketFromList(currentLoggedInUser, ticket);
                            userManager.cancelTicketOfUser(currentLoggedInUser, ticket);
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
                case 4:
                    if (userViews.askForDeletion()) {
                        userManager.deleteUser(userManager.getLoggedInUser());
                        userViews.userDeletedSuccessfullyMessage();
                        return;
                    }
                    break;
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
                int price = userViews.getEventsPrice();
                List<Event> events = eventManager.getEventsUnderPrice(price);
                userViews.showEventsList(events, eventManager.getTotalEventsCount());
                showBookingOptionControl();
                break;
            }
            case 3: {
                String name = userViews.getEventsName();
                Event event = eventManager.getEventByName(name);
                userViews.showSingleEvent(event, eventManager.getTotalEventsCount());
                showBookingOptionControl();
                break;
            }
            case 4: {
                int id = userViews.getEventsId();
                Event event = eventManager.getEventById(id);
                userViews.showSingleEvent(event, eventManager.getTotalEventsCount());
                showBookingOptionControl();
                break;
            }
            case 5: {
                Event.EventStatus status = userViews.getEventsStatus();
                List<Event> events = eventManager.getEventByStatus(status);
                userViews.showEventsList(events, eventManager.getTotalEventsCount());
                showBookingOptionControl();
                break;
            }
            case 6: {
                LocalDateTime date = userViews.getEventsDate();
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
            ticketManager.addTicketToList(currentLoggedInUser, new Ticket(currentLoggedInUser, event, quantity);
            userManager.attachTicketToUser(new Ticket(currentLoggedInUser, event, quantity));
            userViews.eventBookedSuccessfullyMessage();            
        }
        return;
    }
}
