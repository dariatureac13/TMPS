package travel.factory;

import travel.model.ItineraryItem;
import travel.model.Transportation;

public class TransportationFactory implements ItineraryItemFactory {
    @Override
    public ItineraryItem createItem(String name, double price, int countOrNights) {
        return new Transportation(name, price);
    }
}