package dev.finflow.transaction;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransactionService {
    private final Map<UUID, Transaction> transactionStore = new ConcurrentHashMap<>();

    public UUID create(String accountId, TransactionType transactionType, BigDecimal amount) {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction(accountId, transactionType, amount);
        transactionStore.put(transactionId, transaction);
        return transactionId;
    }

    public Optional<Transaction> findById(UUID transactionId) {
        if (transactionId == null) {
            throw new IllegalArgumentException("Transaction ID cannot be null");
        }
        return Optional.ofNullable(transactionStore.get(transactionId));
    }
}
