package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit Test for claas AccountType.
 */
public class AccountTypeTest
{
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testAccountType()
    {
        AccountType checking = AccountType.CHECKING;
        assertEquals("Checking Account", checking.toString());
        AccountType savings = AccountType.SAVINGS;
        assertEquals("Savings Account", savings.toString());
        AccountType maxiSavings = AccountType.MAXI_SAVINGS;
        assertEquals("Maxi Savings Account", maxiSavings.toString());
    }

    @Test
    public void testCheckingAccountType()
    {
        AccountType checkingType = AccountType.CHECKING;
        assertEquals(0, checkingType.calculate(0), DOUBLE_DELTA);
        assertEquals(1000 * 0.001/365, checkingType.calculate(1000), DOUBLE_DELTA);
    }

    @Test
    public void testSavingsAccountType()
    {
        AccountType savingsType = AccountType.SAVINGS;
        assertEquals(0, savingsType.calculate(0), DOUBLE_DELTA);
        assertEquals( (1000 * 0.001) / 365, savingsType.calculate(1000), DOUBLE_DELTA);
        assertEquals( (1 + (2000 - 1000) * 0.002) / 365, savingsType.calculate(2000), DOUBLE_DELTA);
    }

    @Test
    public void testMaxiSavingsAccountType()
    {
        AccountType maxiSavingsType = AccountType.MAXI_SAVINGS;
        assertEquals(0, maxiSavingsType.calculate(0), DOUBLE_DELTA);
        assertEquals(1000 * 0.02 / 365, maxiSavingsType.calculate(1000), DOUBLE_DELTA);
        assertEquals((20 + (2000-1000) * 0.05) / 365, maxiSavingsType.calculate(2000), DOUBLE_DELTA);
        assertEquals((20 + 50 + (3000 - 2000) * 0.1) / 365, maxiSavingsType.calculate(3000), DOUBLE_DELTA);
    }

}
