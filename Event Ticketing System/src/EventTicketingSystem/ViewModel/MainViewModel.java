package EventTicketingSystem.ViewModel;

import EventTicketingSystem.View.MainView;
import EventTicketingSystem.View.UserViews;
import EventTicketingSystem.View.LoginAndSignUpView;
import EventTicketingSystem.Model.User;
import EventTicketingSystem.Model.UserManager;
import EventTicketingSystem.Model.EventManager;

public class MainViewModel {
    private static MainView mainView = new MainView();    
    private static UserViews userViews = new UserViews();
    private static LoginAndSignUpView loginAndSignUpView = new LoginAndSignUpView();
    private static final UserManager userManager = new UserManager();
    private static final EventManager eventManager = new EventManager();
    private static AdminViewModel adminViewModel = new AdminViewModel(userManager, eventManager);

    public static void start() {
        userManager.createDefaultAdmin();
        eventManager.seedData();
        showMainMenu();
    }
    
    public static void showMainMenu() {
        while(true) {
            switch (mainView.showMainMenu()) {
                case 1:
                    handleLogin();
                    break;
    
                case 2:
                    handleSignup();
                    break;
                    
                case 3:
                    return;
            }
        }
    }

    public static void handleLogin() {
        User existingUser = loginAndSignUpView.acceptExistingUserDetails();

        String username = existingUser.getUserName();
        String password = existingUser.getPassword();

        boolean userExists = userManager.isUsernameTaken(username);

        int isAuthenticated = 0;

        if (userExists) {
            isAuthenticated = userManager.authenticateUser(username, password);
        } else {
            loginAndSignUpView.userDoesNotExist();
            return;
        }

        if (isAuthenticated == 1) {
            loginAndSignUpView.userSuccessfulMessage(true);
            showAfterLoginViews();
        } else if (isAuthenticated == -1){
            int attempts = 3;
            while (isAuthenticated != 1 && attempts > 0) {
                password = loginAndSignUpView.incorrectPassword(attempts);                
                isAuthenticated = userManager.authenticateUser(username, password);
                if (isAuthenticated != 1) {
                    attempts--;
                } else if (isAuthenticated == 1) {
                    System.out.println();      
                    loginAndSignUpView.userSuccessfulMessage(true);  
                    User newUser = userManager.getUserByUsername(username);
                    if (newUser != null) userManager.setCurrentUser(newUser);   
                    showAfterLoginViews();           
                    break;
                }
            }            
        }
        return;
    }

    public static void handleSignup() {
        String username = null;
        String password = null;
        boolean isAdmin = false;

        username = loginAndSignUpView.acceptNewUsername();

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
            userManager.addNewUser(new User(username, password, isAdmin));               
            loginAndSignUpView.userSuccessfulMessage(false);                 
        }        
        return;
    }

    public static void showAfterLoginViews() {
        if (userManager.getLoggedInUser().isAdmin()) {
            adminViewModel.showAdminFlow(); 
        } else {
            switch (userViews.showUserMenu()) {
                case 3:
                    userManager.logOutUser();
                    loginAndSignUpView.userLoggedOutMessage();
            }
        }  
        return;
    }

    public static void main(String args[]) {
        start();
    }
}
