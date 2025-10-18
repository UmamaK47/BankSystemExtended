public class SavingsAccount extends BankAccount {
    private final double interestRate;
    private final double minBalance;

    public SavingsAccount(String accountNumber, double balance, String customerId, double interestRate, double minBalance) {
        super(accountNumber, balance, customerId);
        this.interestRate = interestRate;
        this.minBalance = minBalance;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive.");
        if (balance - amount < minBalance) {
            addFailedWithdrawRecord(amount, "Would violate minimum balance");
            throw new InsufficientFundsException("Cannot withdraw: minimum balance requirement would be violated.");
        }
        balance -= amount;
        addSuccessfulWithdrawRecord(amount);
    }

    @Override
    public void applyInterest() {
        double interest = balance * interestRate / 100.0;
        balance += interest;
        transactions.add(new Transactions(
                TransactionType.DEPOSIT,
                interest,
                java.time.LocalDateTime.now(),
                TransactionStatus.SUCCESS,
                accountNumber,
                "Interest added"
        ));
    }
}
