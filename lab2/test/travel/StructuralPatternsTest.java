package travel;

import org.junit.jupiter.api.Test;
import travel.composite.ItineraryComponent;
import travel.composite.ItineraryItemLeaf;
import travel.composite.TripGroup;
import travel.config.AppConfig;
import travel.decorator.InsuranceDecorator;
import travel.decorator.PriorityBookingDecorator;
import travel.facade.TravelBookingFacade;
import travel.factory.ActivityFactory;
import travel.model.Accommodation;
import travel.model.ItineraryItem;
import travel.model.Trip;

import static org.junit.jupiter.api.Assertions.*;

class StructuralPatternsTest {

    // --- COMPOSITE ---

    @Test
    void leafAndGroupShareCommonComponentInterface() {
        ItineraryItem hotel = new Accommodation("Grand Hotel", 100.0, 2); // 200.0
        ItineraryComponent leaf = new ItineraryItemLeaf(hotel);

        TripGroup group = new TripGroup("Stay Group");
        group.add(leaf);

        ItineraryComponent leafRef = leaf;
        ItineraryComponent groupRef = group;

        assertEquals(200.0, leafRef.getCost(), 0.001);
        assertEquals(200.0, groupRef.getCost(), 0.001);
        assertTrue(groupRef.getDetails().contains("Grand Hotel"));
    }

    @Test
    void nestedTripGroupsSumCostAndIncludeDetails() {
        ActivityFactory factory = new ActivityFactory();

        TripGroup day1 = new TripGroup("Day 1");
        day1.add(new ItineraryItemLeaf(factory.createItem("Museum", 25.0, 1)));

        TripGroup day2 = new TripGroup("Day 2");
        day2.add(new ItineraryItemLeaf(factory.createItem("Boat Tour", 45.0, 1)));

        TripGroup fullGroup = new TripGroup("Full Plan");
        fullGroup.add(day1);
        fullGroup.add(day2);

        assertEquals(70.0, fullGroup.getCost(), 0.001);
        assertTrue(fullGroup.getDetails().contains("Museum"));
        assertTrue(fullGroup.getDetails().contains("Boat Tour"));
    }

    // --- DECORATOR ---

    @Test
    void decoratorsGiveSameCostInAnyOrder() {
        ItineraryComponent base = new ItineraryItemLeaf(new Accommodation("Hotel", 150.0, 2)); // 300.0

        ItineraryComponent insuranceFirst = new PriorityBookingDecorator(
                new InsuranceDecorator(base, 30.0),
                20.0
        );

        ItineraryComponent priorityFirst = new InsuranceDecorator(
                new PriorityBookingDecorator(base, 20.0),
                30.0
        );

        assertEquals(350.0, insuranceFirst.getCost(), 0.001);
        assertEquals(350.0, priorityFirst.getCost(), 0.001);
        assertEquals(insuranceFirst.getCost(), priorityFirst.getCost(), 0.001);
    }

    @Test
    void decoratorsCanWrapCompositeGroupAndAppendDetails() {
        TripGroup group = new TripGroup("City Pack");
        group.add(new ItineraryItemLeaf(new ActivityFactory().createItem("City Pass", 50.0, 1)));

        ItineraryComponent decorated = new InsuranceDecorator(group, 15.0);

        assertEquals(65.0, decorated.getCost(), 0.001);
        assertTrue(decorated.getDetails().contains("Travel Insurance"));
        assertTrue(decorated.getDetails().contains("15"));
    }

    // --- FACADE ---

    @Test
    void facadeBuildsPackageWithoutOptionalDecorators() {
        TravelBookingFacade facade = new TravelBookingFacade();

        // Flight (210) + Hotel (95 * 2 = 190) + Tour (35) = 435.0
        Trip trip = facade.createWeekendCityTrip("Rome", 2, false, false);
        double cost = trip.getRootComponent().getCost();
        String details = trip.getRootComponent().getDetails();

        assertEquals(435.0, cost, 0.001);
        assertFalse(details.contains("Travel Insurance"));
        assertFalse(details.contains("Priority Booking"));
    }

    @Test
    void facadeAppliesInsuranceAndPriorityDecoratorsWhenRequested() {
        TravelBookingFacade facade = new TravelBookingFacade();

        // Base 435.0 + Insurance 25.0 + Priority 15.0 = 475.0
        Trip trip = facade.createWeekendCityTrip("Rome", 2, true, true);
        double cost = trip.getRootComponent().getCost();
        String details = trip.getRootComponent().getDetails();

        assertEquals(475.0, cost, 0.001);
        assertTrue(details.contains("Travel Insurance"));
        assertTrue(details.contains("25"));
        assertTrue(details.contains("Priority Booking"));
        assertTrue(details.contains("15"));
        assertNotNull(AppConfig.getInstance().getCurrency());
    }
}
