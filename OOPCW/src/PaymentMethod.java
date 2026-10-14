// payment method interface for all payment methods
public interface PaymentMethod {
	Receipt processPayment(double amount, Address fullAddress);
}
