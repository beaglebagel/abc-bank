package com.abc;

/**
 * AccountType Enum incorporates the account type name and concrete interest calculator.
 */
public enum AccountType
{
    CHECKING("Checking Account", new CheckingInterestCalculator()),
    SAVINGS("Savings Account", new SavingsInterestCalculator()),
    MAXI_SAVINGS("Maxi Savings Account", new MaxiSavingsInterestCalculator());

    private final String typeString;
    private final InterestCalculator calculator;

    public String toString() { return typeString; }
    public double calculate(double amount) { return calculator.interest(amount); }

    private AccountType(String typeString, InterestCalculator calculator)
    {
        this.typeString = typeString;
        this.calculator = calculator;
    }

    /**
     * Inner Interface for Interest Calculator.
     * Different Calculator Class should be maintained per Account Type..
     * Interests are assumed to accrue daily(including weekends) in a year of 365 days.
     */
    private interface InterestCalculator
    {
        /**
         * There is no assumption about positivity of the amount (>0) as transactions within an account
         * could result in negative balance(temporarily) by design. Indeed real Banks do allow this.
         * If the amount < 0, we assume there is 0 interest rather than doing checking input.
         * @param amount: Amount to calculate interest on.
         * @return: interest amount
         */
        public double interest(double amount);
    }

    /** Daily accrued interest rate 0.1% */
    private static class CheckingInterestCalculator implements InterestCalculator
    {
        public double interest(double amount) {
            if(amount <= 0) { return 0; }
            return amount * 0.001 / 365;
        }
    }

    /** Daily accrued interest rate 0.1% for the first 1000, 0.2% for the above. */
    private static class SavingsInterestCalculator implements InterestCalculator
    {
        public double interest(double amount)
        {
            if(amount <= 0) { return 0; }
            if(amount <= 1000) { return amount * 0.001 / 365; }
            return (1 + (amount - 1000) * 0.002) / 365;
        }
    }

    /** Daily accrued interest rate 2% for the first 1000, 5% for the next 1000, 10% for the above. */
    private static class MaxiSavingsInterestCalculator implements InterestCalculator
    {
        public double interest(double amount)
        {
            if(amount <= 0) { return 0; }
            if(amount <= 1000) { return amount * 0.02 / 365; }
            if(amount <= 2000) { return (20 + (amount-1000) * 0.05) / 365; }
            return (20 + 50 + (amount - 2000) * 0.1) / 365;
        }
    }
}

