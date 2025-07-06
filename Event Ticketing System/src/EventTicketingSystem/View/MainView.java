package EventTicketingSystem.View;

import java.util.*;
import EventTicketingSystem.Helpers.InputHelper;

public class MainView {
    Scanner sc = new Scanner(System.in);

    public int showMainMenu() {
        while(true) {
            System.out.println("\nWelcome to Event Tickets! \n\nSelect an option to continue: \n\n1. Login \n2. Signup \n3. Exit \n");
            int option = InputHelper.getInt("Enter Choice: ");
    
            switch (option) {
                case 1,2:
                    return option;
                case 3:
                    System.out.println("Thank You! Visit Again!\n");
                    return 3;
                default:
                    System.out.println("Please enter a valid choice!");
                    System.out.println();
            }
        }
    }
}
