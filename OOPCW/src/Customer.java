// customer class to give user customer permissions
public class Customer extends User {
	private ShoppingBasket basket;
	
	public Customer(int userID, String username, String name, Address address) {
		super(userID, username, name, address);
		this.basket = new ShoppingBasket();
	}
	
	public ShoppingBasket getBasket() {
		return basket;
	}
	
	@Override
	public String getRole() {
		return "customer";
	}
}