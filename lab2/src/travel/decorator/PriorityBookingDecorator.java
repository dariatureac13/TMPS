package travel.decorator;

import travel.composite.ItineraryComponent;

public class PriorityBookingDecorator extends ItineraryDecorator {
    private final double priorityPrice;

    public PriorityBookingDecorator(ItineraryComponent wrapped, double priorityPrice) {
        super(wrapped);
        this.priorityPrice = priorityPrice;
    }

    @Override
    public double getCost() {
        return super.getCost() + priorityPrice;
    }

    @Override
    public String getDetails() {
        return super.getDetails() + String.format(" + [Priority Booking: $%.2f]", priorityPrice);
    }
}