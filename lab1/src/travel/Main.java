package travel;

import java.util.Scanner;
import travel.config.AppConfig;
import travel.model.*;
import travel.printer.ConsoleItineraryPrinter;
import travel.printer.ItineraryPrinter;
import travel.service.CostCalculator;
import travel.service.TripService;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        AppConfig config = AppConfig.getInstance();

        System.out.println("   TRIP BUILDER (Creational Patterns)");
        System.out.println("   System Currency: " + config.getCurrency());

        System.out.print("Enter destination (e.g., Rome): ");
        String destination = scanner.nextLine();


        TripBuilder builder = new TripBuilder(destination);
        boolean running = true;

        while (running) {
            System.out.println("\nAdd item to itinerary:");
            System.out.println("1. Transportation");
            System.out.println("2. Accommodation");
            System.out.println("3. Activity");
            System.out.println("4. Finish and Build Trip");
            System.out.print("Select option (1-4): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Transport type: ");
                    String type = scanner.nextLine();
                    System.out.print("Price: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    builder.addTransportation(type, price);
                    System.out.println("-> Transportation added ");
                    break;

                case "2":
                    System.out.print("Hotel name: ");
                    String hotel = scanner.nextLine();
                    System.out.print("Price per night: ");
                    double nightPrice = Double.parseDouble(scanner.nextLine());
                    System.out.print("Nights count: ");
                    int nights = Integer.parseInt(scanner.nextLine());
                    builder.addAccommodation(hotel, nightPrice, nights);
                    System.out.println("-> Accommodation added ");
                    break;

                case "3":
                    System.out.print("Activity name: ");
                    String activity = scanner.nextLine();
                    System.out.print("Entry fee: ");
                    double fee = Double.parseDouble(scanner.nextLine());
                    builder.addActivity(activity, fee);
                    System.out.println("-> Activity added ");
                    break;

                case "4":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Enter 1-4.");
            }
        }


        Trip trip = builder.build();

        ItineraryPrinter printer = new ConsoleItineraryPrinter();
        CostCalculator calculator = new CostCalculator();
        TripService tripService = new TripService(printer, calculator);

        System.out.println("\nBuilding itinerary trip...");
        tripService.displayTripSummary(trip);

        scanner.close();
    }
}