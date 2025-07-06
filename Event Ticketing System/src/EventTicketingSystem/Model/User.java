package EventTicketingSystem.Model;

import java.util.*;

public class User {
    protected String username;
    protected String password;
    protected boolean isAdmin;
    protected List<Ticket> tickets = new ArrayList<Ticket>();

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }

    @Override
    public String toString() {
        return "Username: "+this.username
                +"\nIs Admin: "+this.isAdmin();
    }
}
