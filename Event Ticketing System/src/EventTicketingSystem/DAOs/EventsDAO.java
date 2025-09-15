package EventTicketingSystem.DAOs;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import EventTicketingSystem.Helpers.DatabaseManager;
import EventTicketingSystem.Model.Event;
import EventTicketingSystem.Model.EventFactory;
import EventTicketingSystem.Model.Event.EventStatus;

public class EventsDAO {
        
    private final Connection con = DatabaseManager.getInstance().getConnection();
    
    private static final String EVENT_TABLE = "events";

    private static String SELECT_STATEMENT = "select * from ";
    private static String TRUNCATE_STATEMENT = "truncate table ";

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    //Event event methods
    public void save(List<Event> events) {
        String statement = "insert into events (id, name, venue, date, price, totalTickets, quantity, status) values (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement st = con.prepareStatement(statement);
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

    public List<Event> load() {
        List<Event> events = new ArrayList<>();

        try (Statement st = con.createStatement();) {

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

                events.add(EventFactory.createEventWithId(id, name, venue, date, price, totalTickets, totalAvailableTickets, status));
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return new ArrayList<Event>(events);
    }
}
