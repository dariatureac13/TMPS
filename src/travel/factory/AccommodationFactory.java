package travel.factory;

import travel.model.Accommodation;
import travel.model.ItineraryItem;

public class AccommodationFactory implements ItineraryItemFactory {
    @Override
    public ItineraryItem createItem(String name, double price, int nights) {
        return new Accommodation(name, price, nights);
    }
}
