package org.bank.presentation;

import org.apache.log4j.Logger;
import org.bank.models.Customer;
import org.bank.models.Employee;
import org.bank.models.Transactions;
import org.bank.services.impl.CustomerServicesImpl;
import org.bank.services.impl.EmployeeServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeMenu {
    static StartMenu start = new StartMenu();
    final static Logger eMenuLog = Logger.getLogger(EmployeeMenu.class);
    CustomerServicesImpl cSImpl = new CustomerServicesImpl();
    EmployeeServicesImpl eSImpl = new EmployeeServicesImpl();

    public void employeeMenu(Employee e) {

        int choice = 0;
        do {
            eMenuLog.info("Welcome, What would you like to do?");
            eMenuLog.info(" 1. View customer account by first name. \n 2. View customer account by customer ID. \n 3. View all customer accounts. \n 4. Delete a customer account by name "
                    + "\n 5. Delete a customer account by Customer id \n 6. Authenticate a user account \n 7. view all transactions \n 8. Exit");

            Scanner sc = new Scanner(System.in);
            choice = Integer.parseInt(sc.nextLine());
            switch (choice) {

                case 1:
                    eMenuLog.info("Please enter the customer name on the account.");
                    Scanner sc1 = new Scanner(System.in);
                    String who = sc1.nextLine();
                    Customer cName = eSImpl.selectCustomer(who);
                    eMenuLog.info(cName);
                    employeeMenu(e);
                    break;

                case 2:
                    eMenuLog.info("Please enter the customer ID on the account.");
                    int cId = sc.nextInt();
                    Customer customerId = eSImpl.selectCustomerId(cId);
                    eMenuLog.info(customerId);
                    employeeMenu(e);
                    break;

                case 3:

                    List<Customer> cList = new ArrayList();
                    cList = eSImpl.selectAllCustomers();
                    for (Customer element : cList) {
                        eMenuLog.info(element);
                    }
                    employeeMenu(e);
                    break;

                case 4:

                    eMenuLog.info("please enter the customer name on the account you with to delete");
                    String dName = sc.next();
                    eSImpl.deleteCustomerByName(dName);
                    employeeMenu(e);
                    break;

                case 5:

                    eMenuLog.info("please enter the customer ID on the account you with to delete");
                    int deleteId = sc.nextInt();
                    eSImpl.deleteCustomerById(deleteId);
                    employeeMenu(e);
                    break;

                case 6:
                    eMenuLog.info("please enter the customer name on the account you with to Authenticate");
                    String authenticate = sc.next();
                    eSImpl.authenticateCustomer(authenticate);
                    employeeMenu(e);
                    break;

                case 7:
                    List<Transactions> tList = new ArrayList();
                    tList = eSImpl.showTransactions();
                    for (Transactions element : tList) {
                        eMenuLog.info(element);
                    }
                    employeeMenu(e);
                    break;

                case 8:
                    eMenuLog.info("Thank You");
                    start.startMenu();
                    break;

                default:
                    eMenuLog.warn("Not a valid choice please choice a number between 1-8");
                    break;

            }
        } while (choice != 8) ;


    }
}
