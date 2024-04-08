package entities.accounts;

import entities.Customer;
import enums.AccountType;
import utils.CommonConstants;

public class SavingsAccount extends Account {

    private double taxableAmount = 0.0;
    private static final double taxRate = CommonConstants.TAX_RATE;
    private static final double interestRate = CommonConstants.INTEREST_RATE;

    public SavingsAccount(String accountNumber, double balance) {
        super(accountNumber, AccountType.SAVINGS, balance);
    }

    @Override
    public synchronized void withdraw(double amount, Customer customer) {
        if (isInvalidValidCustomer(customer.getCustomerID())) {
            System.out.printf("Invalid customer: %s\n", customer.getFirstName());
            return;
        }
        if (balance >= amount) {
            balance -= amount;
            System.out.printf("Withdrawn: %f from savings account : %s by %s %s\n", amount, accountNumber, customer.getLevel(), customer.getFirstName());
        } else {
            System.out.printf("insufficient funds in savings account : %s\n", accountNumber);
        }
    }

    public synchronized void applyInterest() {
        double interest = balance * interestRate;
        balance += interest;
        taxableAmount += interest;
        System.out.printf("Interest applied: %f for savings account : %s : current taxable amount:%f \n", interest, accountNumber, taxableAmount);
    }

    public synchronized void applyTax() {
        if (taxableAmount == 0.0) {
            System.out.printf("No taxable amount for savings account : %s\n", accountNumber);
            return;
        }
        double tax = taxableAmount * taxRate;
        balance -= tax;
        taxableAmount = 0.0;
        System.out.printf("Tax applied: %f for savings account : %s\n", tax, accountNumber);
    }

    public double getTaxableAmount() {
        return taxableAmount;
    }

    public void setTaxableAmount(double taxableAmount) {
        this.taxableAmount = taxableAmount;
    }
}
