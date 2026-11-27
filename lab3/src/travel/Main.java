package travel;

import java.util.Scanner;
import travel.command.AddComponentCommand;
import travel.command.CommandHistory;
import travel.command.ConfirmTripCommand;
import travel.composite.ItineraryItemLeaf;
import travel.composite.TripGroup;
import travel.config.AppConfig;
import travel.decorator.InsuranceDecorator;
import travel.decorator.PriorityBookingDecorator;
import travel.facade.TravelBookingFacade;
import travel.factory.AccommodationFactory;
import travel.factory.ActivityFactory;
import travel.factory.ItineraryItemFactory;
import travel.factory.TransportationFactory;
import travel.model.Trip;
import travel.model.TripBuilder;
import travel.observer.ConsoleNotificationObserver;
import travel.observer.EventCounterObserver;
import travel.printer.ConsoleItineraryPrinter;
import travel.printer.ItineraryPrinter;
import travel.service.CostCalculator;
import travel.service.TripService;
import travel.strategy.PeakSeasonPricingStrategy;
import travel.strategy.StandardPricingStrategy;
import travel.strategy.StudentDiscountPricingStrategy;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        AppConfig config = AppConfig.getInstance();
        TravelBookingFacade facade = new TravelBookingFacade();

        ItineraryItemFactory transportFactory = new TransportationFactory();
        ItineraryItemFactory accommodationFactory = new AccommodationFactory();
        ItineraryItemFactory activityFactory = new ActivityFactory();

        System.out.println("   TRIP PLANNER (Behavioral + Structural + Creational)   ");
        System.out.println("   System Currency: " + config.getCurrency());

        System.out.print("Enter destination (e.g., Rome): ");
        String destination = scanner.nextLine();

        TripBuilder builder = new TripBuilder(destination);
        Trip trip = builder.build();

        TripGroup activeGroup = (trip.getRootComponent() instanceof TripGroup)
                ? (TripGroup) trip.getRootComponent()
                : new TripGroup("Itinerary: " + destination);
        if (trip.getRootComponent() != activeGroup) {
            trip.setRootComponent(activeGroup);
        }
        TripGroup rootGroup = activeGroup;

        ConsoleNotificationObserver consoleObserver = new ConsoleNotificationObserver("TripDesk");
        EventCounterObserver counterObserver = new EventCounterObserver();
        trip.subscribe(consoleObserver);
        trip.subscribe(counterObserver);

        CommandHistory history = new CommandHistory();

        boolean running = true;
        while (running) {
            System.out.println("\nActive group: " + activeGroup.getTitle() + " | Status: " + trip.getStatus());
            System.out.println("1. Transportation (via Command)");
            System.out.println("2. Accommodation (via Command)");
            System.out.println("3. Activity (via Command)");
            System.out.println("4. Create new sub-group (Composite)");
            System.out.println("5. Switch back to root group");
            System.out.println("6. Add ready weekend package via Facade (via Command)");
            System.out.println("7. Undo last command (Command)");
            System.out.println("8. Confirm trip booking (Command -> State -> Observer)");
            System.out.println("9. Subscribe / Unsubscribe console observer (Observer)");
            System.out.println("10. Finish and calculate price (Strategy)");
            System.out.print("Select option (1-10): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": {
                    System.out.print("Transport type: ");
                    String type = scanner.nextLine();
                    System.out.print("Price: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    ItineraryItemLeaf leaf = new ItineraryItemLeaf(transportFactory.createItem(type, price, 1));
                    history.executeCommand(new AddComponentCommand(trip, activeGroup, leaf));
                    System.out.println("-> Transportation added via Command");
                    break;
                }

                case "2": {
                    System.out.print("Hotel name: ");
                    String hotel = scanner.nextLine();
                    System.out.print("Price per night: ");
                    double nightPrice = Double.parseDouble(scanner.nextLine());
                    System.out.print("Nights count: ");
                    int nights = Integer.parseInt(scanner.nextLine());
                    ItineraryItemLeaf leaf = new ItineraryItemLeaf(accommodationFactory.createItem(hotel, nightPrice, nights));
                    history.executeCommand(new AddComponentCommand(trip, activeGroup, leaf));
                    System.out.println("-> Accommodation added via Command");
                    break;
                }

                case "3": {
                    System.out.print("Activity name: ");
                    String activity = scanner.nextLine();
                    System.out.print("Entry fee: ");
                    double fee = Double.parseDouble(scanner.nextLine());
                    ItineraryItemLeaf leaf = new ItineraryItemLeaf(activityFactory.createItem(activity, fee, 1));
                    history.executeCommand(new AddComponentCommand(trip, activeGroup, leaf));
                    System.out.println("-> Activity added via Command");
                    break;
                }

                case "4": {
                    System.out.print("Sub-group name: ");
                    String groupName = scanner.nextLine();
                    TripGroup subGroup = new TripGroup(groupName);
                    history.executeCommand(new AddComponentCommand(trip, activeGroup, subGroup));
                    activeGroup = subGroup;
                    System.out.println("-> Switched to sub-group: " + groupName);
                    break;
                }

                case "5": {
                    activeGroup = rootGroup;
                    System.out.println("-> Switched back to root group");
                    break;
                }

                case "6": {
                    System.out.print("Nights for standard package: ");
                    int pkgNights = Integer.parseInt(scanner.nextLine());
                    Trip sampleTrip = facade.createWeekendCityTrip(destination, pkgNights, false, false);
                    history.executeCommand(new AddComponentCommand(trip, activeGroup, sampleTrip.getRootComponent()));
                    System.out.println("-> Standard package added via Facade & Command");
                    break;
                }

                case "7": {
                    if (history.undoLast()) {
                        System.out.println("-> Last command undone");
                    } else {
                        System.out.println("-> Nothing to undo");
                    }
                    break;
                }

                case "8": {
                    history.executeCommand(new ConfirmTripCommand(trip));
                    System.out.println("-> Confirm command executed");
                    break;
                }

                case "9": {
                    System.out.print("Subscribe (s) or Unsubscribe (u) console observer? ");
                    String subChoice = scanner.nextLine().trim().toLowerCase();
                    if ("u".equals(subChoice)) {
                        trip.unsubscribe(consoleObserver);
                        System.out.println("-> Console observer unsubscribed");
                    } else {
                        trip.subscribe(consoleObserver);
                        System.out.println("-> Console observer subscribed");
                    }
                    break;
                }

                case "10": {
                    running = false;
                    break;
                }

                default:
                    System.out.println("Invalid choice. Enter 1-10.");
            }
        }

        System.out.print("\nAdd Travel Insurance ? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.print("Insurance cost: ");
            double insCost = Double.parseDouble(scanner.nextLine());
            trip.setRootComponent(new InsuranceDecorator(trip.getRootComponent(), insCost));
        }

        System.out.print("Add Priority Booking ? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.print("Priority fee: ");
            double prioCost = Double.parseDouble(scanner.nextLine());
            trip.setRootComponent(new PriorityBookingDecorator(trip.getRootComponent(), prioCost));
        }

        CostCalculator calculator = new CostCalculator();
        System.out.println("\nChoose pricing strategy (Strategy):");
        System.out.println("1. Standard Pricing");
        System.out.println("2. Student Discount (15% off)");
        System.out.println("3. Peak Season (+20%)");
        System.out.print("Select strategy (1-3): ");
        String strategyChoice = scanner.nextLine().trim();

        switch (strategyChoice) {
            case "2":
                calculator.setPricingStrategy(new StudentDiscountPricingStrategy(0.15));
                break;
            case "3":
                calculator.setPricingStrategy(new PeakSeasonPricingStrategy(0.20));
                break;
            default:
                calculator.setPricingStrategy(new StandardPricingStrategy());
                break;
        }

        ItineraryPrinter printer = new ConsoleItineraryPrinter();
        TripService tripService = new TripService(printer, calculator);

        System.out.println("\nBuilding itinerary trip...");
        tripService.displayTripSummary(trip);
        System.out.println("Total tracked observer events: " + counterObserver.getEventCount());

        scanner.close();
    }
}