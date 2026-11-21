package travel.service;

import travel.model.Trip;

public class CostCalculator {

    public double calculateTotalCost(Trip trip) {
        if (trip == null || trip.getRootComponent() == null) {
            return 0.0;
        }
        return trip.getRootComponent().getCost();
    }
}