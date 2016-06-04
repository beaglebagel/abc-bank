package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit Test for class Account.
 */
public class AccountTest
{
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testAccount()
    {
        Account account = new Account(AccountType.CHECKING);
        assertEquals(AccountType.CHECKING, account.type());
        assertEquals(0, account.transactions().size());
    }

    @Test
    public void testSumTransactions()
    {
        Account account = new Account(AccountType.CHECKING);
        account.deposit(100);
        assertEquals(100, account.sumTransactions(), DOUBLE_DELTA);
        account.deposit(100);
        assertEquals(200, account.sumTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarned()
    {
        Account checking = new Account(AccountType.CHECKING);
        checking.deposit(1000);
        assertEquals(1000 * 0.001/365, checking.interestEarned(), DOUBLE_DELTA);

        Account savings = new Account(AccountType.SAVINGS);
        savings.deposit(2000);
        assertEquals( (1 + (2000 - 1000) * 0.002) / 365, savings.interestEarned(), DOUBLE_DELTA);

        Account maxiSavings = new Account(AccountType.MAXI_SAVINGS);
        maxiSavings.deposit(3000);
        assertEquals((20 + 50 + (3000 - 2000) * 0.1) / 365, maxiSavings.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testWithdrawal()
    {
        Account account = new Account(AccountType.CHECKING);
        account.withdraw(100);
        assertEquals(1, account.transactions().size());
        assertEquals(-100, account.sumTransactions(), DOUBLE_DELTA);
        account.withdraw(100);
        assertEquals(2, account.transactions().size());
        assertEquals(-200, account.sumTransactions(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawalException()
    {
        Account account = new Account(AccountType.CHECKING);
        account.withdraw(-100);
    }

    @Test
    public void testDeposit()
    {
        Account account = new Account(AccountType.CHECKING);
        account.deposit(100);
        assertEquals(1, account.transactions().size());
        assertEquals(100, account.sumTransactions(), DOUBLE_DELTA);
        account.deposit(100);
        assertEquals(2, account.transactions().size());
        assertEquals(200, account.sumTransactions(), DOUBLE_DELTA);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositException()
    {
        Account account = new Account(AccountType.CHECKING);
        account.deposit(-100);
    }

}
