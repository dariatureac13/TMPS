package travel;

import org.junit.jupiter.api.Test;
import travel.command.AddComponentCommand;
import travel.command.CommandHistory;
import travel.command.ConfirmTripCommand;
import travel.composite.ItineraryItemLeaf;
import travel.composite.TripGroup;
import travel.factory.ActivityFactory;
import travel.model.Trip;
import travel.model.Trip.TripStatus;
import travel.observer.EventCounterObserver;
import travel.service.CostCalculator;
import travel.strategy.PeakSeasonPricingStrategy;
import travel.strategy.StandardPricingStrategy;
import travel.strategy.StudentDiscountPricingStrategy;

import static org.junit.jupiter.api.Assertions.*;

class BehavioralPatternsTest {

    @Test
    void commandExecutesAndUndoesAddComponent() {
        TripGroup group = new TripGroup("Activities");
        Trip trip = new Trip("Rome", group);
        CommandHistory history = new CommandHistory();
        ItineraryItemLeaf museum = new ItineraryItemLeaf(
                new ActivityFactory().createItem("Vatican Tour", 40.0, 1)
        );

        history.executeCommand(new AddComponentCommand(trip, group, museum));
        assertEquals(40.0, group.getCost(), 0.001);
        assertEquals(1, group.getChildren().size());

        assertTrue(history.undoLast());
        assertEquals(0.0, group.getCost(), 0.001);
        assertEquals(0, group.getChildren().size());
    }

    @Test
    void commandChangesStatusAndUndoRestoresPreviousStatus() {
        Trip trip = new Trip("Rome", new TripGroup("Root"));
        CommandHistory history = new CommandHistory();

        assertEquals(TripStatus.DRAFT, trip.getStatus());
        history.executeCommand(new ConfirmTripCommand(trip));
        assertEquals(TripStatus.CONFIRMED, trip.getStatus());

        assertTrue(history.undoLast());
        assertEquals(TripStatus.DRAFT, trip.getStatus());
    }

    @Test
    void observerReceivesEventsWhenSubscribedAndStopsAfterUnsubscribe() {
        Trip trip = new Trip("Paris", new TripGroup("Root"));
        EventCounterObserver observer = new EventCounterObserver();

        trip.subscribe(observer);
        trip.setStatus(TripStatus.CONFIRMED, "Paid");
        assertEquals(1, observer.getEventCount());

        trip.unsubscribe(observer);
        trip.setStatus(TripStatus.CANCELLED, "User request");
        assertEquals(1, observer.getEventCount());
    }

    @Test
    void multipleObserversAreNotifiedOnStateChange() {
        Trip trip = new Trip("Paris", new TripGroup("Root"));
        EventCounterObserver obs1 = new EventCounterObserver();
        EventCounterObserver obs2 = new EventCounterObserver();

        trip.subscribe(obs1);
        trip.subscribe(obs2);
        trip.setStatus(TripStatus.CONFIRMED, "Confirmed");

        assertEquals(1, obs1.getEventCount());
        assertEquals(1, obs2.getEventCount());
        assertTrue(obs1.getLastMessage().contains("CONFIRMED"));
    }

    @Test
    void strategiesCalculateDifferentTotalsWithoutModifyingCalculator() {
        TripGroup group = new TripGroup("Root");
        group.add(new ItineraryItemLeaf(new ActivityFactory().createItem("Tour", 100.0, 1)));
        Trip trip = new Trip("Berlin", group);
        CostCalculator calculator = new CostCalculator();

        calculator.setPricingStrategy(new StandardPricingStrategy());
        assertEquals(100.0, calculator.calculateStrategyCost(trip), 0.001);

        calculator.setPricingStrategy(new StudentDiscountPricingStrategy(0.15));
        assertEquals(85.0, calculator.calculateStrategyCost(trip), 0.001);

        calculator.setPricingStrategy(new PeakSeasonPricingStrategy(0.20));
        assertEquals(120.0, calculator.calculateStrategyCost(trip), 0.001);
    }

    @Test
    void commandTriggersObserverAndCostUsesSelectedStrategy() {
        TripGroup group = new TripGroup("Root");
        Trip trip = new Trip("Madrid", group);
        EventCounterObserver observer = new EventCounterObserver();
        trip.subscribe(observer);

        CommandHistory history = new CommandHistory();
        history.executeCommand(new AddComponentCommand(
                trip,
                group,
                new ItineraryItemLeaf(new ActivityFactory().createItem("Flamenco Show", 200.0, 1))
        ));
        history.executeCommand(new ConfirmTripCommand(trip));

        assertEquals(2, observer.getEventCount());
        assertEquals(TripStatus.CONFIRMED, trip.getStatus());

        CostCalculator calculator = new CostCalculator();
        calculator.setPricingStrategy(new StudentDiscountPricingStrategy(0.10));
        assertEquals(180.0, calculator.calculateStrategyCost(trip), 0.001);
    }
}