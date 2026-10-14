// Theatre class for performance events
public class Theatre extends PerformanceEvent {
	public Theatre(int eventID, String eventName, AgeRestrictionCategory restriction,
			int quantityInStock, double performanceFee, double ticketPrice,
			String language) {
		super(eventID, eventName, restriction, quantityInStock, performanceFee, ticketPrice, language);
	}
	
	@Override
	public String getPerformanceType() {
		return "Theatre";
	}
}
