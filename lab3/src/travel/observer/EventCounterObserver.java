package travel.observer;

import travel.model.Trip;

public class EventCounterObserver implements TripObserver {
    private int eventCount = 0;
    private String lastMessage = "";

    @Override
    public void onTripEvent(Trip trip, String message) {
        eventCount++;
        lastMessage = message;
    }

    public int getEventCount() {
        return eventCount;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}