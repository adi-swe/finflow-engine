package dev.finflow.transaction;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class TransactionService {
    public Transaction create(String accountId, TransactionType transactionType, BigDecimal amount) {
        return new Transaction(accountId, transactionType, amount);
    }
}
