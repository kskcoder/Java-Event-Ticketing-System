package EventTicketingSystem.Helpers;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import EventTicketingSystem.Model.Event;
import EventTicketingSystem.Model.Event.EventStatus;
import EventTicketingSystem.Model.Ticket;
import EventTicketingSystem.Model.User;

public class DatabaseManager {
    private static final String BASE_URL = "jdbc:postgresql://localhost:5432/eventmanagementsystem";
    private static final String PASSWORD = "1234";
    private static final String USER = "postgres";
    
    private static final String USER_TABLE = "users";
    private static final String EVENT_TABLE = "events";
    private static final String TICKET_TABLE = "tickets";

    private static String SELECT_STATEMENT = "select * from ";
    private static String TRUNCATE_STATEMENT = "truncate table ";

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    //Event event methods
    public static void saveEvents(List<Event> events) {
        String statement = "insert into events (id, name, venue, date, price, totalTickets, quantity, status) values (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = DriverManager.getConnection(BASE_URL, USER, PASSWORD);
            PreparedStatement st = con.prepareStatement(statement);
            Statement stNew = con.createStatement();) {        

            stNew.executeUpdate(TRUNCATE_STATEMENT+EVENT_TABLE);

            for (Event event: events) {
                st.setString(1, String.valueOf(event.getEventId()));
                st.setString(2, event.getEventName());
                st.setString(3, event.getVenue());
                st.setString(4, event.getDate().format(dateTimeFormatter));                
                st.setString(5, String.valueOf(event.getPrice()));
                st.setString(6, String.valueOf(event.getTotalTickets()));
                st.setString(7, String.valueOf(event.getQuantity()));
                st.setString(8, event.getStatus().toString());

                st.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    } 

    public static List<Event> loadEvents() {
        List<Event> events = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(BASE_URL, USER, PASSWORD);
        Statement st = con.createStatement();) {

            ResultSet rs = st.executeQuery(SELECT_STATEMENT+EVENT_TABLE);

            while(rs.next()) {
                    int id = Integer.parseInt(rs.getString(1));
                    String name = rs.getString(2);
                    String venue = rs.getString(3);
                    LocalDateTime date = LocalDateTime.parse(rs.getString(4), dateTimeFormatter);
                    int price = Integer.parseInt(rs.getString(5));
                    int totalTickets = Integer.parseInt(rs.getString(6));
                    int totalAvailableTickets = Integer.parseInt(rs.getString(7));
                    EventStatus status;
                    try {
                        status = EventStatus.valueOf(rs.getString(8));
                    } catch (IllegalArgumentException e) {
                        status = EventStatus.UPCOMING;
                    }

                events.add(new Event(id, name, venue, date, price, totalTickets, totalAvailableTickets, status));
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return new ArrayList<Event>(events);
    }


    //User Related Methods

    public static void saveUsers(List<User> users) {
        String statement = "insert into users (username, password, isAdmin) values (?, ?, ?)";

        try (Connection con = DriverManager.getConnection(BASE_URL, USER, PASSWORD);
        PreparedStatement st = con.prepareStatement(statement);
        Statement stNew = con.createStatement();) {
            stNew.executeUpdate(TRUNCATE_STATEMENT+USER_TABLE);

            for (User user: users) {
                st.setString(1, user.getUserName());
                st.setString(2, user.getPassword());
                st.setString(3, String.valueOf(user.isAdmin()));
                st.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(BASE_URL, USER, PASSWORD);
        Statement st = con.createStatement();) {

            ResultSet rs = st.executeQuery(SELECT_STATEMENT+USER_TABLE);

            while(rs.next()) {
                String username = rs.getString(1);
                String password = rs.getString(2);
                Boolean isAdmin = rs.getString(3).equals("true");
                users.add(new User(username, password, isAdmin));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return users;
    }


    //Tickets Related Storage 

    public static void saveTickets(List<Ticket> tickets) {
        String statement = "insert into tickets (id, username, relatedEventId, bookedQuantity) values (?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(BASE_URL, USER, PASSWORD);
        PreparedStatement st = con.prepareStatement(statement);
        Statement stNew = con.createStatement();) {            
            stNew.executeUpdate(TRUNCATE_STATEMENT+TICKET_TABLE);

            for (Ticket ticket: tickets) {
                st.setString(1, String.valueOf(ticket.getTicketId()));
                st.setString(2, ticket.getTicketUsername());
                st.setString(3,  String.valueOf(ticket.getRelatedEventId()));
                st.setString(4,  String.valueOf(ticket.getBookedQuantity()));
                
                st.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<String[]> loadTickets() {
        List<String[]> tickets = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(BASE_URL, USER, PASSWORD);
        Statement st = con.createStatement();) {

            ResultSet rs = st.executeQuery(SELECT_STATEMENT+TICKET_TABLE);

            while(rs.next()) {
                String[] str = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)};
                tickets.add(str);
            }
        } catch (Exception e) {
            System.out.println("Error reading from file: "+e.getMessage());
        }
        return tickets;       
    }
}
