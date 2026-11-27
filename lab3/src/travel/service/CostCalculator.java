package travel.service;

import travel.model.Trip;
import travel.strategy.PricingStrategy;
import travel.strategy.StandardPricingStrategy;

public class CostCalculator {
    private PricingStrategy pricingStrategy = new StandardPricingStrategy();

    public void setPricingStrategy(PricingStrategy pricingStrategy) {
        if (pricingStrategy != null) {
            this.pricingStrategy = pricingStrategy;
        }
    }

    public PricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }

    public double calculateRawCost(Trip trip) {
        if (trip == null || trip.getRootComponent() == null) {
            return 0.0;
        }
        return trip.getRootComponent().getCost();
    }

    public double calculateStrategyCost(Trip trip) {
        double rawCost = calculateRawCost(trip);
        return pricingStrategy.applyPricing(rawCost, trip);
    }
}