package travel.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trip {
    private final String destination;
    private final List<ItineraryItem> items = new ArrayList<>();

    public Trip(String destination) {
        this.destination = destination;
    }

    public void addItem(ItineraryItem item) {
        items.add(item);
    }

    public List<ItineraryItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public String getDestination() {
        return destination;
    }
}