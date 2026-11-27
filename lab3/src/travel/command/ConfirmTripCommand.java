package travel.command;

import travel.model.Trip;
import travel.model.Trip.TripStatus;

public class ConfirmTripCommand implements TripCommand {
    private final Trip trip;
    private TripStatus previousStatus;

    public ConfirmTripCommand(Trip trip) {
        this.trip = trip;
    }

    @Override
    public void execute() {
        this.previousStatus = trip.getStatus();
        trip.setStatus(TripStatus.CONFIRMED, "Booking confirmed via command");
    }

    @Override
    public void undo() {
        TripStatus restoreTo = (previousStatus != null) ? previousStatus : TripStatus.DRAFT;
        trip.setStatus(restoreTo, "Booking confirmation undone");
    }

    @Override
    public String getDescription() {
        return "Confirm trip to " + trip.getDestination();
    }
}
