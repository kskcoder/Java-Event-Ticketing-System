package EventTicketingSystem.ViewModel;

import java.time.LocalDateTime;
import java.util.*;

import EventTicketingSystem.View.AdminViews;
import EventTicketingSystem.View.LoginAndSignUpView;
import EventTicketingSystem.Model.User;
import EventTicketingSystem.Model.Admin;
import EventTicketingSystem.Model.Ticket;
import EventTicketingSystem.Model.Event;
import EventTicketingSystem.Model.UserManager;
import EventTicketingSystem.Model.EventManager;
import EventTicketingSystem.Model.TicketManager;

public class AdminViewModel {
    UserManager userManager;
    EventManager eventManager;
    TicketManager ticketManager;
    private static AdminViews adminViews;
    private static LoginAndSignUpView loginAndSignUpView;

    Scanner sc = new Scanner(System.in);

    public AdminViewModel(UserManager userManager, EventManager eventManager, TicketManager ticketManager) {
        this.userManager = userManager;
        this.eventManager = eventManager;
        this.ticketManager = ticketManager;
        adminViews = new AdminViews();
        loginAndSignUpView = new LoginAndSignUpView();
    }

    public void showAdminFlow() {
        while (true) {
            switch (adminViews.showMainMenu()) {
                case 1:
                    createNewAdmin();
                    break;
                case 2:
                    showEventManagementMenu();
                    break;
                case 3:
                    userManagementControl();
                    break;
                case 4:
                    userManager.logOutUser();
                    adminViews.adminLoggedOutMessage();
                    return;
                case 5:
                    if (adminViews.askForDeletion()) {
                        userManager.deleteUser(userManager.getLoggedInUser());
                        adminViews.userDeletedSuccessfullyMessage();
                        return;
                    }
                    break;
            }
        }
    }

    public void createNewAdmin() {
        boolean isAdmin = true;

        String username = loginAndSignUpView.acceptNewUsername();
        String password;

        boolean isUsernameTaken = userManager.isUsernameTaken(username);

        if (isUsernameTaken) {
            int attempts = 3;
            while (attempts > 0 && isUsernameTaken) {
                username = loginAndSignUpView.userAlreadyExists(attempts);
                isUsernameTaken = userManager.isUsernameTaken(username);
                attempts--;
            }
        }

        if (!isUsernameTaken) {
            password = loginAndSignUpView.acceptPassword();
            userManager.addNewUser(new Admin(username, password, isAdmin));               
            loginAndSignUpView.userSuccessfulMessage(false);   
        }
        return;
    }

    public void showEventManagementMenu() {
        while (true) {
            switch (adminViews.showEventManagementMenu()) {
                case 1:
                    Event newEvent = adminViews.createNewEvent();
                    boolean validEvent = eventManager.validateNewEvent(newEvent);
                    boolean exists = eventManager.eventExistsAlready(newEvent);

                    if (exists) adminViews.eventExistsMessage();
                    
                    while (!validEvent && !exists) {
                        newEvent = adminViews.createNewEvent();
                        if (exists) adminViews.eventExistsMessage();
                        validEvent = eventManager.validateNewEvent(newEvent);
                    }
                    eventManager.addEvent(newEvent);
                    adminViews.eventAddedSuccessfullyMessage();
                    break;
                case 2:
                    while(true) {
                        int choice = adminViews.viewEventMenu();
                        if (choice == 7) {
                            break;
                        } else {
                            showEventsControl(choice);
                        }                    
                    }
                    break;
                case 3:
                    while (true) {                        
                        int choice = adminViews.updateEventMenu();
                        if (choice == 6) {
                            break;
                        } else {
                            showUpdateControl(choice);
                        }   
                    }
                    break;
                case 4:
                    while (true) {
                        int id = adminViews.getEventsId();
                        Event event = eventManager.getEventById(id);

                        if (event != null) {
                            eventManager.deleteEventById(id);
                            adminViews.deletedSuccessfully();
                            break;
                        } else {
                            adminViews.noEventFound();
                        }
                    }
                case 5:
                    return;
            }
        }
    }

    public void showEventsControl(int choice) {
        switch (choice) {
            case 1: {
                List<Event> events = eventManager.getAllEvents();
                adminViews.showEventsList(events, events.size());
                return;
            }
            case 2: {
                int price = adminViews.getEventsPrice();
                List<Event> events = eventManager.getEventsUnderPrice(price);
                adminViews.showEventsList(events, eventManager.getTotalEventsCount());
                return;
            }
            case 3: {
                String name = adminViews.getEventsName();
                Event event = eventManager.getEventByName(name);
                adminViews.showSingleEvent(event, eventManager.getTotalEventsCount());
                return;
            }
            case 4: {
                int id = adminViews.getEventsId();
                Event event = eventManager.getEventById(id);
                adminViews.showSingleEvent(event, eventManager.getTotalEventsCount());
                return;
            }
            case 5: {
                Event.EventStatus status = adminViews.getEventsStatus();
                List<Event> events = eventManager.getEventByStatus(status);
                adminViews.showEventsList(events, eventManager.getTotalEventsCount());
                return;
            }
            case 6: {
                LocalDateTime date = adminViews.getEventsDate();
                List<Event> events = eventManager.getEventsByDate(date);
                adminViews.showEventsList(events, eventManager.getTotalEventsCount());
                return;
            }          
        }
    }

    public void showUpdateControl(int choice) {
        int id = adminViews.getEventId();

        switch (choice) {
            case 1: {
                eventManager.updateEventName(id, adminViews.getEventsName());
                break;
            }
            case 2: {
                eventManager.updateEventVenue(id, adminViews.getEventVenueToUpdate());
                break;
            }
            case 3: {
                eventManager.updateEventDate(id, adminViews.getEventsDate());
                break;
            }
            case 4: {
                eventManager.updateEventPrice(id, adminViews.getEventsPrice());
                break;
            }
            case 5: {
                eventManager.updateEventStatus(id, adminViews.getEventsStatus());
                break;
            }        
        }

        adminViews.showUpdatedMessage();
        adminViews.showSingleEvent(eventManager.getEventById(id), id);
    }

    //User Management Control

    public void userManagementControl() {
        while (true) {
            switch (adminViews.showUserManagementMenu()) {
                case 1:
                    adminViews.showUsersList(userManager.getAllUsers());
                    break;
                case 2:
                    adminViews.showUsersList(userManager.getOnlyAdmins());
                    break;
                case 3:
                    adminViews.showUsersList(userManager.getOnlyUsers());
                    if (userManager.getOnlyUsers().size() > 0 && adminViews.askToShowTickets()) {
                        User user = userManager.getUserByUsername(adminViews.enterUserName());
                        if (user != null) {
                            adminViews.showTicketsList(userManager.getAllUserBookedEvents(user), userManager.getAllUserBookedEvents(user).size());

                            if (userManager.getUserTicketsCount(user) > 0 && adminViews.askForCancellation()) {
                            Ticket ticket = userManager.getEventByTicketId(user, adminViews.getEventsId());
                            if (ticket != null) {
                                eventManager.updateBookedEvent(ticket.getRelatedEventId(), ticket.getBookedQuantity(), true);
                                ticketManager.removeTicketFromList(user, ticket);
                                userManager.cancelTicketOfUser(user, ticket);
                                adminViews.ticketCancelledSuccessfully();
                            } else {
                                adminViews.noEventFound();
                            }
                    }
                        } else {
                            adminViews.userNotFound();
                            break;
                        }
                    } else if (adminViews.askToDeleteUsers()) {
                        User user = userManager.getUserByUsername(adminViews.enterUserName());
                        if (user != null && user != userManager.getLoggedInUser()) {
                            userManager.deleteUser(user);
                            adminViews.userDeletedSuccessfullyMessage();
                        } else {
                            adminViews.cannotPerformActionMessage();
                            break;
                        }
                    }
                    break;
                case 4:
                    return;
            }
        }
    }
}
