package travel.model;

import travel.factory.AccommodationFactory;
import travel.factory.ActivityFactory;
import travel.factory.TransportationFactory;

public class TripBuilder {
    private final String destination;
    private final Trip trip;

    public TripBuilder(String destination) {
        this.destination = destination;
        this.trip = new Trip(destination);
    }

    public TripBuilder addTransportation(String type, double price) {
        TransportationFactory factory = new TransportationFactory();
        trip.addItem(factory.createItem(type, price, 0));
        return this;
    }

    public TripBuilder addAccommodation(String name, double pricePerNight, int nights) {
        AccommodationFactory factory = new AccommodationFactory();
        trip.addItem(factory.createItem(name, pricePerNight, nights));
        return this;
    }

    public TripBuilder addActivity(String name, double fee) {
        ActivityFactory factory = new ActivityFactory();
        trip.addItem(factory.createItem(name, fee, 0));
        return this;
    }

    public Trip build() {
        return trip;
    }
}
