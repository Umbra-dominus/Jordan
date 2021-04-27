package org.bank.models;

public class Transactions{

    private int transactionId;
    private int customerId;
    private String transactionType;
    private double transationAmount;

    public Transactions() {
    }

    public Transactions(int transactionId, int customerId, String transactionType, double transationAmount) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.transactionType = transactionType;
        this.transationAmount = transationAmount;

    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getTransationAmount() {
        return transationAmount;
    }

    public void setTransationAmount(double transationAmount) {
        this.transationAmount = transationAmount;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "transactionId=" + transactionId +
                ", customerId=" + customerId +
                ", transactionType='" + transactionType + '\'' +
                ", transationAmount=" + transationAmount +
                '}';
    }
}
