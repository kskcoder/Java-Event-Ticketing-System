package EventTicketingSystem.Model;

public class TicketFactory {
    public static Ticket createTicket(User user, Event event, int quantity) {
        return new Ticket(user, event, quantity);
    }

    public static Ticket createTicketWithId(int id, User user, Event event, int quantity) {
        return new Ticket(id, user, event, quantity);
    }
}
