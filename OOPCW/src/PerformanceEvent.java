// Performance event abstract event for all performance events
public abstract class PerformanceEvent extends LiveEvent{
	private String language;
	
	public PerformanceEvent(int eventID, String eventName, AgeRestrictionCategory restriction,
			int quantityInStock, double performanceFee, double ticketPrice,
			String language) {
		super(eventID, eventName, restriction, quantityInStock, performanceFee, ticketPrice, LiveEventCategory.PERFORMANCE);
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}
	
	@Override
	public String toString() {
		return getEventID() + ", Performance, " + getPerformanceType() + ", " + getEventName() + ", " +
				getAgeRestriction() + ", " + getQuantityInStock() + ", " + getPerformanceFee() + ", " +
				getTicketPrice() + ", " + getLanguage();
	}
	
	public abstract String getPerformanceType();
}
