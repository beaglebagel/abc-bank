package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest
{
    private static final double DOUBLE_DELTA = 1e-15;

    @Test // Test customer statement generation
    public void testApp()
    {
        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  Deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  Deposit $4,000.00\n" +
                "  Withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount()
    {
        Customer oscar = new Customer("Oscar").openAccount(new Account(AccountType.SAVINGS));
        assertEquals(1, oscar.numAccounts());
        assertEquals("Oscar", oscar.name());
    }

    @Test
    public void testTwoAccount()
    {
        Customer oscar = new Customer("Oscar").openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        assertEquals(2, oscar.numAccounts());
        assertEquals("Oscar", oscar.name());
    }

    @Test
    public void testTotalInterestEarned()
    {
        Customer oscar = new Customer("Oscar");
        Account checking = new Account(AccountType.CHECKING);
        checking.deposit(1000);
        Account checking2 = new Account(AccountType.CHECKING);
        checking2.deposit(2000);
        oscar.openAccount(checking).openAccount(checking2);
        assertEquals( (1000 * 0.001 + 2000 * 0.001) / 365, oscar.totalInterestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testTransfer()
    {
        Customer customer = new Customer("Customer");
        Account from = new Account(AccountType.CHECKING);
        from.deposit(1000);
        Account to = new Account(AccountType.CHECKING);
        to.deposit(1000);

        customer.transfer(from, to, 1000);
        assertEquals(0, from.sumTransactions(), DOUBLE_DELTA);
        assertEquals(2000, to.sumTransactions(), DOUBLE_DELTA);
    }

}
