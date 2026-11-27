package travel.service;

import travel.config.AppConfig;
import travel.model.Trip;
import travel.printer.ItineraryPrinter;

public class TripService {
    private final ItineraryPrinter printer;
    private final CostCalculator calculator;

    public TripService(ItineraryPrinter printer, CostCalculator calculator) {
        this.printer = printer;
        this.calculator = calculator;
    }

    public void displayTripSummary(Trip trip) {
        printer.print(trip);
        System.out.println("Booking Status: " + trip.getStatus());
        System.out.println("Pricing Strategy: " + calculator.getPricingStrategy().getName());

        double rawCost = calculator.calculateRawCost(trip);
        double afterStrategy = calculator.calculateStrategyCost(trip);
        double taxRate = AppConfig.getInstance().getTaxRate();
        double finalBudget = afterStrategy * (1.0 + taxRate);

        System.out.printf("Raw Cost: $%.2f | After Strategy: $%.2f | Tax (%.0f%%): $%.2f | Final Budget: $%.2f%n",
                rawCost, afterStrategy, taxRate * 100, afterStrategy * taxRate, finalBudget);
    }
}