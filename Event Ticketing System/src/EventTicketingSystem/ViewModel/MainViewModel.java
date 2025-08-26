package EventTicketingSystem.ViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import EventTicketingSystem.View.MainView;
import EventTicketingSystem.View.UserViews;
import EventTicketingSystem.View.LoginAndSignUpView;
import EventTicketingSystem.Model.User;
import EventTicketingSystem.Model.UserManager;
import EventTicketingSystem.Model.EventManager;
import EventTicketingSystem.Model.TicketManager;
import EventTicketingSystem.Helpers.FileManager;

public class MainViewModel {
    private static MainView mainView = new MainView();    
    private static UserViews userViews = new UserViews();
    private static LoginAndSignUpView loginAndSignUpView = new LoginAndSignUpView();
    private static final UserManager userManager = new UserManager();
    private static final EventManager eventManager = new EventManager();
    private static final TicketManager ticketManager = new TicketManager(userManager, eventManager);

    public static void start() {
        // userManager.createDefaultAdmin(); //Uncomment to seed a default admin from UserManager file
        // eventManager.seedData(); //Uncomment to seed temp list of readymade events from EventManager file
        userManager.addMultipleUsers(FileManager.loadUsers());
        eventManager.addMultipleEvents(FileManager.loadEvents());
        ticketManager.addMultipleTickets(FileManager.loadTickets());
        // threadPracticeMethod();
        threadWithExecutors();
        // showMainMenu();  //Commented for thread practice
    }

    public static void threadPracticeMethod() {
        final UserViewModel userViewModel1 = new UserViewModel(userManager, userViews, eventManager, ticketManager, new User("Temp", "qq", false));
        
        new Thread(() -> {
            userViewModel1.showBookingOptionControl(2, 20, new User("User1", "User1", false));
        }).start();

        new Thread(() -> {
            userViewModel1.showBookingOptionControl(2, 50, new User("User2", "User2", false));
        }).start();
        
    }

    public static void threadWithExecutors() {
        final UserViewModel userViewModel1 = new UserViewModel(userManager, userViews, eventManager, ticketManager, new User("Temp", "qq", false));        
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task1 = () -> userViewModel1.showBookingOptionControl(2, 20, new User("User1", "User1", false));
        Runnable task2 = () -> userViewModel1.showBookingOptionControl(2, 50, new User("User2", "User2", false));

        executor.submit(task1);
        executor.submit(task2);
        executor.shutdown();
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
                    FileManager.saveEvents(eventManager.getAllEvents());
                    FileManager.saveUsers(userManager.getAllUsers());
                    FileManager.saveTickets(ticketManager.getAllTickets());
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
