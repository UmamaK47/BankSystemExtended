import java.util.*; //  added import for Scanner and collections

public class Bank {
    private Map<String, Customer> customers = new HashMap<>();

    // Add new customer
    public void addCustomer(Customer customer) {
        customers.put(customer.getCustomerID(), customer);
    }

    // Add new account for a customer
    public void addAccount(String customerID, BankAccount account) {
        Customer c = customers.get(customerID);
        if (c != null) {
            c.addAccount(account);
        } else {
            System.out.println("Customer not found.");
        }
    }

    // Admin menu
    public void showAdminMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View All Customers");
            System.out.println("2. View All Accounts");
            System.out.println("3. View All Transactions");
            System.out.println("4. Exit to Main Menu");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    displayAllCustomers();
                    break;
                case 2:
                    displayAllAccounts();
                    break;
                case 3:
                    displayAllTransactions();
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 4);
    }

    //  Admin feature: Display all customers
    public void displayAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        System.out.println("\n--- Customer List ---");
        for (Customer c : customers.values()) {
            System.out.println("Customer ID: " + c.getCustomerID() + ", Name: " + c.getName());
        }
    }

    //  Admin feature: Display all accounts
    public void displayAllAccounts() {
        System.out.println("\n--- All Accounts ---");
        for (Customer c : customers.values()) {
            for (BankAccount acc : c.getAccounts()) {
                System.out.println("Customer: " + c.getName() +
                        " | Account: " + acc.getAccountNumber() +
                        " | Balance: " + acc.getBalance());
            }
        }
    }

    //  Admin feature: Display all transactions
    public void displayAllTransactions() {
        System.out.println("\n--- All Transactions ---");
        for (Customer c : customers.values()) {
            for (BankAccount acc : c.getAccounts()) {
                System.out.println("\nTransactions for " + c.getName() +
                        " (" + acc.getAccountNumber() + "):");
                for (Transactions t : acc.getTransactions()) {
                    System.out.println(t);
                }
            }
        }
    }
}
