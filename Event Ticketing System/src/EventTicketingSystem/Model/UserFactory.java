package EventTicketingSystem.Model;

public class UserFactory {
    public static User createUser(String username, String password, boolean isAdmin) {
        return new User(username, password, isAdmin);
    }
}
