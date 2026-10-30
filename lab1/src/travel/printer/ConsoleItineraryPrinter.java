package travel.printer;

import travel.model.ItineraryItem;
import travel.model.Trip;

public class ConsoleItineraryPrinter implements ItineraryPrinter {
    @Override
    public void print(Trip trip) {
        System.out.println(" Trip Itinerary: " + trip.getDestination().toUpperCase());

        if (trip.getItems().isEmpty()) {
            System.out.println("Itinerary is empty.");
            return;
        }

        for (int i = 0; i < trip.getItems().size(); i++) {
            ItineraryItem item = trip.getItems().get(i);
            System.out.printf("%d. %s%n", (i + 1), item.getDetails());
        }
    }
}