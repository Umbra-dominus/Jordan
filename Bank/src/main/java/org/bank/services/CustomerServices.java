package org.bank.services;

import org.bank.models.Customer;

public interface CustomerServices {

    public  Customer createCustomer();
    Customer customerSignin();
    double withdrawSavings(Customer c);
    double withdrawChecking(Customer c);
    double depositSavings(Customer c);
    double depositChecking(Customer c);
    double checkingAmount(Customer c);
    double savingAmount(Customer c);
    double totalAmount(Customer c);
    Customer createSavingsAccount(Customer c);
    Customer createCheckingAccount(Customer c);
    Customer createBothAccount(Customer c);
    boolean transferMoney(Customer c);
}
