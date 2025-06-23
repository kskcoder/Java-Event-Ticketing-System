package EventTicketingSystem.ViewModel;

import EventTicketingSystem.View.MainView;
import EventTicketingSystem.View.LoginAndSignUpView;
import EventTicketingSystem.Model.User;
import EventTicketingSystem.Model.UserManager;

public class MainViewModel {
    private static MainView mainView = new MainView();
    private static LoginAndSignUpView loginAndSignUpView = new LoginAndSignUpView();
    private static UserManager userManager = new UserManager();

    public static void start() {
        userManager.addNewUser(new User("Tejas", "Kashid", true));
        showMainMenu();
    }
    
    public static void showMainMenu() {
        switch (mainView.showMainMenu()) {
            case 1:
                handleLogin();
                break;

            case 2:
                handleSignup();
                break;
                
            case 3:
                break;
        }
    }

    public static void handleLogin() {
        User newUser = loginAndSignUpView.acceptExistingUserDetails();

        String username = newUser.getUserName();
        String password = newUser.getPassword();

        boolean userExists = userManager.isUsernameTaken(username);

        int isAuthenticated = 0;

        if (userExists) {
            isAuthenticated = userManager.authenticateUser(username, password);
        } else {
            loginAndSignUpView.userDoesNotExist();
            showMainMenu();
            return;
        }

        if (isAuthenticated == 1) {
            System.out.println("Aao Sir Aao!");
            System.out.println("You are logged in!");
        } else if (isAuthenticated == -1){
            int attempts = 3;
            while (isAuthenticated != 1 && attempts > 0) {
                password = loginAndSignUpView.incorrectPassword(attempts);                
                isAuthenticated = userManager.authenticateUser(username, password);
                if (isAuthenticated != 1) {
                    attempts--;
                } else if (isAuthenticated == 1) {
                    System.out.println();      
                    System.out.println("Aao Sir Aao!");  
                    System.out.println("You are logged in!");                  
                    break;
                }
            }            
        }
        
        showMainMenu();
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
            loginAndSignUpView.userSuccessfullyLoggedIn();         
        }

        showMainMenu();
        return;
    }

    public static void main(String args[]) {
        start();
    }
}
