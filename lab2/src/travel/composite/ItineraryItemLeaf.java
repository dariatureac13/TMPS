package travel.composite;

import travel.model.ItineraryItem;

public class ItineraryItemLeaf implements ItineraryComponent {
    private final ItineraryItem item;

    public ItineraryItemLeaf(ItineraryItem item) {
        this.item = item;
    }

    @Override
    public String getTitle() {
        return item.getTitle();
    }

    @Override
    public double getCost() {
        return item.getCost();
    }

    @Override
    public String getDetails() {
        return item.getDetails();
    }
}