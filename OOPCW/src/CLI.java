// CLI class for the user to interact with the system
import java.util.List;
import java.util.Scanner;

public class CLI {
    private Scanner scanner;
    private EventManager eventManager;
    private UserManager userManager;
    private User currentUser;

    public CLI(EventManager eventManager, UserManager userManager) {
        this.scanner = new Scanner(System.in);
        this.eventManager = eventManager;
        this.userManager = userManager;
        this.currentUser = null;
    }

    public void start() {
    	// Start message only runs at beginning of program
        System.out.println("Welcome to the Performance Hall Management System");
        boolean running = true;

        while (running) {
            // User login page
            login();
            boolean loggedIn = true;
            while (loggedIn && currentUser != null) {
                displayMainMenu();
                int choice = getIntInput("Enter your choice: ");
                // When logged in as an administrator provide admin switch case
                if (currentUser instanceof Admin) {
                    loggedIn = handleAdminChoice(choice);
                // When logged in as a customer provide customer switch case
                } else if (currentUser instanceof Customer) {
                    loggedIn = handleCustomerChoice(choice);
                }
            }

            // If user logs out, set current user to null
            if (!loggedIn && currentUser != null) {
                currentUser = null;
                System.out.println("Logged out successfully.");
            }
        }
    }

    private void login() {
    	// Display all available users to log into
        List < User > users = userManager.getAllUsers();

        System.out.println("\nAvailable Users:");
        for (User user: users) {
            System.out.println(user.getUsername());
        }

        while (currentUser == null) {
            String username = getStringInput("\nEnter username to login: ");
            currentUser = userManager.findUserByUsername(username);

            if (currentUser == null) {
                System.out.println("Invalid username.");
            }
        }
        // If username is a match, logs in as the user
        System.out.println("Logged in as " + currentUser.getName() + " (" + currentUser.getRole() + ")");
    }

    private void displayMainMenu() {
        // User main menu
        System.out.println("\nMain Menu:");
        // Admin main menu
        if (currentUser instanceof Admin) {
            System.out.println("1. View all events");
            System.out.println("2. Add new event");
            System.out.println("0. Logout");
        // Customer main menu
        } else if (currentUser instanceof Customer) {
            System.out.println("1. View all events");
            System.out.println("2. Add tickets to basket");
            System.out.println("3. View basket");
            System.out.println("4. Pay for tickets");
            System.out.println("5. Cancel basket");
            System.out.println("6. Search by event ID");
            System.out.println("7. Search performances by language");
            System.out.println("0. Logout");
        }
    }

    private boolean handleAdminChoice(int choice) {
        // Returns false when the user wants to logout
        switch (choice) {
        // Calling a number returns the corresponding admin method
            case 1:
                viewAllEvents(true);
                return true;
            case 2:
                addNewEvent();
                return true;
            case 0:
                return false;
            default:
                System.out.println("Invalid choice. Please try again.");
                return true;
        }
    }

    private boolean handleCustomerChoice(int choice) {
        // Returns false when the user wants to logout
        switch (choice) {
        // Calling a number returns the corresponding customer method
            case 1:
                viewAllEvents(false);
                return true;
            case 2:
                addTicketToBasket();
                return true;
            case 3:
                viewBasket();
                return true;
            case 4:
                payForTickets();
                return true;
            case 5:
                cancelBasket();
                return true;
            case 6:
                searchByEventID();
                return true;
            case 7:
                searchByLanguage();
                return true;
            case 0:
                return false;
            default:
                System.out.println("Invalid choice. Please try again.");
                return true;
        }
    }

    private void viewAllEvents(boolean showPerformanceFee) {
        List < LiveEvent > events = eventManager.getAllEventsSorted();
        System.out.println("\nAll Events:");
        // Displays all events sorted by price
        for (LiveEvent event: events) {
            System.out.println("ID: " + event.getEventID() + ", Name: " + event.getEventName() +
                ", Type: " + event.getEventCategory() +
                ", Age: " + event.getAgeRestriction() +
                ", Available: " + event.getQuantityInStock() +
                ", Price: £" + event.getTicketPrice());

            if (event instanceof MusicEvent) {
                System.out.println(" Music Type: " + ((MusicEvent) event).getMusicType() +
                    ", Performers: " + ((MusicEvent) event).getNumberOfPerformers());
            } else if (event instanceof PerformanceEvent) {
                System.out.println(" Performance Type: " + ((PerformanceEvent) event).getPerformanceType() +
                    ", Language: " + ((PerformanceEvent) event).getLanguage());
            }
            // If called from Admin menu, displays event performance fee
            if (showPerformanceFee) {
                System.out.println(" Performance Fee: £" + event.getPerformanceFee());
            }
            System.out.println();
        }
    }

    private void addNewEvent() {
        try {
            // Gets inputs individually for new events and create a new object for them
            System.out.println("\nAdd New Event:");
            int eventID = getIntInput("Enter a 5-digit event ID: ");
            // Ensures eventID is 5-digits long
            while (eventID < 10000 || eventID > 99999) {
                eventID = getIntInput("Please enter a 5-digit event ID: ");
            }
            String eventName = getStringInput("Enter event name: ");
            // Ensures event name isn't null
            while (eventName.trim().isEmpty()) {
                eventName = getStringInput("Enter a value that isn't null: ");
            }
            System.out.println("Select age restriction:");
            System.out.println("1. Adults");
            System.out.println("2. All");
            int ageChoice = getIntInput("Enter choice: ");
            // Ensures age restriction is selected
            while (ageChoice != 1 && ageChoice != 2) {
                ageChoice = getIntInput("Please select option 1 or 2: ");
            }
            AgeRestrictionCategory restriction = (ageChoice == 1) ?
                AgeRestrictionCategory.ADULTS : AgeRestrictionCategory.ALL;
            int quantity = getIntInput("Enter quantity of ticket: ");
            // Ensures quantity is a valid number
            while (quantity < 0) {
                quantity = getIntInput("Please select a non-negative quantity: ");
            }
            double fee = getDoubleInput("Enter performance fee: ");
            // Ensures fee is a valid number
            while (fee < 0) {
                fee = getIntInput("Please select a non-negative fee: ");
            }
            double price = getDoubleInput("Enter ticket price: ");
            // Ensures price is a valid number
            while (price < 0) {
                price = getIntInput("Please select a non-negative price: ");
            }
            System.out.println("Select event category:");
            System.out.println("1. Music");
            System.out.println("2. Performance");
            int categoryChoice = getIntInput("Enter choice: ");
            // Ensures event category is selected
            while (categoryChoice != 1 && categoryChoice != 2) {
                categoryChoice = getIntInput("Please select option 1 or 2: ");
            }
            LiveEvent newEvent = null;

            if (categoryChoice == 1) {
                System.out.println("Select music type:");
                System.out.println("1. Live Concert");
                System.out.println("2. DJ Set");
                int musicTypeChoice = getIntInput("Enter choice: ");
                // Ensures music type choice is selected
                while (musicTypeChoice != 1 && musicTypeChoice != 2) {
                    musicTypeChoice = getIntInput("Please select option 1 or 2: ");
                }
                int performers = getIntInput("Enter number of performers: ");
                // Ensures number of performers is a valid number
                while (performers <= 0) {
                    performers = getIntInput("Please enter a positive number of performers: ");
                }
                if (musicTypeChoice == 1) {
                    newEvent = new LiveConcert(eventID, eventName, restriction, quantity, fee, price, performers);
                } else if (musicTypeChoice == 2) {
                    newEvent = new DJSet(eventID, eventName, restriction, quantity, fee, price, performers);
                }
            } else if (categoryChoice == 2) {
                System.out.println("Select performance type:");
                System.out.println("1. Theatre");
                System.out.println("2. Stand-up Comedy");
                System.out.println("3. Magic");
                int performanceTypeChoice = getIntInput("Enter choice: ");
                // Ensures performance type choice is selected
                while (performanceTypeChoice < 1 || performanceTypeChoice > 3) {
                    performanceTypeChoice = getIntInput("Please select an option between 1 and 3: ");
                }
                String language = getStringInput("Enter language: ");
                // Ensures language isn't null
                while (language.trim().isEmpty()) {
                    language = getStringInput("Please enter a value that isn't null: ");
                }
                if (performanceTypeChoice == 1) {
                    newEvent = new Theatre(eventID, eventName, restriction, quantity, fee, price, language);
                } else if (performanceTypeChoice == 2) {
                    newEvent = new StandupComedy(eventID, eventName, restriction, quantity, fee, price, language);
                } else if (performanceTypeChoice == 3) {
                    newEvent = new Magic(eventID, eventName, restriction, quantity, fee, price, language);
                }
            }
            // Saves event to Stock.txt
            boolean added = eventManager.addEvent(newEvent);
            if (added) {
                System.out.println("Event added successfully!");
            } else {
                System.out.println("Error: Event with this ID already exists.");
            }
        } catch (Exception error) {
            System.out.println("Error adding event: " + error.getMessage());
        }
    }

    private void addTicketToBasket() {
        try {
            // Adds matching event ID tickets to basket 
            int eventID = getIntInput("Enter event ID to add to basket: ");
            LiveEvent event = eventManager.findEventByID(eventID);

            if (event == null) {
                System.out.println("Event not found.");
                return;
            }
            // Ensures there are enough tickets available
            if (event.getQuantityInStock() <= 0) {
                System.out.println("Sorry, this event is sold out.");
                return;
            }
            // Finds the number of tickets the user has in their basket
            Customer customer = (Customer) currentUser;
            ShoppingBasket basket = customer.getBasket();
            List < LiveEvent > basketItems = basket.getItems();
            int ticketsInBasket = 0;
            for (LiveEvent item: basketItems) {
                if (item.getEventID() == event.getEventID()) {
                    ticketsInBasket++;
                }
            }
            // Ensures that multiple tickets can be bought as long as they are available and not in the basket
            int availableTickets = event.getQuantityInStock() - ticketsInBasket;
            if (availableTickets <= 0) {
                System.out.println("Sorry, all tickets have been sold out.");
                return;
            }

            System.out.println("Available tickets for " + event.getEventName() + ": " + availableTickets);
            int quantity = getIntInput("Enter number of tickets to purchase: ");
            // Ensures tickets being bought is between 1 and the max available tickets
            while (quantity < 1 || quantity > availableTickets) {
                System.out.println("Invalid quantity. There are only " + availableTickets + " tickets available.");
                quantity = getIntInput("Enter number of tickets to purchase: ");
            }
            // Adds all items to the basket
            for (int i = 0; i < quantity; i++) {
                basket.addItem(event);
            }

            System.out.println(quantity + " ticket(s) added to basket for: " + event.getEventName());
        } catch (Exception error) {
            System.out.println("Error adding ticket to basket: " + error.getMessage());
        }
    }

    private void viewBasket() {
        // Displays the user's shopping basket
        Customer customer = (Customer) currentUser;
        ShoppingBasket basket = customer.getBasket();

        if (basket.isEmpty()) {
            System.out.println("Your basket is empty.");
            return;
        }
        // Prints each basket item and its price individually
        System.out.println("\nYour Shopping Basket:");
        List < LiveEvent > items = basket.getItems();
        for (int i = 0; i < items.size(); i++) {
            LiveEvent event = items.get(i);
            System.out.println((i + 1) + ". " + event.getEventName() + " - £" + event.getTicketPrice());
        }
        // Prints the total basket cost
        System.out.println("\nTotal: £" + basket.calculateTotal());
    }

    private void payForTickets() {
        try {
            // Pays for tickets inside the shopping basket
            Customer customer = (Customer) currentUser;
            ShoppingBasket basket = customer.getBasket();
            if (basket.isEmpty()) {
                System.out.println("Your basket is empty.");
                return;
            }
            double total = basket.calculateTotal();
            System.out.println("\nSelect payment method:");
            System.out.println("1. PayPal");
            System.out.println("2. Credit Card");
            int paymentChoice = getIntInput("Enter choice: ");
            // Ensures payment choice is selected
            while (paymentChoice != 1 && paymentChoice != 2) {
                paymentChoice = getIntInput("Please select option 1 or 2: ");
            }
            PaymentMethod paymentMethod = null;
            if (paymentChoice == 1) {
                // Enter email for PayPal choice
                String email = getStringInput("Enter PayPal email address: ");
               // Ensures email entered is valid 
                while (!isValidEmail(email)) {
                    email = getStringInput("Please enter a valid Paypal email address: ");
                }
                paymentMethod = new PayPal(email);
            } else if (paymentChoice == 2) {
                // Enter card number and security code for credit card choice
                String cardNumber = getStringInput("Enter 6-digit card number: ");
                // Ensures card number entered is the valid format
                while (cardNumber.length() != 6 || !isAllDigits(cardNumber)) {
                    cardNumber = getStringInput("Please enter a valid 6-digit card number: ");
                }
                String securityCode = getStringInput("Enter 3-digit security code: ");
                // Ensures security card entered is the valid format
                while (securityCode.length() != 3 || !isAllDigits(securityCode)) {
                    securityCode = getStringInput("Please enter a valid 3-digit security code: ");
                }
                paymentMethod = new CreditCard(cardNumber, securityCode);
            }
            // Deducts number of tickets from each event, provides receipt and clears the basket
            Receipt receipt = paymentMethod.processPayment(total, currentUser.getAddress());
            List < LiveEvent > items = basket.getItems();
            for (LiveEvent event: items) {
                int newQuantity = event.getQuantityInStock() - 1;
                eventManager.updateEventQuantity(event, newQuantity);
            }
            basket.clear();
            System.out.println("\nPayment Successful!");
            System.out.println("£" + receipt + ".");
        } catch (Exception error) {
            System.out.println("Error processing payment: " + error.getMessage());
        }
    }

    private boolean isAllDigits(String numStr) {
        // Ensures that every character entered in a string is a digit
        for (int i = 0; i < numStr.length(); i++) {
            if (numStr.charAt(i) < '0' || numStr.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidEmail(String email) {
        // Email regex to ensure an email matches the email format
        return email.matches("^[A-Za-z0-9+_-]+(\\.[A-Za-z0-9+_-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    }

    private void cancelBasket() {
    	// Cancels the user's baskets
        Customer customer = (Customer) currentUser;
        customer.getBasket().clear();
        System.out.println("Shopping basket cancelled.");
    }

    private void searchByEventID() {
        // Provides information for an event given an event ID
        try {
            int eventID = getIntInput("Enter event ID to search for: ");
            LiveEvent event = eventManager.findEventByID(eventID);
            if (event == null) {
                System.out.println("No event found with ID: " + eventID);
                return;
            }
            System.out.println("\nEvent found:");
            System.out.println("ID: " + event.getEventID() + ", Name: " + event.getEventName() +
                ", Type: " + event.getEventCategory() +
                ", Age: " + event.getAgeRestriction() +
                ", Available: " + event.getQuantityInStock() +
                ", Price: £" + event.getTicketPrice());
            if (event instanceof MusicEvent) {
                System.out.println(" Music Type: " + ((MusicEvent) event).getMusicType() +
                    ", Performers: " + ((MusicEvent) event).getNumberOfPerformers());
            } else if (event instanceof PerformanceEvent) {
                System.out.println(" Performance Type: " + ((PerformanceEvent) event).getPerformanceType() +
                    ", Language: " + ((PerformanceEvent) event).getLanguage());
            }
        } catch (Exception error) {
            System.out.println("Error searching for event: " + error.getMessage());
        }
    }

    private void searchByLanguage() {
        // Provides information for an event given a language
        try {
            String language = getStringInput("Enter language to search for: ");
            List < LiveEvent > events = eventManager.findPerformancesByLanguage(language);

            if (events.isEmpty()) {
                System.out.println("No performances found in: " + language);
                return;
            }

            System.out.println("\nPerformances in " + language + ":");
            for (LiveEvent event: events) {
                System.out.println("ID: " + event.getEventID() + ", Name: " + event.getEventName() +
                    ", Type: " + ((PerformanceEvent) event).getPerformanceType() +
                    ", Age: " + event.getAgeRestriction() +
                    ", Available: " + event.getQuantityInStock() +
                    ", Price: £" + event.getTicketPrice());
            }
        } catch (Exception error) {
            System.out.println("Error searching performances: " + error.getMessage());
        }
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException error) {
                System.out.println("Invalid input.");
            }
        }
    }

    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                return value;
            } catch (NumberFormatException error) {
                System.out.println("Invalid input.");
            }
        }
    }
}