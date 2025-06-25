package EventTicketingSystem.View;

import java.util.*;

public class UserViews {
    Scanner sc = new Scanner(System.in);
    
    public int showUserMenu() {
        while (true) {
            System.out.println("\n\nSelect an option to continue: \n\n1. Browse Events \n2. Show Booked Tickets \n3. Log Out \n");
            System.out.print("Enter Choice: ");

            int option = sc.nextInt();
    
            switch (option) {
                case 1, 2, 3:
                    return option;
                default:
                    System.out.println("Please enter a valid choice!");
                    System.out.println();
            }
        }
    }
}
