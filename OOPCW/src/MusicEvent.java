// Music event abstract class for all music events
public abstract class MusicEvent extends LiveEvent {
	private int numberOfPerformers;
	
	public MusicEvent(int eventID, String eventName, AgeRestrictionCategory restriction,
			int quantityInStock, double performanceFee, double ticketPrice,
			int numberOfPerformers) {
		super(eventID, eventName, restriction, quantityInStock, performanceFee, ticketPrice, LiveEventCategory.MUSIC);
		this.numberOfPerformers = numberOfPerformers;
	}
	
	public int getNumberOfPerformers() {
		return numberOfPerformers;
	}
	@Override
	public String toString() {
		return getEventID() + ", Music, " + getMusicType() + ", " + getEventName() + ", " +
				getAgeRestriction() + ", " + getQuantityInStock() + ", " + getPerformanceFee() + ", " +
				getTicketPrice() + ", " + getNumberOfPerformers();
	}
	
	public abstract String getMusicType();
}
