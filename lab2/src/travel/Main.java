package travel;

import java.util.Scanner;
import travel.config.AppConfig;
import travel.decorator.InsuranceDecorator;
import travel.decorator.PriorityBookingDecorator;
import travel.facade.TravelBookingFacade;
import travel.model.Trip;
import travel.model.TripBuilder;
import travel.printer.ConsoleItineraryPrinter;
import travel.printer.ItineraryPrinter;
import travel.service.CostCalculator;
import travel.service.TripService;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        AppConfig config = AppConfig.getInstance();
        TravelBookingFacade facade = new TravelBookingFacade();

        System.out.println("   TRIP PLANNER (Creational + Structural Patterns)");
        System.out.println("   System Currency: " + config.getCurrency());

        System.out.print("Enter destination (e.g., Rome): ");
        String destination = scanner.nextLine();

        TripBuilder builder = new TripBuilder(destination);
        boolean running = true;

        while (running) {
            System.out.println("\nActive group: " + builder.getActiveGroupTitle());
            System.out.println("1. Transportation");
            System.out.println("2. Accommodation");
            System.out.println("3. Activity");
            System.out.println("4. Create new sub-group (Composite)");
            System.out.println("5. Switch back to root group");
            System.out.println("6. Add ready weekend package via Facade");
            System.out.println("7. Finish and Build Trip");
            System.out.print("Select option (1-7): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Transport type: ");
                    String type = scanner.nextLine();
                    System.out.print("Price: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    builder.addTransportation(type, price);
                    System.out.println("-> Transportation added");
                    break;

                case "2":
                    System.out.print("Hotel name: ");
                    String hotel = scanner.nextLine();
                    System.out.print("Price per night: ");
                    double nightPrice = Double.parseDouble(scanner.nextLine());
                    System.out.print("Nights count: ");
                    int nights = Integer.parseInt(scanner.nextLine());
                    builder.addAccommodation(hotel, nightPrice, nights);
                    System.out.println("-> Accommodation added");
                    break;

                case "3":
                    System.out.print("Activity name: ");
                    String activity = scanner.nextLine();
                    System.out.print("Entry fee: ");
                    double fee = Double.parseDouble(scanner.nextLine());
                    builder.addActivity(activity, fee);
                    System.out.println("-> Activity added");
                    break;

                case "4":
                    System.out.print("Sub-group name: ");
                    String groupName = scanner.nextLine();
                    builder.startSubGroup(groupName);
                    System.out.println("-> Switched to sub-group: " + groupName);
                    break;

                case "5":
                    builder.resetToRootGroup();
                    System.out.println("-> Switched back to root group");
                    break;

                case "6":
                    System.out.print("Nights for standard package: ");
                    int pkgNights = Integer.parseInt(scanner.nextLine());
                    Trip sampleTrip = facade.createWeekendCityTrip(destination, pkgNights, false, false);
                    builder.addComponent(sampleTrip.getRootComponent());
                    System.out.println("-> Standard package added via Facade");
                    break;

                case "7":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Enter 1-7.");
            }
        }

        Trip trip = builder.build();

        System.out.print("\nAdd Travel Insurance decorator? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.print("Insurance cost: ");
            double insCost = Double.parseDouble(scanner.nextLine());
            trip.setRootComponent(new InsuranceDecorator(trip.getRootComponent(), insCost));
        }

        System.out.print("Add Priority Booking decorator? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.print("Priority fee: ");
            double prioCost = Double.parseDouble(scanner.nextLine());
            trip.setRootComponent(new PriorityBookingDecorator(trip.getRootComponent(), prioCost));
        }

        ItineraryPrinter printer = new ConsoleItineraryPrinter();
        CostCalculator calculator = new CostCalculator();
        TripService tripService = new TripService(printer, calculator);

        System.out.println("\nBuilding itinerary trip...");
        tripService.displayTripSummary(trip);

        scanner.close();
    }
}