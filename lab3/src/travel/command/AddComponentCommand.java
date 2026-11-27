package travel.command;

import travel.composite.ItineraryComponent;
import travel.composite.TripGroup;
import travel.model.Trip;

public class AddComponentCommand implements TripCommand {
    private final Trip trip;
    private final TripGroup targetGroup;
    private final ItineraryComponent component;

    public AddComponentCommand(Trip trip, TripGroup targetGroup, ItineraryComponent component) {
        this.trip = trip;
        this.targetGroup = targetGroup;
        this.component = component;
    }

    @Override
    public void execute() {
        targetGroup.add(component);
        trip.notifyObservers("Added component '" + component.getTitle() + "' to '" + targetGroup.getTitle() + "'");
    }

    @Override
    public void undo() {
        targetGroup.remove(component);
        trip.notifyObservers("Undid adding component '" + component.getTitle() + "'");
    }

    @Override
    public String getDescription() {
        return "Add '" + component.getTitle() + "'";
    }
}
