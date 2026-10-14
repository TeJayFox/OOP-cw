// DJ set class for music events
public class DJSet extends MusicEvent {
	public DJSet(int eventID, String eventName, AgeRestrictionCategory restriction,
			int quantityInStock, double performanceFee, double ticketPrice,
			int numberOfBands) {
		super(eventID, eventName, restriction, quantityInStock, performanceFee, ticketPrice, numberOfBands);
	}
	
	@Override
	public String getMusicType() {
		return "DJ Set";
	}
}