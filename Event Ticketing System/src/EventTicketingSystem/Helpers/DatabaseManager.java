package EventTicketingSystem.Helpers;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;

import EventTicketingSystem.DAOs.EventsDAO;
import EventTicketingSystem.DAOs.TicketsDAO;
import EventTicketingSystem.DAOs.UsersDAO;
import EventTicketingSystem.Model.Event;
import EventTicketingSystem.Model.Ticket;
import EventTicketingSystem.Model.User;

public class DatabaseManager {
    private static final String BASE_URL = "jdbc:postgresql://localhost:5432/eventmanagementsystem";
    private static final String PASSWORD = "1234";
    private static final String USER = "postgres";

    private static DatabaseManager instance;
    private Connection connection;

    private static final EventsDAO eventsDAO = new EventsDAO();
    private static final UsersDAO usersDAO = new UsersDAO();
    private static final TicketsDAO ticketsDAO = new TicketsDAO();

    private DatabaseManager() {
        try {
            connection = DriverManager.getConnection(BASE_URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Error creating database connection: "+e.getMessage());
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    //Event event methods
    public static void saveEvents(List<Event> events) {
        eventsDAO.save(events);
        return;
    } 

    public static List<Event> loadEvents() {
        return eventsDAO.load();
    }


    //User Related Methods

    public static void saveUsers(List<User> users) {
        usersDAO.save(users);
        return;
    }

    public static List<User> loadUsers() {
        return usersDAO.load();
    }


    //Tickets Related Storage 

    public static void saveTickets(List<Ticket> tickets) {
        ticketsDAO.save(tickets);
        return;
    }

    public static List<String[]> loadTickets() {
        return ticketsDAO.load();       
    }
}
