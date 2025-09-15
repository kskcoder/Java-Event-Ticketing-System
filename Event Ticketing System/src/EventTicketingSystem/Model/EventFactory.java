package EventTicketingSystem.Model;

import java.time.LocalDateTime;

import EventTicketingSystem.Model.Event.EventStatus;

public class EventFactory {
    public static Event createEvent(String name, String venue, LocalDateTime date, int price, int totalTickets, int ticketsAvailable, EventStatus status) {
        return new Event(name, venue, date, price, totalTickets, ticketsAvailable, status);
    }

    public static Event createEventWithId(int id, String name, String venue, LocalDateTime date, int price, int totalTickets, int ticketsAvailable, EventStatus status) {
        return new Event(id, name, venue, date, price, totalTickets, ticketsAvailable, status);
    }
}
