package travel.service;

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
        double total = calculator.calculateTotalCost(trip);
        System.out.printf("Final budget: $%.2f%n", total);
    }
}