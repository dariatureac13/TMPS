package travel.strategy;

import travel.model.Trip;

public class StandardPricingStrategy implements PricingStrategy {
    @Override
    public String getName() {
        return "Standard Pricing";
    }

    @Override
    public double applyPricing(double baseCost, Trip trip) {
        return baseCost;
    }
}
