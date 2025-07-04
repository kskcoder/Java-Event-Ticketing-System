package EventTicketingSystem.View;

import java.util.*;
import EventTicketingSystem.Model.User;

public class LoginAndSignUpView {
    Scanner sc = new Scanner(System.in);

    public User acceptExistingUserDetails() {
        String username = null;
        String password = null;

        while (true) {
            if (username == null && password == null) {
                System.out.println("\nEnter Username and Password");
                System.out.print("Username: "); 
                username = sc.next();

                System.out.print("Password: "); 
                password = sc.next();

                System.out.println();
            }            

            if (username == null) {
                System.out.println("Username cannot be blank! Please re-enter username: ");
                System.out.print("Username: "); 
                username = sc.next();
            } else if (password == null) {
                System.out.println("Password cannot be blank! Please re-enter password: ");
                System.out.print("Password: "); 
                password = sc.next();
            } else {
                return new User(username, password, false);
            }
        }
    }

    public void userDoesNotExist() {
        System.out.println("User does not exist!");
    }

    public String incorrectPassword(int attempts) {
        System.out.print("Incorrect Password please re-enter ("+attempts+" left):");
        return sc.next();    
    }

    public String acceptNewUsername() {
        System.out.print("Enter new username: ");
        return sc.next();
    }

    public String userAlreadyExists(int attempts) {
        System.out.print("Username exists already, please use another username ("+attempts+" attempts left): ");
        return sc.next();
    }

    public String acceptPassword() {
        System.out.print("Enter new password: ");
        return sc.next();
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
