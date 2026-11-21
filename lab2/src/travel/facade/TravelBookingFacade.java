package travel.facade;

import travel.composite.ItineraryComponent;
import travel.composite.ItineraryItemLeaf;
import travel.composite.TripGroup;
import travel.config.AppConfig;
import travel.decorator.InsuranceDecorator;
import travel.decorator.PriorityBookingDecorator;
import travel.factory.AccommodationFactory;
import travel.factory.ActivityFactory;
import travel.factory.ItineraryItemFactory;
import travel.factory.TransportationFactory;
import travel.model.Trip;
import travel.model.TripBuilder;

public class TravelBookingFacade {
    private final AppConfig config = AppConfig.getInstance();
    private final ItineraryItemFactory transportFactory = new TransportationFactory();
    private final ItineraryItemFactory accommodationFactory = new AccommodationFactory();
    private final ItineraryItemFactory activityFactory = new ActivityFactory();

    public Trip createWeekendCityTrip(String destination, int nights, boolean withInsurance, boolean withPriority) {
        TripBuilder builder = new TripBuilder(destination);

        builder.startSubGroup("Arrival & Stay");
        builder.addComponent(new ItineraryItemLeaf(
                transportFactory.createItem("Round-trip Flight", 210.0, 1)
        ));
        builder.addComponent(new ItineraryItemLeaf(
                accommodationFactory.createItem("Central Hotel", 95.0, nights)
        ));

        builder.resetToRootGroup();
        builder.startSubGroup("Activities");
        builder.addComponent(new ItineraryItemLeaf(
                activityFactory.createItem("Walking City Tour", 35.0, 1)
        ));

        Trip trip = builder.build();
        ItineraryComponent component = trip.getRootComponent();

        if (withInsurance) {
            component = new InsuranceDecorator(component, 25.0);
        }
        if (withPriority) {
            component = new PriorityBookingDecorator(component, 15.0);
        }

        trip.setRootComponent(component);
        return trip;
    }

    public String getCurrency() {
        return config.getCurrency();
    }
}