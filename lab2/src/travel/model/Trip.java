package travel.model;

import travel.composite.ItineraryComponent;

public class Trip {
    private final String destination;
    private ItineraryComponent rootComponent;

    public Trip(String destination, ItineraryComponent rootComponent) {
        this.destination = destination;
        this.rootComponent = rootComponent;
    }

    public String getDestination() {
        return destination;
    }

    public ItineraryComponent getRootComponent() {
        return rootComponent;
    }

    public void setRootComponent(ItineraryComponent rootComponent) {
        this.rootComponent = rootComponent;
    }
}