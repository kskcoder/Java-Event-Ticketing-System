package EventTicketingSystem.ViewModel;

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
                    adminViews.eventAddedSuccessfullyMessage();
                    break;
            }
        }
    }


}
