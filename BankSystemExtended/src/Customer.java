import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer {
    private final String id;
    private final String name;
    private final String pin;
    private boolean blocked;
    private int failedAttempts;
    private final List<BankAccount> accounts;

    public Customer(String name, String pin) {
        this.id = "CUST-" + UUID.randomUUID().toString().substring(0,8);
        this.name = name;
        this.pin = pin;
        this.blocked = false;
        this.failedAttempts = 0;
        this.accounts = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPin() { return pin; }
    public boolean isBlocked() { return blocked; }
    public List<BankAccount> getAccounts() { return accounts; }

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    public BankAccount findAccount(String accountNumber) {
        for (BankAccount a : accounts) {
            if (a.getAccountNumber().equals(accountNumber)) return a;
        }
        return null;
    }

    /**
     * Attempt to authenticate with a PIN.
     * If account becomes blocked due to 3 failed attempts, AccountBlockedException is thrown.
     * Returns true if authentication succeeds.
     */
    public boolean authenticate(String inputPin) {
        if (blocked) throw new AccountBlockedException("Customer account is blocked. Contact admin.");
        if (this.pin.equals(inputPin)) {
            failedAttempts = 0;
            return true;
        } else {
            failedAttempts++;
            if (failedAttempts >= 3) {
                blocked = true;
                throw new AccountBlockedException("Account blocked due to 3 incorrect PIN attempts.");
            }
            return false;
        }
    }

    public void unblock() {
        blocked = false;
        failedAttempts = 0;
    }
}
