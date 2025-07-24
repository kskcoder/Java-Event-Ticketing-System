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
import EventTicketingSystem.Model.User;
import EventTicketingSystem.Model.Ticket;
import EventTicketingSystem.Model.Event.EventStatus;

public class FileManager {

    //Events Related Methods
    private static final String EVENT_FILE_NAME = "events.txt";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void saveEvents(List<Event> events) {
        try(FileWriter fileWriter = new FileWriter(EVENT_FILE_NAME, false);
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

        try (FileReader fileReader = new FileReader(EVENT_FILE_NAME);
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


    //User Related Methods
    private static final String USERS_FILE_NAME = "users.txt";

    public static void saveUsers(List<User> users) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(USERS_FILE_NAME, false))) {
            String string;

            for (User user: users) {
                string = user.getUserName()+","+user.getPassword()+","+user.isAdmin();

                bufferedWriter.write(string);
                bufferedWriter.newLine();
            }
        } catch (IOException e){
            System.out.println("Error writing to the file "+e.getMessage());
        }
    }

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(USERS_FILE_NAME))) {
            
            String line;

            while((line = bufferedReader.readLine()) != null) {
                String[] str = line.split(",");

                String username = str[0];
                String password = str[1];
                boolean isAdmin = str[2].equals("true");

                users.add(new User(username, password, isAdmin));
            }
        } catch (IOException e) {
            System.out.println("Error reading from file "+e.getMessage());
        }
        return users;
    }

    //Tickets Related Storage 

    private static final String TICKETS_FILE_NAME = "tickets.txt";

    public static void saveTickets(List<Ticket> tickets) {

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(TICKETS_FILE_NAME, false))) {
            for (Ticket ticket: tickets) {
                String string = ticket.getTicketId()+","
                                +ticket.getTicketUsername()+","
                                +ticket.getRelatedEventId()+","
                                +ticket.getBookedQuantity();

                bufferedWriter.write(string);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to the file "+e.getMessage());
        }
    }
}
