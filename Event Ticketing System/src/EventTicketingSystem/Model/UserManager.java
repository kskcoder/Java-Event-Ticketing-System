package EventTicketingSystem.Model;

import java.util.*;

import EventTicketingSystem.Helpers.AppConstants;
import java.util.stream.Collectors;

public class UserManager {
    private List<User> users = new ArrayList<>();
    private static User currentLoggedInUser = null;

    //User data and authentication

    public int authenticateUser(String username, String password) {
        for (User user: users) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                setCurrentUser(user);
                return AppConstants.AUTH_SUCCESS;                
            } else if (user.getUserName().equals(username) && !user.getPassword().equals(password)) {
                return AppConstants.AUTH_INCORRECT_PASSWORD; 
            }
        }
        return AppConstants.AUTH_FAILURE;
    }

    public User getUserByUsername(String username) {
        return users.stream().filter(user -> user.getUserName().equals(username)).findAny().orElse(null);
    }

    public void addNewUser(User user) {
        this.users.add(user);
        return;
    }

    public void addMultipleUsers(List<User> users) {
        for (User user: users) {
            this.users.add(user);
        }
        return;
    }

    public List<User> getAllUsers() {
        return new ArrayList<User>(this.users);
    }

    public List<User> getOnlyAdmins() {
        return new ArrayList<User>(this.users.stream().filter(user -> user.isAdmin() == true).collect(Collectors.toList()));
    }

    public List<User> getOnlyUsers() {
        return new ArrayList<User>(this.users.stream().filter(user -> user.isAdmin() == false).collect(Collectors.toList()));
    }

    public boolean isUsernameTaken(String username) {
        return users.stream().anyMatch(user -> user.getUserName().equals(username));
    }

    //Set Logged In User

    public void setCurrentUser(User user) {
        currentLoggedInUser = user;
        return;
    }

    public User getLoggedInUser() {
        return currentLoggedInUser;
    }

    //Admin related management

    public void createDefaultAdmin() {
        addNewUser(new Admin("Admin", "0000", true));        
    }

    public void deleteUser(User user) {
        this.users.remove(user);
        return; 
    }

    //Log Out User

    public void logOutUser() {
        setCurrentUser(null);
        return;
    }

    //Ticket Related to management

    public void attachTicketToUser(Ticket ticket, User user) {
        if (user != null) {
            user.tickets.add(ticket);
        }
        return;
    }

    public void cancelTicketOfUser(User user, Ticket ticket) {
        if (user != null) {
            user.tickets.remove(ticket);
        }
        return;
    }

    public List<Ticket> getAllUserBookedEvents(User user) {
        return new ArrayList<Ticket>(user.tickets);
    }

    public int getUserTicketsCount(User user) {
        return user.tickets.size();
    }

    public Ticket getEventByTicketId(User user, int ticketId) {
        return this.getAllUserBookedEvents(user).stream().filter(ticket -> ticket.getTicketId() == ticketId).findFirst().orElse(null);
    }
}