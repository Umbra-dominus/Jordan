package org.bank.models;

public class SavingsAccount{
    private int  savingAccountId;
    private int customerId;
    private double savingsAccountAmount;

    public SavingsAccount() {
    }

    public SavingsAccount(int savingAccountId, int customerId, double savingsAccountAmount) {
        this.savingAccountId = savingAccountId;
        this.customerId = customerId;
        this.savingsAccountAmount = savingsAccountAmount;

    }

    public int getSavingAccountId() {
        return savingAccountId;
    }

    public void setSavingAccountId(int savingAccountId) {
        this.savingAccountId = savingAccountId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getSavingsAccountAmount() {
        return savingsAccountAmount;
    }

    public void setSavingsAccountAmount(double savingsAccountAmount) {
        this.savingsAccountAmount = savingsAccountAmount;
    }

    @Override
    public String toString() {
        return "SavingsAccount{" +
                "savingAccountId=" + savingAccountId +
                ", customerId=" + customerId +
                ", savingsAccountAmount=" + savingsAccountAmount +
                '}';
    }
}
