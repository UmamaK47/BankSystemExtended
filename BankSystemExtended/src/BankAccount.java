import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class BankAccount {
    protected final String accountNumber;
    protected double balance;
    protected final String customerId;
    protected final List<Transactions> transactions;

    public BankAccount(String accountNumber, double balance, String customerId) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customerId = customerId;
        this.transactions = new ArrayList<>();
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public String getCustomerId() { return customerId; }
    public List<Transactions> getTransactions() { return transactions; }

    public void deposit(double amount) {
        if (amount <= 0) return;
        balance += amount;
        transactions.add(new Transactions(
                TransactionType.DEPOSIT,
                amount,
                LocalDateTime.now(),
                TransactionStatus.SUCCESS,
                accountNumber,
                "Deposit"
        ));
    }

    /**
     * Withdraw amount according to account rules.
     * Implementations must add appropriate Transactions and throw InsufficientFundsException when needed.
     */
    public abstract void withdraw(double amount) throws InsufficientFundsException;

    protected void addFailedWithdrawRecord(double amount, String reason) {
        transactions.add(new Transactions(
                TransactionType.WITHDRAWAL,
                amount,
                LocalDateTime.now(),
                TransactionStatus.FAILED_INSUFFICIENT_FUNDS,
                accountNumber,
                reason
        ));
    }

    protected void addSuccessfulWithdrawRecord(double amount) {
        transactions.add(new Transactions(
                TransactionType.WITHDRAWAL,
                amount,
                LocalDateTime.now(),
                TransactionStatus.SUCCESS,
                accountNumber,
                "Withdrawal"
        ));
    }

    public abstract void applyInterest();
}
