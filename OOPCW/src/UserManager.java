// User manager class for parsing through user account details
import java.io.*;
import java.util.*;
public class UserManager {
	private List<User> users;
	private String userFilePath;
	
	public UserManager(String userFilePath) {
		this.users = new ArrayList<>();
		this.userFilePath = userFilePath;
		loadUsersFromFile();
	}
	
	private void loadUsersFromFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(userFilePath))) {
			String line;
			// Reads from each line of UserAccounts.txt and sets a user object with their fields
			while ((line = reader.readLine()) != null) {
				String[] elements = line.split(", ");
				if (elements.length >= 7) {
					int userID = Integer.parseInt(elements[0]);
					String username = elements[1];
					String name = elements[2];
					int houseNumber = Integer.parseInt(elements[3]);
					String postcode = elements[4];
					String city = elements[5];
					String role = elements[6];
					
					Address address = new Address(houseNumber, postcode, city);
					User user;
					
					if ("admin".equals(role)) {
						user = new Admin(userID, username, name, address);
					} else {
						user = new Customer(userID, username, name, address);
					}
					
					users.add(user);
				}
			}
		} catch (IOException error) {
			System.out.println("Error loading users: " + error.getMessage());
		}
	}
	
	public List<User> getAllUsers() {
		// Displays all users as a list of objects
		return users;
	}
	
	public User findUserByUsername(String username) {
		// Returns a user if the user's username matches
		for (User user: users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}
}