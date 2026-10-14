// Main class for retrieving user accounts and stock data and beginning the CLI
public class Main {
    public static void main(String[] args) {
        String userAccountsPath = "UserAccounts.txt";
        String stockPath = "Stock.txt";
        EventManager eventManager = new EventManager(stockPath);
        UserManager userManager = new UserManager(userAccountsPath);
        CLI cli = new CLI(eventManager, userManager);
        cli.start();
    }
}