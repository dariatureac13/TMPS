package travel.model;

public class Transportation implements ItineraryItem {
    private final String transportType;
    private final double ticketPrice;

    public Transportation(String transportType, double ticketPrice) {
        this.transportType = transportType;
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String getTitle() { return transportType; }

    @Override
    public double getCost() { return ticketPrice; }

    @Override
    public String getDetails() {
        return String.format("Transport: %s ($%.2f)", transportType, ticketPrice);
    }
}