package org.bank.models;

public class CheckingAccount {
    private int checkingAccountId;
    private int customerId;
    private double checkingAccountAmount;

    public CheckingAccount() {
    }

    public CheckingAccount(int checkingAccountId, int customerId, double checkingAccountAmount) {
        this.checkingAccountId = checkingAccountId;
        this.customerId = customerId;
        this.checkingAccountAmount = checkingAccountAmount;
    }

    public int getCheckingAccountId() {
        return checkingAccountId;
    }

    public void setCheckingAccountId(int checkingAccountId) {
        this.checkingAccountId = checkingAccountId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getCheckingAccountAmount() {
        return checkingAccountAmount;
    }

    public void setCheckingAccountAmount(double checkingAccountAmount) {
        this.checkingAccountAmount = checkingAccountAmount;
    }

    @Override
    public String toString() {
        return "CheckingAccount{" +
                "checkingAccountId=" + checkingAccountId +
                ", customerId=" + customerId +
                ", checkingAccountAmount=" + checkingAccountAmount +
                '}';
    }
}
