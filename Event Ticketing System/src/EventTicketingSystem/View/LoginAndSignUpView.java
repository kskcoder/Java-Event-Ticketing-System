package EventTicketingSystem.View;

import java.util.*;
import EventTicketingSystem.Helpers.InputHelper;
import EventTicketingSystem.Model.User;

public class LoginAndSignUpView {
    Scanner sc = new Scanner(System.in);

    public User acceptExistingUserDetails() {
        String username = null;
        String password = null;

        while (true) {
            if (username == null && password == null) {
                System.out.println("\nEnter Username and Password");
                username = InputHelper.getNonEmptyString("Username: ");

                password = InputHelper.getNonEmptyString("Password: ");

                System.out.println();
            }            

            if (username == null) {
                System.out.println("Username cannot be blank! Please re-enter username: ");
                username = InputHelper.getNonEmptyString("Username: ");
            } else if (password == null) {
                System.out.println("Password cannot be blank! Please re-enter password: ");
                password = InputHelper.getNonEmptyString("Password: ");
            } else {
                return new User(username, password, false);
            }
        }
    }

    public void userDoesNotExist() {
        System.out.println("User does not exist!");
    }

    public String incorrectPassword(int attempts) {

        return InputHelper.getNonEmptyString("Incorrect Password please re-enter ("+attempts+" left):");   
    }

    public String acceptNewUsername() {
        return InputHelper.getNonEmptyString("Enter new username: ");   
    }

    public String userAlreadyExists(int attempts) {
        return InputHelper.getNonEmptyString("Username exists already, please use another username ("+attempts+" attempts left): ");   
    }

    public String acceptPassword() {
        return InputHelper.getNonEmptyString("Enter new password: ");
    }

    public void userSuccessfulMessage(boolean loggedIN) {
        System.out.println();
        if (loggedIN) {
            System.out.println("User successfully logged in!");
        } else {
            System.out.println("User successfully created!");
        }        
    }

    public void userLoggedOutMessage() {
        System.out.println();
        System.out.println("User logged out successfully!");
        System.out.println();
    }

}
