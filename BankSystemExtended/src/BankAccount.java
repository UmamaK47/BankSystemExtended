import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class BankAccount {
    protected String accountNumber;
    protected double balance;
    protected List<Transactions> transactions;

    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        addTransaction(new Transactions(
                TransactionType.Deposit,
                amount,
                LocalDateTime.now(),
                TransactionStatus.SUCCESS,
                "Deposit successful"
        ));
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            addTransaction(new Transactions(
                    TransactionType.Withdraw,
                    amount,
                    LocalDateTime.now(),
                    TransactionStatus.FAILED_INSUFFICIENT_FUNDS,
                    "Not enough balance"
            ));
            System.out.println("Insufficient funds!");
        } else {
            balance -= amount;
            addTransaction(new Transactions(
                    TransactionType.Withdraw,
                    amount,
                    LocalDateTime.now(),
                    TransactionStatus.SUCCESS,
                    "Withdrawal successful"
            ));
        }
    }

    public void addTransaction(Transactions t) {
        transactions.add(t);
    }

    public List<Transactions> getTransactions() {
        return transactions;
    }

    public abstract void applyInterest();
}
