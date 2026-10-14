// Live event abstract class for all events
public abstract class LiveEvent {
	private int eventID;
	private String eventName;
	private LiveEventCategory LiveEventCategory;
	private double performanceFee;
	private int quantityInStock;
	private AgeRestrictionCategory restriction;
	private double ticketPrice;
	public AgeRestrictionCategory getAgeRestriction() {
		return restriction;
	}
	public LiveEventCategory getEventCategory() {
		return LiveEventCategory;
	}
	public int getEventID() {
		return eventID;
	}
	public String getEventName() {
		return eventName;
	}
	public double getPerformanceFee() {
		return performanceFee;
	}
	public int getQuantityInStock() {
		return quantityInStock;
	}
	public double getTicketPrice() {
		return ticketPrice;
	}
	public void setQuantityInStock(int newQuantityInStock) {
		this.quantityInStock = newQuantityInStock;
	}
	public abstract String toString();
	
	LiveEvent(int eventID, String eventName, AgeRestrictionCategory restriction,
			int quantityInStock, double performanceFee, double ticketPrice,
			LiveEventCategory LiveEventCategory) {
		this.eventID = eventID;
		this.eventName = eventName;
		this.restriction = restriction;
		this.quantityInStock = quantityInStock;
		this.performanceFee = performanceFee;
		this.ticketPrice = ticketPrice;
		this.LiveEventCategory = LiveEventCategory;
	}
}
