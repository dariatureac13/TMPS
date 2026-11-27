package travel.factory;

import travel.model.Activity;
import travel.model.ItineraryItem;

public class ActivityFactory implements ItineraryItemFactory {
    @Override
    public ItineraryItem createItem(String name, double price, int countOrNights) {
        return new Activity(name, price);
    }
}
