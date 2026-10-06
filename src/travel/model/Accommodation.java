package travel.model;

public class Accommodation implements ItineraryItem {
    private final String hotelName;
    private final double pricePerNight;
    private final int nights;

    public Accommodation(String hotelName, double pricePerNight, int nights) {
        this.hotelName = hotelName;
        this.pricePerNight = pricePerNight;
        this.nights = nights;
    }

    @Override
    public String getTitle() { return hotelName; }

    @Override
    public double getCost() { return pricePerNight * nights; }

    @Override
    public String getDetails() {
        return String.format("Accomodation: %s (%d nights x $%.2f)", hotelName, nights, pricePerNight);
    }
}