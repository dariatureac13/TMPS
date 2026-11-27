package travel.strategy;

import travel.model.Trip;

public interface PricingStrategy {
    String getName();
    double applyPricing(double baseCost, Trip trip);
}
