package travel.model;

import java.util.ArrayList;
import java.util.List;
import travel.composite.ItineraryComponent;
import travel.observer.TripObserver;
import travel.observer.TripSubject;

public class Trip implements TripSubject {
    public enum TripStatus {
        DRAFT,
        CONFIRMED,
        CANCELLED
    }

    private final String destination;
    private ItineraryComponent rootComponent;
    private TripStatus status;
    private final List<TripObserver> observers = new ArrayList<>();

    public Trip(String destination, ItineraryComponent rootComponent) {
        this.destination = destination;
        this.rootComponent = rootComponent;
        this.status = TripStatus.DRAFT;
    }

    public String getDestination() {
        return destination;
    }

    public ItineraryComponent getRootComponent() {
        return rootComponent;
    }

    public void setRootComponent(ItineraryComponent rootComponent) {
        this.rootComponent = rootComponent;
        notifyObservers("Root itinerary component updated");
    }

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status, String reason) {
        this.status = status;
        notifyObservers("Status changed to " + status + " (" + reason + ")");
    }

    @Override
    public void subscribe(TripObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void unsubscribe(TripObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (TripObserver observer : List.copyOf(observers)) {
            observer.onTripEvent(this, message);
        }
    }
}