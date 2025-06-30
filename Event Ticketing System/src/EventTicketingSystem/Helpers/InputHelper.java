package EventTicketingSystem.Helpers;

import java.util.*;

public class InputHelper {
    private static final Scanner sc = new Scanner(System.in);

    public static void clearInput() {
        sc.nextLine();
    }

    public static int getInt(String prompt) {
        while (true) {            
            System.out.print(prompt);
            try {
                return sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input! Enter again");
                sc.nextLine();
            }
        }    
    }

    public static String getNonEmptyString(String prompt) {

        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("Input cannot be empty! Please Enter again");
                sc.nextLine();
            }
        }
    }

}
