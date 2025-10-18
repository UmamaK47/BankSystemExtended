import java.time.LocalDateTime;

public class Transactions {
    private TransactionType type;
    private double amount;
    private LocalDateTime date;
    private TransactionStatus status;
    private String description;

    public Transactions(TransactionType type, double amount, LocalDateTime date,
                        TransactionStatus status, String description) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.description = description;
    }

    @Override
    public String toString() {
        return "[" + date + "] " + type + " $" + amount + " (" + status + ") - " + description;
    }
}
