// PayPal class for paypal payment details
import java.text.SimpleDateFormat;
import java.util.Date;
public class PayPal implements PaymentMethod {
	private String email;
	
	public PayPal(String email) {
		this.email = email;
	}
	
	@Override
	public Receipt processPayment(double amount, Address fullAddress) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String today = formatter.format(new Date());
		String paymentDetails = "paid via PayPal using " + email;
		return new Receipt(amount, paymentDetails, today, fullAddress);
	}
}
