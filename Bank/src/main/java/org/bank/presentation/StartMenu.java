package org.bank.presentation;

import org.apache.log4j.Logger;
import org.bank.models.Customer;
import org.bank.models.Employee;
import org.bank.services.impl.CustomerServicesImpl;
import org.bank.services.impl.EmployeeServicesImpl;

import java.util.Scanner;

public class StartMenu {
    static CustomerMenu cMenu = new CustomerMenu();
    static EmployeeMenu eMenu = new EmployeeMenu();
    final static Logger sMenuLog = Logger.getLogger(StartMenu.class);

    CustomerServicesImpl cSImpl = new CustomerServicesImpl();
    EmployeeServicesImpl eSImpl = new EmployeeServicesImpl();

    public void startMenu(){


        Scanner sc = new Scanner(System.in);
        int choice = 0;

        do {

            sMenuLog.info("Welcome, What would you like to do?");
            sMenuLog.info(" 1. Customer Login \n 2. Employee Login \n 3. Create a new account \n 4. Exit");
            try {
                choice = Integer.parseInt(sc.nextLine());
            }catch (NumberFormatException e){}

            switch(choice){

                case 1:
                    Customer c = cSImpl.customerSignin();
                    boolean auth = c.isAuthenticated();
                    if(auth){
                        sMenuLog.info("Welcome " + c.getCustomerFirstName());
                        cMenu.customerMenu(c);
                    }else{
                        sMenuLog.info("Sorry an employee has not authenticated you yet please try again later.");
                        startMenu();

                    }

                case 2:
                    Employee e = eSImpl.employeeSignIn();
                    sMenuLog.info("Welcome " + e.getEmployeeFirstName());
                    eMenu.employeeMenu(e);
                    break;

                case 3:
                    Customer newCustomer = cSImpl.createCustomer();
                    cMenu.createAccountMenu(newCustomer);
                    break;

                case 4:
                    sMenuLog.info("Thank You");
                    System.exit(0);
                    break;

                default:
                    sMenuLog.info("Not a valid choice");
                    break;

            }

        }while (choice != 4);

    }
}
