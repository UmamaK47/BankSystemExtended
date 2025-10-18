import java.time.LocalDateTime;
import java.util.UUID;

public class Transactions {
    private final String transactionId;
    private final TransactionType type;
    private final double amount;
    private final LocalDateTime timestamp;
    private final TransactionStatus status;
    private final String accountNumber;
    private final String note;

    public Transactions(TransactionType type, double amount, LocalDateTime timestamp,
                        TransactionStatus status, String accountNumber, String note) {
        this.transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 8);
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.status = status;
        this.accountNumber = accountNumber;
        this.note = note;
    }

    public String getTransactionId() { return transactionId; }
    public TransactionType getType() { return type; }
    public double getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public TransactionStatus getStatus() { return status; }
    public String getAccountNumber() { return accountNumber; }
    public String getNote() { return note; }

    @Override
    public String toString() {
        return timestamp + " | " + transactionId + " | " + accountNumber + " | " + type
                + " | $" + amount + " | " + status + " | " + note;
    }
}
