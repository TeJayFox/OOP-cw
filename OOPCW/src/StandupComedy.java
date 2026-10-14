// Stand-up comedy class for performance events
public class StandupComedy extends PerformanceEvent {
	public StandupComedy(int eventID, String eventName, AgeRestrictionCategory restriction,
			int quantityInStock, double performanceFee, double ticketPrice,
			String language) {
		super(eventID, eventName, restriction, quantityInStock, performanceFee, ticketPrice, language);
	}
	
	@Override
	public String getPerformanceType() {
		return "Stand-up Comedy";
	}
}
