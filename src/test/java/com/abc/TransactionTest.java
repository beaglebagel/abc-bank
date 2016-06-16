package com.abc;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest
{
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testTransaction()
    {
        Transaction t = new Transaction(5, Transaction.TransactionType.DEPOSIT);
        assertTrue(t instanceof Transaction);
        assertEquals(5, t.amount(), DOUBLE_DELTA);
        assertEquals(Transaction.TransactionType.DEPOSIT, t.type());
        assertEquals("Deposit", t.type().toString());

        Transaction t2 = new Transaction(5, Transaction.TransactionType.WITHDRAWAL);
        assertTrue(t2 instanceof Transaction);
        assertEquals(5, t2.amount(), DOUBLE_DELTA);
        assertEquals(Transaction.TransactionType.WITHDRAWAL, t2.type());
        assertEquals("Withdrawal", t2.type().toString());

        // Skipping the transaction's datetime test for now as Java 8 requires fixed Clock instance
        // into Time api's constructor in order to properly test Time functionality,
        // This affects constructor design and am skipping now as it's minor for now.
    }

}
