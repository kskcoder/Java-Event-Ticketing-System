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
}

