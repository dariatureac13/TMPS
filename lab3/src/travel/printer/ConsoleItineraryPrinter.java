package travel.printer;

import travel.composite.ItineraryComponent;
import travel.model.Trip;

public class ConsoleItineraryPrinter implements ItineraryPrinter {
    @Override
    public void print(Trip trip) {
        System.out.println(" Trip Itinerary: " + trip.getDestination().toUpperCase());

        ItineraryComponent root = trip.getRootComponent();
        if (root == null) {
            System.out.println("Itinerary is empty.");
            return;
        }

        System.out.println(root.getDetails());
    }
}