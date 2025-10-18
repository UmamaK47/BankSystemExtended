import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String customerID;
    private String name;
    private String pin;
    private List<BankAccount> accounts;

    public Customer(String customerID, String name, String pin) {
        this.customerID = customerID;
        this.name = name;
        this.pin = pin;
        this.accounts = new ArrayList<>();
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getPin() {
        return pin;
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }
}
