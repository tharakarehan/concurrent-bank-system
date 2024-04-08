package entities.accounts;

import entities.Customer;
import enums.AccountType;
import utils.CommonConstants;

public class CheckingAccount extends Account{
    private double overdraftLimit;
    private boolean overdraftUsed = false;

    public CheckingAccount(String accountNumber, double balance,
                           double overdraftLimit) {
        super(accountNumber, AccountType.CHECKING, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public synchronized void withdraw(double amount, Customer customer) {
        if (isInvalidValidCustomer(customer.getCustomerID())) {
            System.out.printf("Invalid customer: %s\n", customer.getFirstName());
            return;
        }
        if (balance >= amount) {
            balance -= amount;
            System.out.printf("Withdrawn: %f from checking account : %s by %s\n", amount, accountNumber, customer.getFirstName());
        }else if (balance + overdraftLimit >= amount) {
            balance -= amount;
            overdraftUsed = true;
            System.out.printf("Withdrawn: %f from checking account : %s by %s %s\n", amount, accountNumber, customer.getLevel(), customer.getFirstName());
        } else {
            System.out.printf("Overdraft limit exceeded for checking account : %s by %s\n", accountNumber, customer.getFirstName());
        }
    }

    public synchronized void deductOverdraftFee() {
        if (!overdraftUsed) {
            return;
        }
        double overdraftFee = overdraftLimit * CommonConstants.OVERDRAFT_RATE;
        balance -= overdraftFee;
        System.out.printf("Overdraft fee applied: %f for checking account : %s\n", overdraftFee, accountNumber);
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public boolean isOverdraftUsed() {
        return overdraftUsed;
    }

    public void setOverdraftUsed(boolean overdraftUsed) {
        this.overdraftUsed = overdraftUsed;
    }
}
