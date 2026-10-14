// Receipt class holding all payment, date and address details
public class Receipt {
	private double amount;
	private String paymentDetails;
	private String date;
	private Address billingAddress;
	
	public Receipt(double amount, String paymentDetails, String date, Address billingAddress) {
		this.amount = amount;
		this.paymentDetails = paymentDetails;
		this.date = date;
		this.billingAddress = billingAddress;
	}
	
	@Override
	public String toString() {
		return amount + " " + paymentDetails + " on " + date + ", and the billing address is " + billingAddress;
	}
}
