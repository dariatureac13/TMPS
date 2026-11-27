package travel.observer;

public interface TripSubject {
    void subscribe(TripObserver observer);
    void unsubscribe(TripObserver observer);
    void notifyObservers(String message);
}
