package EventTicketingSystem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import EventTicketingSystem.ViewModel.MainViewModel;

class LoginTest {

    @Test
    void printAnything() {
        MainViewModel.start();
        Assertions.assertTrue(MainViewModel.handleLogin("Admin", "0000"));
    }
}
