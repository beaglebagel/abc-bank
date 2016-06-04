package com.abc;

import java.time.LocalDateTime;

/**
 * Transaction class.
 */
public class Transaction
{
    /**
     * TransactionType Enum
     */
    public enum TransactionType
    {
        WITHDRAWAL("Withdrawal"),
        DEPOSIT("Deposit");

        private final String typeString;
        public String toString() { return this.typeString; }
        private TransactionType(String typeString) { this.typeString = typeString; }
    }

    private final double amount;
    private final TransactionType type;
    private final LocalDateTime dateTime;   // immutable date time class.

    public Transaction(double amount, TransactionType type)
    {
        this.amount = amount;
        this.type = type;
        this.dateTime = DateProvider.getInstance().now();
    }

    public double amount() { return this.amount; }
    public TransactionType type() { return this.type; }
    public LocalDateTime date() { return this.dateTime; }
}
