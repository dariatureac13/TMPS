# Laboratory Work #1: SOLID Principles Implementation

**Topic:** Trip Itinerary Builder  
**Language:** Java  
**Environment:** IntelliJ IDEA  
**Group:** FAF-243   
**Student Name:** Daria Tureac

---

## 1. Project Overview

The **Trip Itinerary Builder** is an interactive Java application designed to manage travel logistics and budget estimation. Users can define a trip destination and dynamically add various expenses, including accommodations, transportation, and activities. The application automatically formats the itinerary details and computes the total travel budget.

The primary objective of this laboratory work is to implement a modular architecture following the **SOLID principles of Object-Oriented Design**, ensuring high maintainability, low coupling, and scalability.

---

## 2. SOLID Principles Breakdown & Code Snippets

### A. Single Responsibility Principle (SRP)

> **Definition:** A class should have one, and only one, reason to change.

#### Application in Project:
I separated domain data, financial computations, and output mechanisms into distinct classes:
* `Trip`: Manages only the storage and retrieval of itinerary items.
* `CostCalculator`: Handles monetary additions and overall budget calculations.
* `ConsoleItineraryPrinter`: Responsible solely for console formatting and output presentation.

#### Code Snippet (`travel/service/CostCalculator.java`):
```java
package travel.service;

import travel.model.ItineraryItem;
import travel.model.Trip;

// Dedicated solely to financial calculations
public class CostCalculator {

    public double calculateTotalCost(Trip trip) {
        double total = 0;
        for (ItineraryItem item : trip.getItems()) {
            total += item.getCost();
        }
        return total;
    }
}
```

### B. Open/Closed Principle (OCP)

> **Definition:** Software entities should be open for extension, but closed for modification.


#### Application in Project:

The polymorphic interface `ItineraryItem` acts as an abstraction for all trip expenses (`Accommodation`, `Transportation`, `Activity`). If a new expense type needs to be introduced in the future (e.g., `CarRental` or `TravelInsurance`), it can be added by implementing `ItineraryItem` without modifying any existing calculation logic in `CostCalculator` or processing logic in `TripService`.

#### Code Snippet (`travel/model/ItineraryItem.java` & `travel/model/Accommodation.java`):
```java
package travel.model;

// Abstraction layer for expense extension
public interface ItineraryItem {
    String getTitle();
    double getCost();
    String getDetails();
}
```
```java
package travel.model;

// Concrete implementation extending functionality without modifying existing classes
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
        return String.format("Accommodation: %s (%d night(s) x $%.2f)", hotelName, nights, pricePerNight);
    }
}
```

### C. Dependency Inversion Principle (DIP)

> **Definition:** High-level modules should not depend on low-level modules. Both should depend on abstractions.


#### Application in Project:

The high-level orchestrator class `TripService` does not instantiate or directly depend on `ConsoleItineraryPrinter`. Instead, it relies on the `ItineraryPrinter` interface passed via constructor injection. This makes the system independent of specific output channels, allowing easy adaptation to file-based (PDF, TXT) or network-based outputs without modifying business logic.

#### Code Snippet (`travel/service/TripService.java`):

```java
package travel.service;

import travel.model.Trip;
import travel.printer.ItineraryPrinter;

public class TripService {
    private final ItineraryPrinter printer; // Depends on abstraction, not concrete class
    private final CostCalculator calculator;

    // Constructor Injection
    public TripService(ItineraryPrinter printer, CostCalculator calculator) {
        this.printer = printer;
        this.calculator = calculator;
    }

    public void displayTripSummary(Trip trip) {
        printer.print(trip);
        double total = calculator.calculateTotalCost(trip);
        System.out.printf("Total Budget: $%.2f%n", total);
    }
}
```

## 3. Package Structure

```
src/
└── travel/
├── model/
│   ├── ItineraryItem.java
│   ├── Accommodation.java
│   ├── Transportation.java
│   ├── Activity.java
│   └── Trip.java
├── service/
│   ├── CostCalculator.java
│   └── TripService.java
├── printer/
│   ├── ItineraryPrinter.java
│   └── ConsoleItineraryPrinter.java
└── Main.java
```

## 4. Conclusion

By organizing the application around SOLID principles:

1. Maintainability is improved through decoupled responsibility layers.

2. Extensibility is simplified via interface abstractions (ItineraryItem and ItineraryPrinter).

3. Testability is enhanced, allowing individual components (such as calculations or printers) to be tested independently.