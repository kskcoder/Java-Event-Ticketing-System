package EventTicketingSystem.Helpers;

import java.util.List;

import EventTicketingSystem.DAOs.EventsDAO;
import EventTicketingSystem.DAOs.TicketsDAO;
import EventTicketingSystem.DAOs.UsersDAO;
import EventTicketingSystem.Model.Event;
import EventTicketingSystem.Model.Ticket;
import EventTicketingSystem.Model.User;

public class DatabaseManager {
    //Event event methods
    public static void saveEvents(List<Event> events) {
        EventsDAO.save(events);
        return;
    } 

    public static List<Event> loadEvents() {
        return EventsDAO.load();
    }


    //User Related Methods

    public static void saveUsers(List<User> users) {
        UsersDAO.save(users);
        return;
    }

    public static List<User> loadUsers() {
        return UsersDAO.load();
    }


    //Tickets Related Storage 

    public static void saveTickets(List<Ticket> tickets) {
        TicketsDAO.saveTickets(tickets);
        return;
    }

    public static List<String[]> loadTickets() {
        return TicketsDAO.loadTickets();       
    }
}
