package org.bank.services.impl;

import org.apache.log4j.Logger;
import org.bank.database.connection.DatabaseConnection;
import org.bank.models.CheckingAccount;
import org.bank.models.Customer;
import org.bank.models.SavingsAccount;
import org.bank.presentation.CustomerMenu;
import org.bank.presentation.EmployeeMenu;
import org.bank.presentation.StartMenu;
import org.bank.services.CustomerServices;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CustomerServicesImpl implements CustomerServices {

    static StartMenu start = new StartMenu();
    static CustomerMenu cMenu = new CustomerMenu();
    final static Logger cServeLog = Logger.getLogger(CustomerServicesImpl.class);


    @Override
    public Customer createCustomer(){
        Customer newCustomer = new Customer();
        Scanner sc = new Scanner(System.in);
        cServeLog.info("please enter your First name.");
        String fName = sc.nextLine();

        cServeLog.info("please enter your Last name.");
        String lName = sc.nextLine();

        cServeLog.info("please enter the User name you want.");
        String uName = sc.nextLine();

        cServeLog.info("please enter the Password you want to use.");
        String uPassword = sc.nextLine();

        newCustomer.setCustomerFirstName(fName);
        newCustomer.setCustomerLastName(lName);
        newCustomer.setUsername(uName);
        newCustomer.setPassword(uPassword);

        try(Connection conn = DatabaseConnection.getConnection()){
            String sql = "INSERT INTO customer (first_name, last_name, User_name, User_password ) VALUES"
                    + "(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, fName);
            ps.setString(2, lName);
            ps.setString(3, uName);
            ps.setString(4, uPassword);

            ps.execute();

        } catch (SQLException e) {
        }
        return newCustomer;
    }

    @Override
    public Customer customerSignin() {
        Scanner sc = new Scanner(System.in);
        Customer customer = null;
        cServeLog.info("Please enter your user name.");
        String loginName = sc.nextLine();
        cServeLog.info("Please enter your password.");
        String loginPassword = sc.nextLine();

        try (Connection conn = DatabaseConnection.getConnection()){
            String sql = "SELECT c.customer_id, c.first_name, c.last_name, c.authenticated \n" +
                    "FROM customer c  WHERE c.user_Name = ? AND c.user_Password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, loginName);
            ps.setString(2, loginPassword);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                customer = new Customer();
                customer.setCustomerId(result.getInt("customer_Id"));
                customer.setCustomerFirstName(result.getString("first_Name"));
                customer.setCustomerLastName(result.getString("last_Name"));
                customer.setAuthenticated(result.getBoolean("authenticated"));
//                customer.setCheckingaccountId(result.getInt("checking_id"));
//                customer.setCheckingAmount(result.getDouble("checking_balance"));
//                customer.setSavingsAccountId(result.getInt("savings_id"));
//                customer.setSavingsAmount(result.getDouble("savings_balance"));

            }

            else {
                cServeLog.warn("Invalide Login. Please Try again");
                start.startMenu();
            }
        } catch (SQLException e) {

        }
        return customer;

    }

    @Override
    public double withdrawSavings(Customer c) {

        double balance = savingAmount(c);
        double amount = 0;

        Scanner sc = new Scanner(System.in);
        cServeLog.info("How much would you like to withdraw?");
        amount = sc.nextDouble();

            if(balance - amount >= 0) {
                balance = balance - amount;
                String sql = "UPDATE savings_account SET savings_balance = ? WHERE customer_id = ? ";
                try (Connection conn = DatabaseConnection.getConnection()){

                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setDouble(1, balance);
                    ps.setInt(2, c.getCustomerId());

                    ps.execute();

                    String sqlT = "INSERT INTO Transactions(customer_id, Transaction_type, Transaction_Amount) Values(?,'Savings Withdraw', ?);";
                    PreparedStatement psT = conn.prepareStatement(sqlT);
                    psT.setInt(1, c.getCustomerId());
                    psT.setDouble(2, amount);
                    psT.execute();

                    return balance;



                }catch(SQLException e) {
                }

            }else {
                cServeLog.warn("Invalid withdraw, insufficient funds");
                cMenu.customerMenu(c);

            }

            return balance;
        }



    @Override
    public double withdrawChecking(Customer c) {
        double balance = checkingAmount(c);
        double amount = 0;


        cServeLog.info("How much would you like to withdraw?");
        Scanner sc = new Scanner(System.in);
        amount = sc.nextDouble();
        if(balance - amount >= 0) {
            balance = balance - amount;
            String sql = "UPDATE Checking_account SET Checking_balance = ? WHERE customer_id = ? ";

            try (Connection conn = DatabaseConnection.getConnection()){

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setDouble(1, balance);
                ps.setInt(2, c.getCustomerId());
                ps.execute();

                String sqlT = "INSERT INTO Transactions(customer_id, Transaction_type, Transaction_Amount) Values(?,'Checking Withdraw', ?);";
                PreparedStatement psT = conn.prepareStatement(sqlT);
                psT.setInt(1, c.getCustomerId());
                psT.setDouble(2, amount);
                psT.execute();

                return balance;

            }catch(SQLException e) {
            }

        }else {
            cServeLog.warn("Invalid withdraw, insufficient funds");
            cMenu.customerMenu(c);

        }

        return balance;
    }

    @Override
    public double depositSavings(Customer c) {
        double balance = savingAmount(c);
        double amount = 0;

        Scanner sc = new Scanner(System.in);
        cServeLog.info("How much would you like to deposit?");
        amount = sc.nextDouble();

        if(balance > 0 && amount >= 0){

            balance = balance + amount;
            String sql = "UPDATE savings_account SET savings_balance = ? WHERE customer_id = ? ";
            try (Connection conn = DatabaseConnection.getConnection()){

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setDouble(1, balance);
                ps.setInt(2, c.getCustomerId());
                ps.execute();

                String sqlT = "INSERT INTO Transactions(customer_id, Transaction_type, Transaction_Amount) Values(?,'Savings Deposit', ?);";
                PreparedStatement psT = conn.prepareStatement(sqlT);
                psT.setInt(1, c.getCustomerId());
                psT.setDouble(2, amount);
                psT.execute();


            }catch(SQLException e) {
                e.printStackTrace();
            }


        } else if(amount >= 0) {
            balance = 0;
            balance = balance + amount;
            String sql2 = "INSERT INTO Savings_Account(Customer_id, Savings_Balance) VALUES"
                    + "(?,?)";
            try (Connection conn = DatabaseConnection.getConnection()){

                PreparedStatement ps2 = conn.prepareStatement(sql2);
                ps2.setDouble(2, balance);
                ps2.setInt(1, c.getCustomerId());
                ps2.execute();

                String sqlT = "INSERT INTO Transactions(customer_id, Transaction_type, Transaction_Amount) Values(?,'Savings Deposit', ?);";
                PreparedStatement psT = conn.prepareStatement(sqlT);
                psT.setInt(1, c.getCustomerId());
                psT.setDouble(2, amount);
                psT.execute();

            }catch(SQLException e) {
            }
        }else {
            cServeLog.warn("invalide input Please try again.");
            depositSavings(c);
        }
        return balance;    }

    @Override
    public double depositChecking(Customer c) {
        double balance = checkingAmount(c);
        double amount = 0;

        Scanner sc = new Scanner(System.in);
        cServeLog.info("How much would you like to deposit?");
        amount = sc.nextDouble();

        if(balance > 0 && amount >= 0){

            balance = balance + amount;
            String sql = "UPDATE Checking_account SET Checking_balance = ? WHERE customer_id = ? ";

            try (Connection conn = DatabaseConnection.getConnection()){

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setDouble(1, balance);
                ps.setInt(2, c.getCustomerId());
                ps.execute();

                String sqlT = "INSERT INTO Transactions(customer_id, Transaction_type, Transaction_Amount) Values(?,'Checking Deposit', ?);";
                PreparedStatement psT = conn.prepareStatement(sqlT);
                psT.setInt(1, c.getCustomerId());
                psT.setDouble(2, amount);
                psT.execute();


            }catch(SQLException e) {
            }


        } else if(amount > 0) {
            balance = 0;
            balance = balance + amount;
            String sql2 = "INSERT INTO Checking_Account(Customer_id, Checking_Balance) VALUES"
                    + "(?,?)";

            try (Connection conn = DatabaseConnection.getConnection()){

                PreparedStatement ps2 = conn.prepareStatement(sql2);
                ps2.setDouble(2, balance);
                ps2.setInt(1, c.getCustomerId());
                ps2.execute();

                String sqlT = "INSERT INTO Transactions(customer_id, Transaction_type, Transaction_Amount) Values(?,'Savings Deposit', ?);";
                PreparedStatement psT = conn.prepareStatement(sqlT);
                psT.setInt(1, c.getCustomerId());
                psT.setDouble(2, amount);
                psT.execute();

            }catch(SQLException e) {
            }
        }else {
            cServeLog.warn("invalide input Please try again.");
            depositChecking(c);
        }

        return balance;    }

    @Override
    public double checkingAmount(Customer c) {
        String sql = "SELECT * FROM Checking_Account WHERE customer_id = ? ";
        double cBalance = 0;
        CheckingAccount cAccount = null;
        try (Connection conn = DatabaseConnection.getConnection()){

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, c.getCustomerId());
            ps.execute();

            ResultSet result = ps.executeQuery();
            while(result.next()) {
                cAccount = new CheckingAccount();
                c.setCheckingaccountId(result.getInt("Checking_Id"));
                cAccount.setCustomerId(result.getInt("Customer_Id"));
                cAccount.setCheckingAccountId(result.getInt("Checking_Id"));
                cAccount.setCheckingAccountAmount(result.getDouble("Checking_Balance"));
                cBalance = cAccount.getCheckingAccountAmount();

            }


        }catch(SQLException e) {
        }


        return cBalance;
    }

    @Override
    public double savingAmount(Customer c) {
        String sql = "SELECT * FROM Savings_Account WHERE customer_id = ? ";
        double sBalance = 0;
        SavingsAccount sAccount = null;
        try (Connection conn = DatabaseConnection.getConnection()){

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, c.getCustomerId());
            ps.execute();

            ResultSet result = ps.executeQuery();
            while(result.next()) {
                sAccount = new SavingsAccount();
                c.setSavingsAccountId(result.getInt("Savings_Id"));
                sAccount.setCustomerId(c.getCustomerId());
                sAccount.setSavingAccountId(result.getInt("Savings_Id"));
                sAccount.setSavingsAccountAmount(result.getDouble("Savings_Balance"));
                sBalance = sAccount.getSavingsAccountAmount();
            }


        }catch(SQLException e) {
        }


        return sBalance;
    }

    @Override
    public double totalAmount(Customer c) {
        double totalBalance = 0;
        double cBalance = checkingAmount(c);
        double sBalance = savingAmount(c);

        totalBalance = cBalance + sBalance;

        return totalBalance;
    }

    @Override
    public Customer createSavingsAccount(Customer c) {
        Customer customer = c ;
        double amount = 0;

        String sql = "SELECT  Customer_id FROM customer WHERE first_name = ?";


        String sql2 = "INSERT INTO Savings_Account(customer_id, Savings_balance) VALUES"
                + "(?,?)";
        cServeLog.info("Please enter the amount you are using to open this  savings acount.");

        Scanner sc = new Scanner(System.in);
        amount = sc.nextDouble();

        if(amount >= 0) {
            try (Connection conn = DatabaseConnection.getConnection()){

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,customer.getCustomerFirstName());
                ps.execute();
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    customer.setCustomerId(rs.getInt("Customer_id"));
                }

                PreparedStatement ps2 = conn.prepareStatement(sql2);
                ps2.setInt(1, customer.getCustomerId());
                ps2.setDouble(2, amount);
                ps2.execute();

            } catch (SQLException e) {
            }

        }else {
            cServeLog.warn("invalid input");
            createSavingsAccount(customer);
        }

        return customer;
    }

    @Override
    public Customer createCheckingAccount(Customer c) {
        Customer customer = c ;

        String sql = "SELECT  Customer_id FROM customer WHERE first_name = ?";

        cServeLog.info("Please enter the amount you are using to open this checking acount.");
        double amount = 0;
        Scanner sc = new Scanner(System.in);
        amount = sc.nextDouble();
        String sql2 = "INSERT INTO Checking_Account(customer_id, Checking_balance) VALUES"
                + "(?,?)";

        if(amount >= 0) {
            try (Connection conn = DatabaseConnection.getConnection()){

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,customer.getCustomerFirstName());
                ps.execute();
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    customer.setCustomerId(rs.getInt("Customer_id"));
                }
                PreparedStatement ps2 = conn.prepareStatement(sql2);
                ps2.setInt(1, customer.getCustomerId());
                ps2.setDouble(2, amount);
                ps2.execute();

            } catch (SQLException e) {
            }
        }else {
            cServeLog.warn("invalid input");
            createCheckingAccount(customer);
        }


        return customer;
    }

    @Override
    public Customer createBothAccount(Customer c) {
        Customer customer = c;

        customer = createSavingsAccount(customer);
        customer = createCheckingAccount(customer);

        return customer;
    }

    @Override
    public boolean transferMoney(Customer c) {
        double balance = checkingAmount(c);
        double amount = 0;
        String transferName;
        Scanner sc2 = new Scanner(System.in);

        cServeLog.info("How much would you like to transfer?");
        Scanner sc = new Scanner(System.in);
        amount = Double.parseDouble(sc2.nextLine());
        cServeLog.info("Whos checking account are you transfering money to?");
        transferName = sc2.nextLine();

        if(balance - amount >= 0) {
            balance = balance - amount;
            String sql = "UPDATE Checking_account SET checking_balance = ? WHERE customer_id = ? ";

            try (Connection conn = DatabaseConnection.getConnection()){

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setDouble(1, balance);
                ps.setInt(2, c.getCustomerId());
                ps.execute();

                String sqlT = "INSERT INTO Transactions(customer_id, Transaction_type, Transaction_Amount) Values(?,'Money Transfer', ?);";
                PreparedStatement psT = conn.prepareStatement(sqlT);
                psT.setInt(1, c.getCustomerId());
                psT.setDouble(2, amount);
                psT.execute();

            }catch(SQLException e) {
            }

        }else {
            cServeLog.warn("Invalid transfer, insufficient funds");
            cMenu.customerMenu(c);
            return false;

        }



        String sql2 = "UPDATE Checking_account SET Checking_balance = checking_balance + ? where customer_id in(select customer_id from customer where first_name = ?)";
        try (Connection conn = DatabaseConnection.getConnection()){

            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setDouble(1, amount);
            ps2.setString(2,transferName);

            ps2.execute();

        }catch(SQLException e) {
        }

        return true;
    }
}
