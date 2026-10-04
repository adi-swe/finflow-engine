package dev.finflow.transaction;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class TransactionTests {
    @Test
    void testTransactionCreation() {
        String accountId = "12345";
        TransactionType type = TransactionType.PAYMENT;
        BigDecimal amount = new BigDecimal("250.50");

        Transaction transaction = new Transaction(accountId, type, amount);

        assertEquals(accountId, transaction.accountId());
        assertSame(type, transaction.type());
        assertEquals(amount, transaction.amount());
    }

    @Test
    void testTransactionCreationWithInvalidAccountId() {
        String accountId = "";
        TransactionType type = TransactionType.PAYMENT;
        BigDecimal amount = new BigDecimal("250.50");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(accountId, type, amount);
        });
        assertEquals("Account ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testTransactionCreationWithInvalidType() {
        String accountId = "12345";
        TransactionType type = null;
        BigDecimal amount = new BigDecimal("250.50");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(accountId, type, amount);
        });
        assertEquals("Transaction type cannot be null", exception.getMessage());
    }

    @Test
    void testTransactionCreationWithInvalidAmount() {
        String accountId = "12345";
        TransactionType type = TransactionType.PAYMENT;
        BigDecimal amount = new BigDecimal("-100.00");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(accountId, type, amount);
        });
        assertEquals("Amount must be greater than zero", exception.getMessage());
    }

    @Test
    void avoidZeroAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Transaction("12345", TransactionType.PAYMENT, BigDecimal.ZERO);
        });
    }

    @Test
    void rejectsNullAmount() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Transaction("12345", TransactionType.PAYMENT, null));
    }

    @Test
    void rejectsNullAccountId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Transaction(
                        null,
                        TransactionType.PAYMENT,
                        new BigDecimal("250.50")));
    }
}
