package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Bank class.
 */
public class Bank
{
    private final List<Customer> customers;

    public Bank()
    {
        this.customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String customerSummary()
    {
        StringBuilder summary = new StringBuilder("Customer Summary");
        customers.forEach(c ->
                summary.append(String.format("\n - %s (%s)", c.name(), format(c.numAccounts(), "account"))));
        return summary.toString();
    }

    // Just return the statement string with total interest paid on all customer account.
    public String interestSummary()
    {
        return String.format("Total Interest Paid on All Accounts %s",
                                String.format("$%,.2f", this.totalInterestPaid()));
    }

    // Make sure correct plural of word is created based on the number passed in:
    // If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word)
    {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid()
    {
        return customers.stream().mapToDouble(c -> c.totalInterestEarned()).sum();
    }

}
