package EventTicketingSystem.Helpers;

public class HelperInterface {
    public interface Observers {
        void update(String message);
    }

    public interface Subject {
        void addObserver(Observers o);
        void removeObserver(Observers o);
        void notifyObservers(String message);
    }
}
