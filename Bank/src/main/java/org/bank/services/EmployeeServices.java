package org.bank.services;



import org.bank.models.Customer;
import org.bank.models.Employee;
import org.bank.models.Transactions;


import java.util.List;

public interface EmployeeServices {

    Employee employeeSignIn();
    Customer selectCustomer(String name);
    Customer selectCustomerId(int id);
    void deleteCustomerById(int id);
    void deleteCustomerByName(String name);
    List<Customer> selectAllCustomers();
    void authenticateCustomer(String name);
    List<Transactions> showTransactions();
}
