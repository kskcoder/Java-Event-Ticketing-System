package EventTicketingSystem.View;

import java.util.Scanner;

public class AdminViews {
    Scanner sc = new Scanner(System.in);

    public int showAdminMenu() {
        while (true) {
            System.out.println("\n\nSelect an option to continue: \n\n1. Create Admin \n2. Events Management \n3. Bookings Management \n4. User Management \n5. Log Out \n");
            System.out.print("Enter Choice: ");

            int option = sc.nextInt();
    
            switch (option) {
                case 1,2,3,4,5:
                    return option;
                default:
                    System.out.println("Please enter a valid choice!");
                    System.out.println();
            }
        }
    }

    public void adminLoggedOutMessage() {
        System.out.println();
        System.out.println("Admin logged out successfully!");
        System.out.println();
    }    

    public int showEventManagementMenu() {
        while (true) {
            System.out.println("\n Select an option to continue: \n1. Create Event \n2. View Events Menu \n3. Update Event Menu \n4. Delete Event \n5. Exit");

            int option = sc.nextInt();
    
            switch (option) {
                case 1,2,3,4:
                    return option;
                case 5:
                    break;
                default:
                    System.out.println("Please enter a valid choice!");
                    System.out.println();
            }
        }
    }

    public int viewEventMenu() {
        while(true) {
            System.out.println("\n Select an option to continue: \n1. View Events by Under Price \n2. View All Events \n3. View Events by Name \n4. View Events by Id \n5. View Events by Status \n6. View Events by Date \n7. View Events by Under Price \n8. Exit");
            
            int option = sc.nextInt();
    
            switch (option) {
                case 1,2,3,4,5,6,7:
                    return option;
                case 8:
                    break;
                default:
                    System.out.println("Please enter a valid choice!");
                    System.out.println();
            }
        }
    }

    public int updateEventMenu() {
        while(true) {
            System.out.println("\n Select an option to continue: \n1. Update Event Name \n2. Update Event Venue \n3. Update Event Date \n4. Update Event Price \n5. View Events by Status \n6. Exit");
            
            int option = sc.nextInt();
    
            switch (option) {
                case 1,2,3,4,5:
                    return option;
                case 6:
                    break;
                default:
                    System.out.println("Please enter a valid choice!");
                    System.out.println();
            }
        }
    }
}

