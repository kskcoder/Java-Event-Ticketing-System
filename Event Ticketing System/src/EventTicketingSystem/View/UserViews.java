package EventTicketingSystem.View;

import java.util.*;

import EventTicketingSystem.Helpers.InputHelper;

public class UserViews extends EventBaseViews{
    Scanner sc = new Scanner(System.in);
    
    public int showMainMenu() {
        while (true) {
            System.out.println("\n\nSelect an option to continue: \n\n1. Browse Events \n2. Show Booked Tickets \n3. Log Out \n4. Delete Account\n");
            int option = InputHelper.getInt("Enter Choice: ");
    
            switch (option) {
                case 1, 2, 3, 4:
                    return option;
                default:
                    System.out.println("Please enter a valid choice!");
                    System.out.println();
            }
        }
    }

    public void userLoggedOutMessage() {
        System.out.println();
        System.out.println("User logged out successfully!");
        System.out.println();
        return;
    }    

    public int getEventId() {
        return InputHelper.getInt("Enter event Id to Book tickets(-1 to exit): ");
    }

    public int getQuantity(int quantity,boolean repeated) {
        if (repeated) {
            return InputHelper.getInt("Entered quantity is more than available tickets\nPlease re-enter under"+quantity+": ");
        } else {
            return InputHelper.getInt("Enter quantity you want to buy tickets for under "+quantity+": ");
        }
    }

    public void cannotBookTickets() {
        System.out.println();
        System.out.println("This Event cannot be booked due to some reasons!\nPlease book another event. Sorry For the Inconvenience!");
        return;
    }

    public void eventBookedSuccessfullyMessage() {
        System.out.println();
        System.out.println("Event booked successfully!");
        System.out.println();
        return;
    }
}
