package EventTicketingSystem.Model;

public class Admin extends User{    

    public Admin() {}

    public Admin(String username, String password, boolean isAdmin) {
        super(username, password, true);
    }
}
