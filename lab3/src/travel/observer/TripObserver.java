package travel.observer;

import travel.model.Trip;

public interface TripObserver {
    void onTripEvent(Trip trip, String message);
}