public class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(String accountNumber, double balance, double interestRate) {
        super(accountNumber, balance);
        this.interestRate = interestRate;
    }

    @Override
    public void applyInterest() {
        double interest = balance * interestRate / 100;
        balance += interest;
        addTransaction(new Transactions(
                TransactionType.Deposit,
                interest,
                java.time.LocalDateTime.now(),
                TransactionStatus.SUCCESS,
                "Interest added"
        ));
    }
}
