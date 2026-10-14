// Event manager for parsing through stock details
import java.io.*;
import java.util.*;
public class EventManager {
	private List<LiveEvent> eventStock;
	private String stockFilePath;
	
	public EventManager(String stockFilePath) {
		this.eventStock = new ArrayList<>();
		this.stockFilePath = stockFilePath;
		loadEventsFromFile();
	}
	
	private void loadEventsFromFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(stockFilePath))) {
			String line;
			// reads from each line of Stock.txt and sets a live event object with their fields
			while ((line = reader.readLine()) != null) {
				String[] elements = line.split(", ");
				if (elements.length >= 8) {
					int eventID = Integer.parseInt(elements[0]);
					String category = elements[1];
					String type = elements[2];
					String name = elements[3];
					AgeRestrictionCategory restriction = "Adults".equals(elements[4]) ?
							AgeRestrictionCategory.ADULTS : AgeRestrictionCategory.ALL;
					int quantity = Integer.parseInt(elements[5]);
					double fee = Double.parseDouble(elements[6]);
					double price = Double.parseDouble(elements[7]);
					String additionalInfo = elements[8];
					
					LiveEvent event = null;
					
					if ("Music".equals(category)) {
						int performerCount = Integer.parseInt(additionalInfo);
						if ("Live Concert".equals(type)) {
							event = new LiveConcert(eventID, name, restriction, quantity, fee, price, performerCount);
						} else if ("DJ Set".equals(type)) {
							event = new DJSet(eventID, name, restriction, quantity, fee, price, performerCount);
						}
					} else if ("Performance".equals(category)) {
						String language = additionalInfo;
						if("Theatre".equals(type)) {
							event = new Theatre(eventID, name, restriction, quantity, fee, price, language);
						} else if ("Stand-up Comedy".equals(type)) {
							event = new StandupComedy(eventID, name, restriction, quantity, fee, price, language);
						} else if ("Magic".equals(type)) {
							event = new Magic(eventID, name, restriction, quantity, fee, price, language);
						}
					}
					
					if (event != null) {
						eventStock.add(event);
					}
				}
			}
		} catch (IOException error) {
			System.out.println("Error loading events: " + error.getMessage());
		}
	}
	
	public void saveEventsToFile() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(stockFilePath))) {
			// Writes new event data to Stock.txt
			for (LiveEvent event : eventStock) {
				writer.write(event.toString());
				writer.newLine();
			}
		} catch (IOException error) {
			System.out.println("Error saving events: " + error.getMessage());
		}
	}
	
	public List<LiveEvent> getAllEventsSorted() {
		// Sorts all events by ticket price
		List<LiveEvent> sortedEvents = new ArrayList<>(eventStock);
		Collections.sort(sortedEvents, Comparator.comparingDouble(LiveEvent::getTicketPrice));
		return sortedEvents;
	}
	
	public boolean addEvent(LiveEvent event) {
		// Only saves if event ID is unique
		for (LiveEvent error : eventStock) {
			if(error.getEventID() == event.getEventID()) {
				return false;
			}
		}
		
		eventStock.add(event);
		saveEventsToFile();
		return true;
	}
	
	public LiveEvent findEventByID(int eventID) {
		// Returns LiveEvent object if entered event ID matches with an event
		for (LiveEvent event : eventStock) {
			if (event.getEventID() == eventID) {
				return event;
			}
		}
		return null;
	}
	
	public List<LiveEvent> findPerformancesByLanguage(String language) {
		// Returns list of all live events with a matching performance language
		List<LiveEvent> result = new ArrayList<>();
		for (LiveEvent event : eventStock) {
			if (event instanceof PerformanceEvent &&
					((PerformanceEvent) event).getLanguage().equalsIgnoreCase(language)) {
				result.add(event);
			}
		}
		return result;
	}
	
	public void updateEventQuantity(LiveEvent event, int newQuantity) {
		// Sets new ticket quantity in Stock.txt when tickets are bought
		event.setQuantityInStock(newQuantity);
		saveEventsToFile();
	}
}