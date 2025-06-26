package EventTicketingSystem.Model;

import java.util.Date;

public class Event {
    protected int id;
    protected String name;    
    protected String venue;
    protected Date date;
    protected int price;
    protected int totalTickets;
    protected int ticketsAvailable;    
    protected EventStatus status;

    public Event(int id, String name, String venue, Date date, int price, int totalTickets, int ticketsAvailable, EventStatus status) {
        this.id = id;
        this.name = name;
        this.venue = venue;
        this.date = date;
        this.price = price;
        this.totalTickets = totalTickets;
        this.ticketsAvailable = ticketsAvailable;
        this.status = status;
    }

    //Getters
    public String getEventName() {
        return this.name;
    }

    public int getEventId() {
        return this.id;
    }

    //Setter
    public void setEventName(String name) {
        this.name = name;
    }

    public void setEventVenue(String venue) {
        this.venue = venue;
    }

    public void setEventDate(Date date) {
        this.date = date;
    }

    public void setEventPrice(int price) {
        this.price = price;
    }

    public void setEventStatus(EventStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Event Details: \nId: "+this.id
        +"\nName: "+this.name
        +"\nVenue: "+this.venue
        +"\nDate: "+this.date
        +"\nPrice: "+this.price
        +"\nTotal Tickets: "+this.totalTickets
        +"\nTickets Available: "+this.ticketsAvailable
        +"\nStatus: "+this.status.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((venue == null) ? 0 : venue.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + price;
        result = prime * result + totalTickets;
        result = prime * result + ticketsAvailable;
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Event that = (Event)obj; 
        return this.id == that.id;
    }

    public enum EventStatus {
        UPCOMING, CANCELLED, COMPLETED, POSTPONED
    }
}
