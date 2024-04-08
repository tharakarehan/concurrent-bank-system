package entities.accounts;

import entities.Customer;
import enums.AccountType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Account {
    protected final String accountNumber;
    protected double balance;
    protected final AccountType accountType;
    protected Map<String, Customer> customers = new ConcurrentHashMap<>();

    public Account(String accountNumber, AccountType accountType,
                double balance) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;

    }

    public void addCustomer(Customer customer) {
        System.out.printf("Added %s %s to %s account : %s\n", customer.getFirstName(), customer.getLastName(), accountType.getStringValue(), accountNumber);
        customers.put(customer.getCustomerID(), customer);
    }

    public void removeCustomer(String customerID) {
        System.out.printf("Removed %s from %s account : %s\n", customers.get(customerID).getFirstName(), accountType.getStringValue(), accountNumber);
        customers.remove(customerID);
    }

    public Customer getCustomer(String customerID) {
        return customers.get(customerID);
    }

    public Map<String, Customer> getCustomers() {
        return customers;
    }

    public boolean hasCustomer(String customerID) {
        return customers.containsKey(customerID);
    }

    public synchronized void deposit(double amount) {
        balance += amount;
        System.out.printf("Deposited: %f to %s account : %s\n", amount, accountType.getStringValue(), accountNumber);
    }

    public abstract void withdraw(double amount, Customer customer);

    public synchronized double getBalance() {
        System.out.printf("Balance for %s account : %s is %f\n", accountType.getStringValue(), accountNumber, balance);
        return balance;
    }

    public synchronized void applyAnnualFee() {
        double annualFee = accountType.getAnnualFee();
        balance -= annualFee;
        System.out.printf("Annual fee applied: %f for %s account : %s\n", annualFee, accountType.getAnnualFee(), accountNumber);
    }

    public boolean isInvalidValidCustomer(String customerID) {
        return !customers.containsKey(customerID);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
