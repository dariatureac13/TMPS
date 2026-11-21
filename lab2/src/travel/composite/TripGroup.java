package travel.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TripGroup implements ItineraryComponent {
    private final String title;
    private final List<ItineraryComponent> children = new ArrayList<>();

    public TripGroup(String title) {
        this.title = title;
    }

    public void add(ItineraryComponent component) {
        children.add(component);
    }

    public void remove(ItineraryComponent component) {
        children.remove(component);
    }

    public List<ItineraryComponent> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public double getCost() {
        double sum = 0.0;
        for (ItineraryComponent child : children) {
            sum += child.getCost();
        }
        return sum;
    }

    @Override
    public String getDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Group [%s] (Subtotal: $%.2f)", title, getCost()));
        for (ItineraryComponent child : children) {
            sb.append("\n   - ").append(child.getDetails());
        }
        return sb.toString();
    }
}