package EventTicketingSystem.ViewModel;

import EventTicketingSystem.Model.UserManager;
import EventTicketingSystem.View.UserViews;

public class UserViewModel {
    UserManager userManager;
    UserViews userViews;

    public UserViewModel(UserManager userManager, UserViews userViews) {
        this.userManager = userManager;
        this.userViews = userViews;
    }

    public void showUserFlow() {
        while (true) {
            switch (userViews.showMainMenu()) {
                case 1:
                    // createNewAdmin();
                    break;
                case 2:
                    // showEventMenu();
                    break;
                case 3:

                case 4:
                case 5:
                    userManager.logOutUser();
                    userViews.userLoggedOutMessage();
                    return;
            }
        }
    }

}
