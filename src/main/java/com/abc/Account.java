package com.abc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Account class
 */
public class Account
{
    // Account Id generator.
    public static final AtomicInteger idCounter = new AtomicInteger(0);
    private final int id;   // account integer id
    private final AccountType type; // account type
    private final List<Transaction> transactions;   // account types

    public Account(AccountType type)
    {
        // Each new account gets unique incremental id.
        this(idCounter.getAndIncrement(), type);
    }

    private Account(int id, AccountType type)
    {
        this.id = id;
        this.type = type;
        this.transactions = new ArrayList<Transaction>();
    }

    public int id() { return id; }
    public AccountType type() { return type; }

    // Return immutable Transactions list.
    public List<Transaction> transactions() { return Collections.unmodifiableList(this.transactions); }

    /**
     * Deposit new amount. Creates deposit transaction, Intentionally implemented as blocking call.
     * @param amount: amount to deposit.
     */
    public void deposit(double amount)
    {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit Amount must be greater than zero");
        }
        else {
            synchronized (this) {
                transactions.add(new Transaction(amount, Transaction.TransactionType.DEPOSIT));
            }
        }
    }

    /**
     * Withdraw new amount. Creates withdraw transaction, Intentionally implemented as blocking call.
     * @param amount: amount to withdraw.
     */
    public void withdraw(double amount)
    {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal Amount must be greater than zero");
        }
        else {
            synchronized (this) {
                transactions.add(new Transaction(-amount, Transaction.TransactionType.WITHDRAWAL));
            }
        }
    }

    public double interestEarned()
    {
        double amount = sumTransactions();
        return this.type.calculate(amount);
    }

    public double sumTransactions()
    {
        return transactions.stream().mapToDouble(t -> t.amount()).sum();
    }

}
