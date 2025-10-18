import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<String, Customer> customers;
    private final Map<String, BankAccount> accounts;

    public Bank() {
        this.customers = new HashMap<>();
        this.accounts = new HashMap<>();
    }
    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

    public void addCustomer(Customer c) {
        customers.put(c.getId(), c);
        // register existing accounts in accounts map
        for (BankAccount a : c.getAccounts()) accounts.put(a.getAccountNumber(), a);
    }

    public Customer getCustomerById(String id) {
        return customers.get(id);
    }

    public BankAccount getAccountByNumber(String accNo) {
        return accounts.get(accNo);
    }

    public void registerAccount(BankAccount account, Customer owner) {
        owner.addAccount(account);
        accounts.put(account.getAccountNumber(), account);
    }

    // Transfer within same customer
    public void transferWithinCustomer(String customerId, String fromAccNo, String toAccNo, double amount)
            throws InvalidAccountException, InsufficientFundsException {
        Customer c = customers.get(customerId);
        if (c == null) throw new InvalidAccountException("Customer not found.");

        BankAccount from = c.findAccount(fromAccNo);
        BankAccount to = c.findAccount(toAccNo);
        if (from == null || to == null) throw new InvalidAccountException("One of the accounts not found for this customer.");

        // perform withdrawal (may throw InsufficientFundsException)
        from.withdraw(amount);
        // deposit into destination
        to.deposit(amount);

        // record transfer transactions explicitly (optional; withdrawal/deposit already record)
        from.getTransactions().add(new Transactions(TransactionType.TRANSFER, amount, LocalDateTime.now(), TransactionStatus.SUCCESS, from.getAccountNumber(), "Transfer to " + to.getAccountNumber()));
        to.getTransactions().add(new Transactions(TransactionType.TRANSFER, amount, LocalDateTime.now(), TransactionStatus.SUCCESS, to.getAccountNumber(), "Transfer from " + from.getAccountNumber()));
    }

    // Cross-customer transfer
    public void transferToOtherCustomer(String fromCustomerId, String fromAccNo, String toCustomerId, String toAccNo, double amount)
            throws InvalidAccountException, InsufficientFundsException {
        Customer fromC = customers.get(fromCustomerId);
        Customer toC = customers.get(toCustomerId);
        if (fromC == null) throw new InvalidAccountException("Sender customer not found.");
        if (toC == null) throw new InvalidAccountException("Receiver customer not found.");

        BankAccount from = fromC.findAccount(fromAccNo);
        BankAccount to = toC.findAccount(toAccNo);
        if (from == null || to == null) throw new InvalidAccountException("Source or destination account not found.");

        from.withdraw(amount);
        to.deposit(amount);

        from.getTransactions().add(new Transactions(TransactionType.TRANSFER, amount, LocalDateTime.now(), TransactionStatus.SUCCESS, from.getAccountNumber(), "Transfer to " + to.getAccountNumber()));
        to.getTransactions().add(new Transactions(TransactionType.TRANSFER, amount, LocalDateTime.now(), TransactionStatus.SUCCESS, to.getAccountNumber(), "Transfer from " + from.getAccountNumber()));
    }

    // Admin functions
    public void displayAllCustomers() {
        System.out.println("\n--- All Customers ---");
        Collection<Customer> col = customers.values();
        if (col.isEmpty()) {
            System.out.println("No customers registered.");
            return;
        }
        for (Customer c : col) {
            System.out.print("ID: " + c.getId() + " | Name: " + c.getName() + " | Accounts: ");
            for (BankAccount a : c.getAccounts()) System.out.print(a.getAccountNumber() + " ");
            System.out.println("| Blocked: " + c.isBlocked());
        }
    }

    public void displayAllAccounts() {
        System.out.println("\n--- All Accounts ---");
        if (accounts.isEmpty()) {
            System.out.println("No accounts exist.");
            return;
        }
        for (BankAccount a : accounts.values()) {
            String type = a.getClass().getSimpleName();
            System.out.println("Type: " + type + " | Acc#: " + a.getAccountNumber() + " | Owner: " + a.getCustomerId() + " | Balance: $" + a.getBalance());
        }
    }

    public void displayAllTransactions() {
        System.out.println("\n--- All Transactions ---");
        for (BankAccount a : accounts.values()) {
            System.out.println("\nTransactions for account " + a.getAccountNumber() + ":");
            for (Transactions t : a.getTransactions()) System.out.println(t);
        }
    }

    public void createAccountForCustomer(String customerId, BankAccount newAccount) {
        Customer c = customers.get(customerId);
        if (c == null) {
            System.out.println("Customer not found.");
            return;
        }
        registerAccount(newAccount, c);
        System.out.println("Account " + newAccount.getAccountNumber() + " created for " + c.getName());
    }

    public boolean unblockCustomer(String customerId) {
        Customer c = customers.get(customerId);
        if (c == null) return false;
        c.unblock();
        return true;
    }

    // helpers for initial data and counts
    public int totalCustomers() { return customers.size(); }
    public int totalAccounts() { return accounts.size(); }
}
