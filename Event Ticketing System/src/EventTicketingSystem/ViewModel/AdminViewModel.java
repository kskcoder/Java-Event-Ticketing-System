package EventTicketingSystem.ViewModel;

import java.time.LocalDateTime;
import java.util.*;
import EventTicketingSystem.Model.Admin;
import EventTicketingSystem.Model.UserManager;
import EventTicketingSystem.Model.Event;
import EventTicketingSystem.Model.EventManager;
import EventTicketingSystem.View.AdminViews;
import EventTicketingSystem.View.LoginAndSignUpView;;

public class AdminViewModel {
    UserManager userManager;
    EventManager eventManager;
    private static AdminViews adminViews;
    private static LoginAndSignUpView loginAndSignUpView;

    Scanner sc = new Scanner(System.in);

    public AdminViewModel(UserManager userManager, EventManager eventManager) {
        this.userManager = userManager;
        this.eventManager = eventManager;
        adminViews = new AdminViews();
        loginAndSignUpView = new LoginAndSignUpView();
    }

    public void showAdminFlow() {
        while (true) {
            switch (adminViews.showAdminMenu()) {
                case 1:
                    createNewAdmin();
                    break;
                case 2:
                    showEventManagementMenu();
                    break;
                case 3:
                case 4:
                case 5:
                    userManager.logOutUser();
                    adminViews.adminLoggedOutMessage();
                    return;
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
                int price = adminViews.viewEventsUnderPrice();
                List<Event> events = eventManager.getEventsUnderPrice(price);
                adminViews.showEventsList(events, eventManager.getTotalEventsCount());
                return;
            }
            case 3: {
                String name = adminViews.viewEventsByName();
                Event event = eventManager.getEventByName(name);
                adminViews.showSingleEvent(event, eventManager.getTotalEventsCount());
                return;
            }
            case 4: {
                int id = adminViews.viewEventsById();
                Event event = eventManager.getEventById(id);
                adminViews.showSingleEvent(event, eventManager.getTotalEventsCount());
                return;
            }
            case 5: {
                Event.EventStatus status = adminViews.viewEventsByStatus();
                List<Event> events = eventManager.getEventByStatus(status);
                adminViews.showEventsList(events, eventManager.getTotalEventsCount());
                return;
            }
            case 6: {
                LocalDateTime date = adminViews.viewEventsByDate();
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
                eventManager.updateEventName(id, adminViews.getEventNameToUpdate());
                break;
            }
            case 2: {
                eventManager.updateEventVenue(id, adminViews.getEventVenueToUpdate());
                break;
            }
            case 3: {
                eventManager.updateEventDate(id, adminViews.getEventDateToUpdate());
                break;
            }
            case 4: {
                eventManager.updateEventPrice(id, adminViews.getEventPriceToUpdate());
                break;
            }
            case 5: {
                eventManager.updateEventStatus(id, adminViews.getEventStatusToUpdate());
                break;
            }        
        }

        adminViews.showUpdatedMessage();
        adminViews.showSingleEvent(eventManager.getEventById(id), id);
    }
}
