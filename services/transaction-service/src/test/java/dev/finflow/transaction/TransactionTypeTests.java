package dev.finflow.transaction;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TransactionTypeTests {
    
    @Test 
    public void recognizesPayment() {
        // Test that the TransactionType enum has the expected value
        assertSame(TransactionType.PAYMENT, TransactionType.valueOf("PAYMENT"));
    }

    @Test 
    public void rejectsUnsupportedType() {
        // Test that the TransactionType enum does not have an unexpected value
        assertThrows(IllegalArgumentException.class, () -> {
            TransactionType.valueOf("REFUND");
        });
    }   
}
