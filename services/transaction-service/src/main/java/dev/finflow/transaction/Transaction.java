package dev.finflow.transaction;
import java.math.BigDecimal;
public record Transaction (
    String accountId,
    TransactionType type,
    BigDecimal amount
) {
    public Transaction {
        if (accountId == null || accountId.isEmpty()) {
            throw new IllegalArgumentException("Account ID cannot be null or empty");
        }
        if (type == null) {
            throw new IllegalArgumentException("Transaction type cannot be null");
        }
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }
}
