package EventTicketingSystem.ViewModel;

import java.util.*;
import EventTicketingSystem.Model.Admin;
import EventTicketingSystem.Model.User;
import EventTicketingSystem.Model.UserManager;
import EventTicketingSystem.View.AdminViews;
import EventTicketingSystem.View.LoginAndSignUpView;;

public class AdminViewModel {
    UserManager userManager;
    private static AdminViews adminViews;
    private static LoginAndSignUpView loginAndSignUpView;

    Scanner sc = new Scanner(System.in);

    public AdminViewModel(UserManager userManager) {
        this.userManager = userManager;
        this.adminViews = new AdminViews();
        this.loginAndSignUpView = new LoginAndSignUpView();
    }

    public void showAdminFlow() {
        while (true) {
            switch (adminViews.showAdminMenu()) {
                case 1:
                    createNewAdmin();
                    break;
                case 2:
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
            userManager.addNewUser(new User(username, password, isAdmin));               
            loginAndSignUpView.userSuccessfulMessage(false);   
        }
        return;
    }
}
