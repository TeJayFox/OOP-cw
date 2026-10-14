// Shopping basket class for holding item details
import java.util.ArrayList;
import java.util.List;
public class ShoppingBasket {
	private List<LiveEvent> items;
	
	public ShoppingBasket() {
		items = new ArrayList<>();
	}
	
	public void addItem(LiveEvent event) {
		items.add(event);
	}
	
	public void clear() {
		items.clear();
	}
	
	public List<LiveEvent> getItems() {
		return items;
	}
	
	public boolean isEmpty() {
		return items.isEmpty();
	}
	
	public double calculateTotal() {
		double total = 0.0;
		for (LiveEvent event : items) {
			total += event.getTicketPrice();
		}
		return total;
	}
}
