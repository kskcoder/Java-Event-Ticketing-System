package EventTicketingSystem.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import EventTicketingSystem.Model.User;

public class UsersDAO {
    private static final String BASE_URL = "jdbc:postgresql://localhost:5432/eventmanagementsystem";
    private static final String PASSWORD = "1234";
    private static final String USER = "postgres";
    
    private static final String USER_TABLE = "users";

    private static String SELECT_STATEMENT = "select * from ";
    private static String TRUNCATE_STATEMENT = "truncate table ";
    
    //User Related Methods

    public static void save(List<User> users) {
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

    public static List<User> load() {
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
}
