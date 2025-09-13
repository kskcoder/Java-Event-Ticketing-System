package EventTicketingSystem.DAOs;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import EventTicketingSystem.Helpers.DatabaseManager;
import EventTicketingSystem.Model.Ticket;

public class TicketsDAO {
    private final Connection con = DatabaseManager.getInstance().getConnection();
    
    private static final String TICKET_TABLE = "tickets";

    private static String SELECT_STATEMENT = "select * from ";
    private static String TRUNCATE_STATEMENT = "truncate table ";
        
    //Tickets Related Storage 

    public void save(List<Ticket> tickets) {
        String statement = "insert into tickets (id, username, relatedEventId, bookedQuantity) values (?, ?, ?, ?)";

        try (PreparedStatement st = con.prepareStatement(statement);
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

    public List<String[]> load() {
        List<String[]> tickets = new ArrayList<>();

        try (Statement st = con.createStatement();) {

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