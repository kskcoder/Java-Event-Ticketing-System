package EventTicketingSystem.Model;

import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class EventManager {
    private List<Event> events = new ArrayList<Event>();

    public void addEvent(Event event) {
        events.add(event);
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
    }

    public void updateEventVenue(int id, String venue) {
        Event event = getEventById(id);

        if (event != null) {
            event.setEventVenue(venue);
        }
    }

    public void updateEventDate(int id, LocalDateTime date) {
        Event event = getEventById(id);

        if (event != null) {
            event.setEventDate(date);
        }
    }

    public void updateEventPrice(int id, int price) {
        Event event = getEventById(id);

        if (event != null) {
            event.setEventPrice(price);
        }
    }

    public void updateEventStatus(int id, Event.EventStatus status) {
        Event event = getEventById(id);

        if (event != null) {
            event.setEventStatus(status);
        }
    }


}
