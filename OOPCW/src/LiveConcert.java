// Live concert class for music events
public class LiveConcert extends MusicEvent {
	public LiveConcert(int eventID, String eventName, AgeRestrictionCategory restriction,
			int quantityInStock, double performanceFee, double ticketPrice,
			int numberOfBands) {
		super(eventID, eventName, restriction, quantityInStock, performanceFee, ticketPrice, numberOfBands);
	}
	
	@Override
	public String getMusicType() {
		return "Live Concert";
	}
}
