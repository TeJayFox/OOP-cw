// admin class to give a user admin permissions
public class Admin extends User {
	public Admin(int userID, String username, String name, Address address) {
		super(userID, username, name, address);
	}
	
	@Override
	public String getRole() {
		return "admin";
	}
}
