package entities.accounts;

import entities.Customer;
import enums.AccountType;
import utils.CommonConstants;

public class MoneyMarketAccount extends Account{

    private int transactionsRemaining;
    private double taxableAmount = 0.0;
    private static final double taxRate = CommonConstants.TAX_RATE;
    private static final double interestRate = CommonConstants.MONEY_MARKET_INTEREST_RATE;

    public MoneyMarketAccount(String accountNumber, double balance,
                              int transactionsRemaining) {
        super(accountNumber, AccountType.MONEY_MARKET, balance);
        this.transactionsRemaining = transactionsRemaining;
    }

    @Override
    public synchronized void withdraw(double amount, Customer customer) {
        if (isInvalidValidCustomer(customer.getCustomerID())) {
            System.out.printf("Invalid customer: %s\n", customer.getFirstName());
            return;
        }
        if (transactionsRemaining <= 0) {
            System.out.printf("Transaction limit exceeded for money market account : %s by %s\n", accountNumber, customer.getFirstName());
            return;
        }
        if (balance >= amount) {
            balance -= amount;
            transactionsRemaining--;
            System.out.printf("Withdrawn: %f from money market account : %s by %s %s\n", amount, accountNumber, customer.getLevel(), customer.getFirstName());
        } else {
            System.out.printf("insufficient funds in money market account : %s\n", accountNumber);
        }
    }

    public synchronized void applyInterest() {
        double interest= balance * interestRate;
        balance += interest;
        taxableAmount += interest;
        System.out.printf("Interest applied: %f for money market account : %s : current taxable amount:%f \n", interest, accountNumber, taxableAmount);
    }

    public synchronized void applyTax() {
        if (taxableAmount == 0.0) {
            System.out.printf("No taxable amount for money market account : %s\n", accountNumber);
            return;
        }
        double tax = taxableAmount * taxRate;
        balance -= tax;
        taxableAmount = 0.0;
        System.out.printf("Tax applied: %f for money market account : %s\n", tax, accountNumber);
    }

    public void setTransactionsRemaining(int transactionsRemaining) {
        if (transactionsRemaining <= 0) {
            System.out.println("Invalid transaction limit");
            return;
        }
        this.transactionsRemaining = transactionsRemaining;
    }

    public int getTransactionsRemaining() {
        return transactionsRemaining;
    }

    public double getTaxableAmount() {
        return taxableAmount;
    }

    public void setTaxableAmount(double taxableAmount) {
        this.taxableAmount = taxableAmount;
    }

}
