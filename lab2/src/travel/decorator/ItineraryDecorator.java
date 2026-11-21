package travel.decorator;

import travel.composite.ItineraryComponent;

public abstract class ItineraryDecorator implements ItineraryComponent {
    protected final ItineraryComponent wrapped;

    protected ItineraryDecorator(ItineraryComponent wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String getTitle() {
        return wrapped.getTitle();
    }

    @Override
    public double getCost() {
        return wrapped.getCost();
    }

    @Override
    public String getDetails() {
        return wrapped.getDetails();
    }
}