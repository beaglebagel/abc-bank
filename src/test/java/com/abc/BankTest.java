package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest
{
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary()
    {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        bank.addCustomer(john);
        john.openAccount(new Account(AccountType.CHECKING));
        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
        john.openAccount(new Account(AccountType.CHECKING));
        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }

    @Test
    public void testInterestSummary()
    {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Account checking = new Account(AccountType.CHECKING);
        john.openAccount(checking);
        bank.addCustomer(john);
        assertEquals("Total Interest Paid on All Accounts $0.00", bank.interestSummary());
    }

    @Test
    public void testTotalInterestPaid()
    {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(john);
        assertEquals(0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
