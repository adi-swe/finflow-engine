package dev.finflow.transaction;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;
import java.util.Optional;

public class TransactionServiceTests {
    @Test
    void testValidTransactionCreation() {
        String accountId = "12345";
        BigDecimal amount = new BigDecimal("250.50");
        TransactionType transactionType = TransactionType.PAYMENT;

        TransactionService transactionService = new TransactionService();
        UUID transactionId = transactionService.create(accountId, transactionType, amount);

        assertNotNull(transactionId);
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

    @Test
    void testFindById() {
        String accountId = "12345";
        BigDecimal amount = new BigDecimal("100.00");
        TransactionType transactionType = TransactionType.PAYMENT;

        TransactionService transactionService = new TransactionService();
        UUID transactionId = transactionService.create(accountId, transactionType, amount);

        assertNotNull(transactionId);
        Optional<Transaction> retrievedTransaction = transactionService.findById(transactionId);

        assertTrue(retrievedTransaction.isPresent());

        Transaction retrieved = retrievedTransaction.orElseThrow();
        assertEquals(accountId, retrieved.accountId());
        assertEquals(transactionType, retrieved.type());
        assertEquals(amount, retrieved.amount());
    }

    @Test
    void returnsEmptyForUnknownId() {
        TransactionService service = new TransactionService();

        assertTrue(service.findById(UUID.randomUUID()).isEmpty());
    }

    @Test
    void storesIdenticalTransactionsUnderDifferentIds() {
        TransactionService service = new TransactionService();
        BigDecimal amount = new BigDecimal("250.50");
        Transaction expected = new Transaction("12345", TransactionType.PAYMENT, amount);

        UUID firstId = service.create("12345", TransactionType.PAYMENT, amount);
        UUID secondId = service.create("12345", TransactionType.PAYMENT, amount);

        assertNotEquals(firstId, secondId);
        assertEquals(expected, service.findById(firstId).orElseThrow());
        assertEquals(expected, service.findById(secondId).orElseThrow());
    }

    @Test
    void keepsStoresSeparateBetweenServiceInstances() {
        TransactionService firstService = new TransactionService();
        TransactionService secondService = new TransactionService();
        UUID transactionId = firstService.create(
                "12345", TransactionType.PAYMENT, new BigDecimal("250.50"));

        assertTrue(firstService.findById(transactionId).isPresent());
        assertTrue(secondService.findById(transactionId).isEmpty());
    }

    @Test
    void rejectsNullLookupId() {
        TransactionService service = new TransactionService();

        assertThrows(IllegalArgumentException.class, () -> service.findById(null));
    }
}
