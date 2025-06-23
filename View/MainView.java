package EventTicketingSystem.View;

import java.util.*;

public class MainView {
    Scanner sc = new Scanner(System.in);

    public int showMainMenu() {
        while(true) {
            System.out.println("\nWelcome to Event Tickets! \n\nSelect an option to continue: \n\n1. Login \n2. Signup \n3. Exit \n");
            System.out.print("Enter Choice: ");

            int option = sc.nextInt();
    
            switch (option) {
                case 1:
                    return 1;
                case 2:
                    return 2;
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
