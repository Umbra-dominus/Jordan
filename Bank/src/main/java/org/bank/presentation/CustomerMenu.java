package org.bank.presentation;

import org.apache.log4j.Logger;
import org.bank.models.Customer;
import org.bank.services.impl.CustomerServicesImpl;

import java.text.DecimalFormat;
import java.util.Scanner;

public class CustomerMenu {
    static StartMenu start = new StartMenu();
    CustomerServicesImpl cSImpl = new CustomerServicesImpl();
    DecimalFormat df = new DecimalFormat("#.00");
    final static Logger cMenuLog = Logger.getLogger(CustomerMenu.class);


    public void customerMenu(Customer c) {
        Scanner sc = new Scanner(System.in);
        int num = 0;

        do{

            cMenuLog.info("What services would you like to use today?");
            cMenuLog.info(" 1. savings account balance \n 2. checking account balance \n 3. check total amount in checking and saving account \n 4. Withdraw from savings account \n 5. Deposit to savings acount"
                + "\n 6. Withdraw from checking account \n 7. Deposit to checking acount \n 8. Transfer money to another account \n 9. Exit");
        try {
            num = Integer.parseInt(sc.nextLine());
        }catch (NumberFormatException e){}


        switch(num) {

            case 1:

                double sBalance = cSImpl.savingAmount(c);
                cMenuLog.info("Your current savings account balance is $" + df.format(sBalance));
                customerMenu(c);
                break;

            case 2:

                double cBalance = cSImpl.checkingAmount(c);
                cMenuLog.info("Your current Checking account balance is $" + df.format(cBalance));
                customerMenu(c);
                break;

            case 3:

                double tBalance = cSImpl.totalAmount(c);
                cMenuLog.info("Your current total accounts balance is $" + df.format(tBalance));
                customerMenu(c);
                break;

            case 4:

                double sWBalance = cSImpl.withdrawSavings(c);
                cMenuLog.info("your savings account balance is $" + df.format(sWBalance));
                customerMenu(c);
                break;

            case 5:

                double sDBalance = cSImpl.depositSavings(c);
                cMenuLog.info("your savings account balance is $" + df.format(sDBalance));
                customerMenu(c);
                break;

            case 6:

                double cWBalance = cSImpl.withdrawChecking(c);
                cMenuLog.info("your checking account balance is $" + df.format(cWBalance));
                customerMenu(c);
                break;

            case 7:

                double cDBalance = cSImpl.depositChecking(c);
                cMenuLog.info("your checking account balance is $" + df.format(cDBalance));
                customerMenu(c);
                break;

            case 8:

                boolean completed = cSImpl.transferMoney(c);
                if (completed) {
                    cMenuLog.info("You have successfully completed your transfer. ");
                } else {
                    cMenuLog.info("The transfer could not be completed.");
                }
                customerMenu(c);
                break;

            case 9:
                cMenuLog.info("Thank You");
                start.startMenu();
                break;

            default:
                cMenuLog.warn("Not a valid choice please choice a number between 1-9");
                customerMenu(c);
                break;
        }

        }while (num != 9);



        }

    public void createAccountMenu(Customer c) {


        cMenuLog.info("What type of account do you want to create? \n Checking \n Savings \n Both \n or logout");

        Scanner sc = new Scanner(System.in);

        String accountType = sc.nextLine();

        accountType.toLowerCase();

        if(accountType.equals("checking")) {
            cSImpl.createCheckingAccount(c);
            cMenuLog.info("Thank you for creating a Checking account with us. An employee with Authenticate you soon.");
            start.startMenu();
        } else if (accountType.equals("savings")) {
            cSImpl.createSavingsAccount(c);
            cMenuLog.info("Thank you for creating a Savings account with us. An employee with Authenticate you soon.");
            start.startMenu();
        }else if (accountType.equals("both")) {
            cSImpl.createBothAccount(c);
            cMenuLog.info("Thank you for creating a Checking and Savings account with us. An employee with Authenticate you soon.");
            start.startMenu();
        }else if (accountType.equals("logout")) {
            cMenuLog.info("Thank you for registering withe us.");
            start.startMenu();

        }else {

            cMenuLog.warn("Invalid Option, Please try again.");
            createAccountMenu(c);

        }


    }


    }



