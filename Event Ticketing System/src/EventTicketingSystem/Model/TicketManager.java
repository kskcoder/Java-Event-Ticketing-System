package EventTicketingSystem.Model;

import java.util.*;

import EventTicketingSystem.Model.Ticket;
import EventTicketingSystem.Model.User;
import EventTicketingSystem.Model.UserManager;
import EventTicketingSystem.Model.EventManager;

public class TicketManager {
    UserManager userManager;
    EventManager eventManager;


    public TicketManager(UserManager userManager, EventManager eventManager) {
        this.userManager = userManager;
        this.eventManager = eventManager;
    }

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

    public List<Ticket> getAllTicketsOfUser(User user) {
        List<Ticket> list = new ArrayList<Ticket>();

        for (Map.Entry<String, List<Ticket>> entry: userTicketMap.entrySet()) {
            if (user.getUserName() == entry.getKey()) {
                return entry.getValue();
            }
        }
        return list;
    }

    public void addMultipleTickets(List<String[]> tickets) {
        for (String[] ticket: tickets) {
            int id = Integer.parseInt(ticket[0]);
            User user = userManager.getUserByUsername(ticket[1]);
            Event event = eventManager.getEventById( Integer.parseInt(ticket[2]));
            int quantity = Integer.parseInt(ticket[3]);

            Ticket newTicket = new Ticket(id, user, event, quantity);

            if (user != null && event != null) {
                userManager.attachTicketToUser(newTicket, user);
                addTicketToList(user, newTicket);
            }
        }
        return;
    }

    public void setAutoIdForTickets(User user) {
        Ticket.setAutoId(getAllTicketsOfUser(user).get(getAllTicketsOfUser(user).size()-1).getTicketId()+1);
        return;
    }
    
}
