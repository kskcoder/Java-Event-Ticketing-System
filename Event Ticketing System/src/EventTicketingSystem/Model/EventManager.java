package EventTicketingSystem.Model;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import EventTicketingSystem.Model.Event.EventStatus;

public class EventManager {
    private List<Event> events = new ArrayList<Event>();

    public void addEvent(Event event) {
        events.add(event);
        return;
    }

    public void addMultipleEvents(List<Event> events) {
        for (Event event: events) {
            this.events.add(event);
        }
        setIdForNewSession();
        return;
    }

    public boolean eventExistsAlready(Event event) {
        return events
        .stream().anyMatch(events -> events.hashCode() == event.hashCode());
    }

    public boolean validateNewEvent(Event event) {
        if (event.name != null 
        && event.date != null 
        && event.price > 0
        && event.totalTickets > 0
        && event.venue != null) {
            return true;
        } else {
            return false;
        }        
    }

    public void setIdForNewSession() {
        Event.setAutoId(getAllEvents().get(getTotalEventsCount()-1).id+1);
        return;
    }

    //View Event
    public List<Event> getAllEvents() {
        return new ArrayList<Event>(this.events);
    }

    public Event getEventByName(String eventName) {
        return this.events.stream().filter(event -> event.getEventName().equals(eventName)).findFirst().orElse(null); 
    }

    public Event getEventById(int id) {
        for (Event event: events) {
            if (event.id == id) return event;
        }
        return null;
    }

    public List<Event> getEventByStatus(Event.EventStatus status) {
        return new ArrayList<Event>(this.events.stream()
        .filter(event -> event.status.equals(status))
        .collect(Collectors.toList()));
    }    

    public List<Event> getEventsByDate(LocalDateTime date) {
        return new ArrayList<>(events.stream().filter(event -> event.date.equals(date)).collect(Collectors.toList()));
    }

    public List<Event> getEventsUnderPrice(int price) {
        return new ArrayList<>(this.events.stream().filter(event -> event.price <= price).collect(Collectors.toList()));
    }

    public int getTotalEventsCount() {
        return this.events.size();
    }

    //Delete Event
    public void deleteEventById(int id) {
        events.remove(events.stream()
        .filter(event -> event.getEventId() == id)
        .findFirst().orElse(null));

        return;
    }

    //Update event details
    public void updateEventName(int id, String name) {
        Event event = getEventById(id);

        if (event != null) {
            event.setEventName(name);
        }
        return;
    }

    public void updateEventVenue(int id, String venue) {
        Event event = getEventById(id);

        if (event != null) {
            event.setEventVenue(venue);
        }
        return;
    }

    public void updateEventDate(int id, LocalDateTime date) {
        Event event = getEventById(id);

        if (event != null) {
            event.setEventDate(date);
        }
        return;
    }

    public void updateEventPrice(int id, int price) {
        Event event = getEventById(id);

        if (event != null) {
            event.setEventPrice(price);
        }
        return;
    }

    public void updateEventStatus(int id, Event.EventStatus status) {
        Event event = getEventById(id);

        if (event != null) {
            event.setEventStatus(status);
        }
        return;
    }

    //Booking management
    public void updateBookedEvent(int id, int quantity, boolean add) {
        Event event = getEventById(id);

        if (event != null) {
            if (add) {
                event.setEventQuantity(event.getQuantity() + quantity);
            } else {
                event.setEventQuantity(event.getQuantity() - quantity);
            }
        }
    }

    //Create temp events

    public void seedData() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        addEvent(EventFactory.createEvent("Diwali Pahat", "Mumbai", LocalDateTime.parse("22/11/2024 06:00", dateTimeFormatter), 500, 200, 200, EventStatus.UPCOMING));
        addEvent(EventFactory.createEvent("Christmas Concert", "Pune", LocalDateTime.parse("25/12/2024 19:00", dateTimeFormatter), 800, 300, 250, EventStatus.UPCOMING));
        addEvent(EventFactory.createEvent("Ganesh Utsav", "Nagpur", LocalDateTime.parse("02/09/2023 10:00", dateTimeFormatter), 300, 150, 0, EventStatus.COMPLETED));
        addEvent(EventFactory.createEvent("New Year Bash", "Goa", LocalDateTime.parse("31/12/2024 23:59", dateTimeFormatter), 1500, 500, 450, EventStatus.UPCOMING));
        addEvent(EventFactory.createEvent("Food Festival", "Mumbai", LocalDateTime.parse("15/01/2024 12:00", dateTimeFormatter), 200, 400, 390, EventStatus.UPCOMING));
        addEvent(EventFactory.createEvent("Marathon", "Pune", LocalDateTime.parse("10/02/2024 05:30", dateTimeFormatter), 100, 1000, 990, EventStatus.UPCOMING));
        addEvent(EventFactory.createEvent("Stand-Up Comedy", "Delhi", LocalDateTime.parse("05/03/2024 20:00", dateTimeFormatter), 700, 250, 245, EventStatus.UPCOMING));
        addEvent(EventFactory.createEvent("Tech Conference", "Bengaluru", LocalDateTime.parse("20/07/2024 09:00", dateTimeFormatter), 1200, 800, 780, EventStatus.UPCOMING));
        addEvent(EventFactory.createEvent("Classical Music Night", "Chennai", LocalDateTime.parse("18/09/2023 18:00", dateTimeFormatter), 400, 300, 0, EventStatus.COMPLETED));
        addEvent(EventFactory.createEvent("Dandiya Night", "Ahmedabad", LocalDateTime.parse("24/10/2024 21:00", dateTimeFormatter), 600, 500, 500, EventStatus.UPCOMING));
        addEvent(EventFactory.createEvent("Art Exhibition", "Kolkata", LocalDateTime.parse("12/11/2024 11:00", dateTimeFormatter), 100, 200, 190, EventStatus.UPCOMING));
        addEvent(EventFactory.createEvent("Yoga Workshop", "Hyderabad", LocalDateTime.parse("18/09/2023 18:00", dateTimeFormatter), 50, 150, 0, EventStatus.CANCELLED));
        addEvent(EventFactory.createEvent("Book Fair", "Mumbai", LocalDateTime.parse("05/12/2024 10:00", dateTimeFormatter), 20, 1000, 985, EventStatus.UPCOMING));
        addEvent(EventFactory.createEvent("Rock Concert", "Goa", LocalDateTime.parse("15/01/2025 20:00", dateTimeFormatter), 2000, 800, 780, EventStatus.UPCOMING));
        addEvent(EventFactory.createEvent("Drama Festival", "Jaipur", LocalDateTime.parse("18/09/2023 18:00", dateTimeFormatter), 300, 400, 380, EventStatus.POSTPONED));
        
    }

}
