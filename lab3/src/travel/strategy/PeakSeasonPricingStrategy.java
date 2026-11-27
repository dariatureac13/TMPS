package travel.strategy;

import travel.model.Trip;

public class PeakSeasonPricingStrategy implements PricingStrategy {
    private final double surchargeRate;

    public PeakSeasonPricingStrategy(double surchargeRate) {
        this.surchargeRate = surchargeRate;
    }

    @Override
    public String getName() {
        return "Peak Season (+" + Math.round(surchargeRate * 100) + "%)";
    }

    @Override
    public double applyPricing(double baseCost, Trip trip) {
        return baseCost * (1.0 + surchargeRate);
    }
}