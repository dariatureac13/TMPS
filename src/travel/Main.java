package travel;

import java.util.Scanner;
import travel.model.*;
import travel.printer.ConsoleItineraryPrinter;
import travel.printer.ItineraryPrinter;
import travel.service.CostCalculator;
import travel.service.TripService;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("   WELCOME TO TRIP ITINERARY BUILDER!");

        System.out.print("Enter your trip destination (e.g., Rome): ");
        String destination = scanner.nextLine();

        Trip trip = new Trip(destination);
        boolean running = true;

        while (running) {
            System.out.println("\nWhat would you like to add to your itinerary?");
            System.out.println("1. Transportation (flight, train, etc.)");
            System.out.println("2. Accommodation (hotel, apartment)");
            System.out.println("3. Activity (guided tour, museum)");
            System.out.println("4. Finish and print itinerary");
            System.out.print("Select an option (1-4): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter transport type (e.g., Flight Chisinau - Rome): ");
                    String transportType = scanner.nextLine();
                    System.out.print("Enter ticket price ($): ");
                    double ticketPrice = Double.parseDouble(scanner.nextLine());
                    trip.addItem(new Transportation(transportType, ticketPrice));
                    System.out.println("Transportation added successfully!");
                    break;

                case "2":
                    System.out.print("Enter hotel/accommodation name: ");
                    String hotelName = scanner.nextLine();
                    System.out.print("Enter price per night ($): ");
                    double pricePerNight = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter number of nights: ");
                    int nights = Integer.parseInt(scanner.nextLine());
                    trip.addItem(new Accommodation(hotelName, pricePerNight, nights));
                    System.out.println("Accommodation added successfully!");
                    break;

                case "3":
                    System.out.print("Enter activity name: ");
                    String activityName = scanner.nextLine();
                    System.out.print("Enter entry fee ($): ");
                    double entryFee = Double.parseDouble(scanner.nextLine());
                    trip.addItem(new Activity(activityName, entryFee));
                    System.out.println("Activity added successfully!");
                    break;

                case "4":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }

        ItineraryPrinter printer = new ConsoleItineraryPrinter();
        CostCalculator calculator = new CostCalculator();
        TripService tripService = new TripService(printer, calculator);

        System.out.println("\nGenerating your itinerary summary...");
        tripService.displayTripSummary(trip);

        scanner.close();
    }
}