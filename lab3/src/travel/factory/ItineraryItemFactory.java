package travel.factory;

import travel.model.ItineraryItem;

public interface ItineraryItemFactory {
    ItineraryItem createItem(String name, double price, int countOrNights);
}