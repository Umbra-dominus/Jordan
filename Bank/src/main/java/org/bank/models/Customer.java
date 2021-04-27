package org.bank.models;

public class Customer {

    private int customerId;
    private String customerFirstName;
    private String customerLastName;
    private String username;
    private String password;
    private boolean authenticated;
    private int savingsAccountId;
    private int checkingaccountId;
    private double checkingAmount;
    private double savingsAmount;

    public Customer() {
    }

    public Customer(int customerId, String customerFirstName, String customerLastName, String username, String password, boolean authenticated, int savingsAccountId, int checkingaccountId, double checkingAmount, double savingsAmount) {
        this.customerId = customerId;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.username = username;
        this.password = password;
        this.authenticated = authenticated;
        this.savingsAccountId = savingsAccountId;
        this.checkingaccountId = checkingaccountId;
//        this.checkingAmount = checkingAmount;
//        this.savingsAmount = savingsAmount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public int getSavingsAccountId() {
        return savingsAccountId;
    }

    public void setSavingsAccountId(int savingsAccountId) {
        this.savingsAccountId = savingsAccountId;
    }

    public int getCheckingaccountId() {
        return checkingaccountId;
    }

    public void setCheckingaccountId(int checkingaccountId) {
        this.checkingaccountId = checkingaccountId;
    }

//    public double getCheckingAmount() {
//        return checkingAmount;
//    }
//
//    public void setCheckingAmount(double checkingAmount) {
//        this.checkingAmount = checkingAmount;
//    }
//
//    public double getSavingsAmount() {
//        return savingsAmount;
//    }
//
//    public void setSavingsAmount(double savingsAmount) {
//        this.savingsAmount = savingsAmount;
//    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerFirstName='" + customerFirstName + '\'' +
                ", customerLastName='" + customerLastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authenticated=" + authenticated +
                ", savingsAccountId=" + savingsAccountId +
                ", checkingaccountId=" + checkingaccountId +
                ", checkingAmount=" + checkingAmount +
                ", savingsAmount=" + savingsAmount +
                '}';
    }
}
