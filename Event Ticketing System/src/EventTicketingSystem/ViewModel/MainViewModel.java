package EventTicketingSystem.ViewModel;

import EventTicketingSystem.View.MainView;
import EventTicketingSystem.View.UserViews;
import EventTicketingSystem.View.LoginAndSignUpView;
import EventTicketingSystem.Model.User;
import EventTicketingSystem.Model.UserManager;
import EventTicketingSystem.Model.EventManager;
import EventTicketingSystem.Model.TicketManager;
import EventTicketingSystem.Helpers.DatabaseManager;;

public class MainViewModel {
    private static MainView mainView = new MainView();    
    private static UserViews userViews = new UserViews();
    private static LoginAndSignUpView loginAndSignUpView = new LoginAndSignUpView();
    private static final UserManager userManager = new UserManager();
    private static final EventManager eventManager = new EventManager();
    private static final TicketManager ticketManager = new TicketManager(userManager, eventManager);

    public static void start() {
        // userManager.createDefaultAdmin(); //Uncomment only first run to seed a default admin from UserManager file
        // eventManager.seedData(); //Uncomment only first run to seed temp list of readymade events from EventManager file
        userManager.addMultipleUsers(DatabaseManager.loadUsers());
        eventManager.addMultipleEvents(DatabaseManager.loadEvents());
        ticketManager.addMultipleTickets(DatabaseManager.loadTickets());
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
                    DatabaseManager.saveEvents(eventManager.getAllEvents());
                    DatabaseManager.saveUsers(userManager.getAllUsers());
                    DatabaseManager.saveTickets(ticketManager.getAllTickets());
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
            final AdminViewModel adminViewModel = new AdminViewModel(userManager, eventManager, ticketManager);
            adminViewModel.showAdminFlow(); 
        } else {
            final UserViewModel userViewModel = new UserViewModel(userManager, userViews, eventManager, ticketManager);
            ticketManager.setAutoIdForTickets(userManager.getLoggedInUser());
            userViewModel.showUserFlow();
        }  
        return;
    }

    public static void main(String args[]) {
        start();
    }
}
