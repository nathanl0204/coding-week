package codenames;

public interface ObservableInterface {
    void addObserver(ObserverInterface observer);
    void notifyObserver();
}
