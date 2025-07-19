package EventTicketingSystem.Helpers;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import EventTicketingSystem.Model.Event;
import EventTicketingSystem.Model.Event.EventStatus;

public class FileManager {
    private static final String FILE_NAME = "events.txt";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void saveEvents(List<Event> events) {
        try(FileWriter fileWriter = new FileWriter(FILE_NAME, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

                for (Event event: events) {
                    String line = event.getEventId()
                    +","+event.getEventName()
                    +","+event.getVenue()
                    +","+event.getDate().format(dateTimeFormatter)
                    +","+event.getPrice()
                    +","+event.getTotalTickets()
                    +","+event.getQuantity()
                    +","+event.getStatus().toString();

                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
        } catch (IOException e) {
            System.out.println("Error writing to the file "+e.getMessage());
        }
    }

    public static List<Event> loadEvents() {
        List<Event> events = new ArrayList<>();

        try (FileReader fileReader = new FileReader(FILE_NAME);
        BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] eventString = line.split(",");

                int id = Integer.parseInt(eventString[0]);
                String name =  eventString[1];
                String venue = eventString[2];
                LocalDateTime date = LocalDateTime.parse(eventString[3], dateTimeFormatter);
                int price = Integer.parseInt(eventString[4]);
                int totalTickets = Integer.parseInt(eventString[5]);
                int totalAvailableTickets = Integer.parseInt(eventString[6]);
                EventStatus status;
                try {
                    status = EventStatus.valueOf(eventString[7]);
                } catch (IllegalArgumentException e) {
                    status = EventStatus.UPCOMING;
                }

                events.add(new Event(id, name, venue, date, price, totalTickets, totalAvailableTickets, status));
            }
        } catch (IOException e) {
            System.out.println("Error reading from the file "+e.getMessage());
        }

        return new ArrayList<Event>(events);
    }
}
