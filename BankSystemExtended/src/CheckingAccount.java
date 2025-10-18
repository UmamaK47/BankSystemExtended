import java.time.LocalDateTime;

public class CheckingAccount extends BankAccount {
    private final double overdraftLimit;

    public CheckingAccount(String accountNumber, double balance, String customerId, double overdraftLimit) {
        super(accountNumber, balance, customerId);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive.");

        double allowed = balance + overdraftLimit;
        if (amount > allowed) {
            addFailedWithdrawRecord(amount, "Exceeds overdraft limit");
            throw new InsufficientFundsException("Withdrawal exceeds overdraft limit.");
        }

        balance -= amount;
        addSuccessfulWithdrawRecord(amount);
    }

    @Override
    public void applyInterest() {
        // No interest for checking accounts
    }
}
