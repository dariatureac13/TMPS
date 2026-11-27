package travel.strategy;

import travel.model.Trip;

public class StudentDiscountPricingStrategy implements PricingStrategy {
    private final double discountRate;

    public StudentDiscountPricingStrategy(double discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public String getName() {
        return "Student Discount (" + Math.round(discountRate * 100) + "% off)";
    }

    @Override
    public double applyPricing(double baseCost, Trip trip) {
        return baseCost * (1.0 - discountRate);
    }
}