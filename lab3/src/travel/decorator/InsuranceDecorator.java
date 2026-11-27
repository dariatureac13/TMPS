package travel.decorator;

import travel.composite.ItineraryComponent;

public class InsuranceDecorator extends ItineraryDecorator {
    private final double insurancePrice;

    public InsuranceDecorator(ItineraryComponent wrapped, double insurancePrice) {
        super(wrapped);
        this.insurancePrice = insurancePrice;
    }

    @Override
    public double getCost() {
        return super.getCost() + insurancePrice;
    }

    @Override
    public String getDetails() {
        return super.getDetails() + String.format(" + [Travel Insurance: $%.2f]", insurancePrice);
    }
}