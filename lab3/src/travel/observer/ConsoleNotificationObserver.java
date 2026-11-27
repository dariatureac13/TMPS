package travel.observer;

import travel.model.Trip;

public class ConsoleNotificationObserver implements TripObserver {
    private final String subscriberName;

    public ConsoleNotificationObserver(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    @Override
    public void onTripEvent(Trip trip, String message) {
        System.out.println("[Observer: " + subscriberName + "] Trip to "
                + trip.getDestination() + " (" + trip.getStatus() + "): " + message);
    }
}
