package EventTicketingSystem.Model;

import java.util.*;

import EventTicketingSystem.Model.Ticket;
import EventTicketingSystem.Model.User;

public class TicketManager {
    private Map<String, List<Ticket>> userTicketMap = new HashMap<>();

    public void addTicketToList(User user, Ticket ticket) {
        this.userTicketMap.get(user.getUserName()).add(ticket);
        return;
    }

    public void removeTicketFromList(User user, Ticket ticket) {
        this.userTicketMap.get(user.getUserName()).remove(ticket);
        return;
    }

    
}
