package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.abs;

/**
 * Customer class
 */
public class Customer
{
    private String name;
    private final List<Account> accounts;

    public Customer(String name)
    {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Open an account, of supplied Account type.
     * The internal account id is auto-generated.
     */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public String name() {   return name;    }
    public int numAccounts() { return accounts.size(); }

    /**
     * Transfer affects from/to Account's underlying transactions which can potentially be modified concurrently.
     * In order prevent race conditions / potential deadlock, acquisition of the lock on both accounts are necessary.
     * This is intentionally implemented as a blocking call.
     * Since this method assumes caller has access to the Account objects,
     * exposing available account list through id -> Account map might become appropriate in the future.
     * which will transform the parameters types to Account ids instead of Account references.
     *
     * @param from : Account to withdraw money from.
     * @param to : Account to deposit money into.
     * @param amount : Amount to transfer.
     */
    public boolean transfer(Account from, Account to, double amount)
    {
        Account firstAcquire = from;
        Account secondAcquire = to;

        // Identify which account has lower id.
        if(to.id() < from.id())
        {
            firstAcquire = to;
            secondAcquire = from;
        }

        // Attempt to acquire locks on accounts in order of ascending id,
        // this is a basic way to prevent potential deadlock among accounts.
        synchronized (firstAcquire)
        {
            synchronized (secondAcquire)
            {
                from.withdraw(amount);
                to.deposit(amount);
            }
        }

        // At this point, transfer is complete.
        return true;
    }

    public double totalInterestEarned()
    {   return accounts.stream().mapToDouble(d -> d.interestEarned()).sum();    }

    public String getStatement()
    {
        StringBuilder statement = new StringBuilder(String.format("Statement for %s\n", name));

        double total = 0.0;
        for (Account account : accounts)
        {
            statement.append(String.format("\n%s\n", statementForAccount(account)));
            total += account.sumTransactions();
        }
        statement.append("\nTotal In All Accounts " + toDollars(total));
        return statement.toString();
    }

    public String statementForAccount(Account account)
    {
        StringBuilder statement = new StringBuilder(account.type().toString() + "\n");

        // Now total up all the transactions
        double total = 0.0;
        for (Transaction t : account.transactions())
        {
            statement.append(String.format("  %s %s\n", t.type(), toDollars(t.amount())));
            total += t.amount();
        }
        statement.append("Total " + toDollars(total));
        return statement.toString();
    }

    private static String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }
}
