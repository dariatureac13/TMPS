package travel.service;

import travel.model.ItineraryItem;
import travel.model.Trip;

public class CostCalculator {

    public double calculateTotalCost(Trip trip) {
        double total = 0;
        for (ItineraryItem item : trip.getItems()) {
            total += item.getCost();
        }
        return total;
    }
}