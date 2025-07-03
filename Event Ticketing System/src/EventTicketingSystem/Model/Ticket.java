package EventTicketingSystem.Model;

public class Ticket {
    private int ticketCounter = 1;
    private int ticketId;
    private User user;
    private Event event;
    private int quantity;

    public Ticket(User user, Event event, int quantity) {
        this.ticketId = ticketCounter++;
        this.user = user;
        this.event = event;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Ticket details: \n"
        +"Ticket ID: "+this.ticketId
        +"\nUsername: "+this.user.username
        +"\nEvent Name: "+this.event.name
        +"\nDate: "+this.event.date
        +"\nVenue: "+this.event.venue
        +"\nQuantity: "+this.quantity
        +"\nAmount paid: "+this.event.price;
    }
}
