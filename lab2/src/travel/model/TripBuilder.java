package travel.model;

import travel.composite.ItineraryComponent;
import travel.composite.ItineraryItemLeaf;
import travel.composite.TripGroup;
import travel.factory.AccommodationFactory;
import travel.factory.ActivityFactory;
import travel.factory.ItineraryItemFactory;
import travel.factory.TransportationFactory;

public class TripBuilder {
    private final String destination;
    private final TripGroup rootGroup;
    private TripGroup activeGroup;

    private final ItineraryItemFactory transportFactory = new TransportationFactory();
    private final ItineraryItemFactory accommodationFactory = new AccommodationFactory();
    private final ItineraryItemFactory activityFactory = new ActivityFactory();

    public TripBuilder(String destination) {
        this.destination = destination;
        this.rootGroup = new TripGroup("Itinerary: " + destination);
        this.activeGroup = this.rootGroup;
    }

    public void startSubGroup(String groupName) {
        TripGroup subGroup = new TripGroup(groupName);
        this.activeGroup.add(subGroup);
        this.activeGroup = subGroup;
    }

    public void resetToRootGroup() {
        this.activeGroup = this.rootGroup;
    }

    public String getActiveGroupTitle() {
        return activeGroup.getTitle();
    }

    public void addTransportation(String type, double price) {
        ItineraryItem item = transportFactory.createItem(type, price, 1);
        activeGroup.add(new ItineraryItemLeaf(item));
    }

    public void addAccommodation(String hotelName, double pricePerNight, int nights) {
        ItineraryItem item = accommodationFactory.createItem(hotelName, pricePerNight, nights);
        activeGroup.add(new ItineraryItemLeaf(item));
    }

    public void addActivity(String activityName, double entryFee) {
        ItineraryItem item = activityFactory.createItem(activityName, entryFee, 1);
        activeGroup.add(new ItineraryItemLeaf(item));
    }

    public void addComponent(ItineraryComponent component) {
        activeGroup.add(component);
    }

    public Trip build() {
        return new Trip(destination, rootGroup);
    }
}