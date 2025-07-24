package EventTicketingSystem.Model;

import java.util.*;

import EventTicketingSystem.Model.Ticket;
import EventTicketingSystem.Model.User;

public class TicketManager {
    private Map<String, List<Ticket>> userTicketMap = new HashMap<>();

    public void addTicketToList(User user, Ticket ticket) {
        if (!this.userTicketMap.containsKey(user.getUserName())) {
            this.userTicketMap.put(user.getUserName(), new ArrayList<Ticket>());
        }
        this.userTicketMap.get(user.getUserName()).add(ticket);

        return;
    }

    public void removeTicketFromList(User user, Ticket ticket) {
        this.userTicketMap.get(user.getUserName()).remove(ticket);
        return;
    }

    public List<Ticket> getAllTickets() {
        List<Ticket> list = new ArrayList<Ticket>();

        for (List<Ticket> tickets: userTicketMap.values()) {
            for (Ticket ticket: tickets) {
                list.add(ticket);
            }
        }
        return list;
    }
    
}
