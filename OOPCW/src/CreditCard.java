// credit card class to hold user's credit card details
import java.text.SimpleDateFormat;
import java.util.Date;
public class CreditCard implements PaymentMethod {
	private String cardNumber;
	private String securityCode;
	
	public CreditCard(String cardNumber, String securityCode) {
		this.cardNumber = cardNumber;
		this.securityCode = securityCode;
	}
	
	@Override
	public Receipt processPayment(double amount, Address fullAddress) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String today = formatter.format(new Date());
		String paymentDetails = "paid by Credit Card using " + cardNumber;
		return new Receipt(amount, paymentDetails, today, fullAddress);
	}
}
