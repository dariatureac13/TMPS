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
        double subtotal = calculator.calculateTotalCost(trip);
        double taxRate = AppConfig.getInstance().getTaxRate();
        double finalBudget = subtotal * (1.0 + taxRate);
        System.out.printf("Subtotal: $%.2f | Tax (%.0f%%): $%.2f | Final budget: $%.2f%n",
                subtotal, taxRate * 100, subtotal * taxRate, finalBudget);
    }
}