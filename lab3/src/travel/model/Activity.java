package travel.model;

public class Activity implements ItineraryItem {
    private final String activityName;
    private final double entryFee;

    public Activity(String activityName, double entryFee) {
        this.activityName = activityName;
        this.entryFee = entryFee;
    }

    @Override
    public String getTitle() { return activityName; }

    @Override
    public double getCost() { return entryFee; }

    @Override
    public String getDetails() {
        return String.format("Activity: %s ($%.2f)", activityName, entryFee);
    }
}