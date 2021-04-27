package org.bank.services.impl;

import org.apache.log4j.Logger;
import org.bank.database.connection.DatabaseConnection;
import org.bank.models.Customer;
import org.bank.models.Employee;
import org.bank.models.Transactions;
import org.bank.presentation.StartMenu;
import org.bank.services.EmployeeServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeServicesImpl implements EmployeeServices {
    final static Logger eServeLog = Logger.getLogger(EmployeeServicesImpl.class);
    static StartMenu start = new StartMenu();


    @Override
    public Employee employeeSignIn() {
        Employee employee = null;
        Scanner sc = new Scanner(System.in);
        eServeLog.info("Please enter your user name.");
        String loginName = sc.nextLine();
        eServeLog.info("Please enter your password.");
        String loginPassword = sc.nextLine();

        try (Connection conn = DatabaseConnection.getConnection()){
            String sql = "SELECT * FROM employee WHERE User_Name = ? AND User_Password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, loginName);
            ps.setString(2, loginPassword);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                employee = new Employee();
                employee.setEmployeeId(result.getInt("employee_Id"));
                employee.setEmployeeFirstName(result.getString("First_Name"));
                employee.setEmployeeLastName(result.getString("Last_Name"));


            }

            else {
                eServeLog.warn("Invalide Login. Please Try again");
                start.startMenu();
            }
        } catch (SQLException e) {
        }
        return employee;
    }

    @Override
    public Customer selectCustomer(String name) {
        Customer customer = null;

        try(Connection conn = DatabaseConnection.getConnection()){

            String sql = "SELECT * FROM customer AS c FULL JOIN checking_account AS ca ON ca.customer_id = c.customer_id FULL JOIN savings_account sa ON sa.customer_id = c.customer_id WHERE c.first_name = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                customer = new Customer(
                        rs.getInt("Customer_id"),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBoolean(6),
                        rs.getInt("savings_id"),
                        rs.getInt("Checking_id"),
                        rs.getDouble("Checking_Balance"),
                        rs.getDouble("Savings_Balance")
                );


            }

        } catch (SQLException e) {
        }

        return customer;
    }

    @Override
    public Customer selectCustomerId(int id) {
        Customer customer = null;

        try(Connection conn = DatabaseConnection.getConnection()){

            String sql = "SELECT * FROM customer AS c FULL JOIN checking_account AS ca ON ca.customer_id = c.customer_id FULL JOIN savings_account sa ON sa.customer_id = c.customer_id WHERE c.customer_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                customer = new Customer(
                        rs.getInt("Customer_id"),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBoolean(6),
                        rs.getInt("savings_id"),
                        rs.getInt("Checking_id"),
                        rs.getDouble("Checking_Balance"),
                        rs.getDouble("Savings_Balance")
                );


            }

        } catch (SQLException e) {
        }

        return customer;
    }


    @Override
    public void deleteCustomerById(int id) {

        try(Connection conn = DatabaseConnection.getConnection()){

            String sql = "delete FROM savings_account WHERE customer_id = ?;"
                    + "delete FROM checking_account WHERE customer_id = ?;"
                    + "DELETE FROM Transactions WHERE customer_id = ?;"
                    + "delete FROM customer WHERE customer_Id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, id);
            ps.setInt(3, id);
            ps.setInt(4, id);
            ps.execute();


        } catch (SQLException e) {
        }

    }


    @Override
    public void deleteCustomerByName(String name) {

        try(Connection conn = DatabaseConnection.getConnection()){

            String sql = "delete FROM savings_account WHERE customer_id in(select customer_id from customer where first_name = ?);"
                    + "delete FROM checking_account WHERE customer_id in(select customer_id from customer where first_name = ?);"
                    + "DELETE FROM Transactions WHERE customer_id in(select customer_id from customer where first_name = ?);"
                    + "delete from customer where first_name = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,name);
            ps.setString(3,name);
            ps.setString(4,name);

            ps.execute();


        } catch (SQLException e) {

        }

    }


    @Override
    public List<Customer> selectAllCustomers() {
        List<Customer> customerList = new ArrayList<>();

        try(Connection conn = DatabaseConnection.getConnection()){

            String sql = "SELECT * FROM customer AS c FULL JOIN checking_account AS ca ON ca.customer_id = c.customer_id FULL JOIN savings_account sa ON sa.customer_id = c.customer_id ORDER BY c.first_name";

            PreparedStatement ps = conn.prepareStatement(sql);


            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                customerList.add(new Customer(
                        rs.getInt("Customer_id"),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBoolean(6),
                        rs.getInt("savings_id"),
                        rs.getInt("Checking_id"),
                        rs.getDouble("Checking_Balance"),
                        rs.getDouble("Savings_Balance")
                ));


            }

        } catch (SQLException e) {
        }


        return customerList;
    }

    @Override
    public void authenticateCustomer(String name) {

        try(Connection conn = DatabaseConnection.getConnection()){

            String sql = "UPDATE customer SET Authenticated = true WHERE first_name = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);

            ps.execute();

        } catch (SQLException e) {
        }
    }

    @Override
    public List<Transactions> showTransactions() {
        List<Transactions> transList = new ArrayList<>();

        try(Connection conn = DatabaseConnection.getConnection()){

            String sql = "SELECT * FROM Transactions";

            PreparedStatement ps = conn.prepareStatement(sql);


            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                transList.add(new Transactions(
                        rs.getInt("Transaction_id"),
                        rs.getInt("Customer_id"),
                        rs.getString("Transaction_type"),
                        rs.getDouble("Transaction_amount")
                ));


            }

        } catch (SQLException e) {
        }


        return transList;
    }
}
