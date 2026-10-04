package dev.finflow.transaction;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
public class TransactionServiceTests {
    @Test 
    void testValidTransactionCreation() {
        String accountId = "12345";
        BigDecimal amount = new BigDecimal("250.50");
        TransactionType transactionType = TransactionType.PAYMENT;

        TransactionService transactionService = new TransactionService();
        Transaction transaction = transactionService.create(accountId, transactionType, amount);

        assertEquals(accountId, transaction.accountId());
        assertEquals(transactionType, transaction.type());
        assertEquals(amount, transaction.amount());
    }

    @Test 
    void testInvalidAmountTransactionCreation() {
        String accountId = "12345";
        BigDecimal amount = new BigDecimal("-100.00");
        TransactionType transactionType = TransactionType.PAYMENT;

        TransactionService transactionService = new TransactionService();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.create(accountId, transactionType, amount);
        });
        assertEquals("Amount must be greater than zero", exception.getMessage());
    }
}
