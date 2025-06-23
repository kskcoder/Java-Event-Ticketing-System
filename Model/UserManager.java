package EventTicketingSystem.Model;

import java.util.*;

import EventTicketingSystem.Helpers.AppConstants;

public class UserManager {
    private List<User> users = new ArrayList<>();

    public int authenticateUser(String username, String password) {
        for (User user: users) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                return AppConstants.AUTH_SUCCESS;
            } else if (user.getUserName().equals(username) && !user.getPassword().equals(password)) {
                return AppConstants.AUTH_INCORRECT_PASSWORD; 
            }
        }
        return AppConstants.AUTH_FAILURE;
    }

    public void addNewUser(User user) {
        this.users.add(user);
    }

    public List<User> getAllUsers() {
        return new ArrayList<User>(this.users);
    }

    public boolean isUsernameTaken(String username) {
        return users.stream().anyMatch(user -> user.getUserName().equals(username));
    }
}