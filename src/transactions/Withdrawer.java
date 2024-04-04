package transactions;

import entities.Customer;
import entities.accounts.Account;
import entities.accounts.CheckingAccount;
import entities.accounts.MoneyMarketAccount;
import entities.accounts.SavingsAccount;
import enums.AccountType;

public class Withdrawer implements Runnable{

    private final double amount;
    private final Account account;
    private final Customer customer;

    public Withdrawer(double amount, Account account, Customer customer) {
        super();
        this.amount = amount;
        this.account = account;
        this.customer = customer;
    }

    @Override
    public void run() {
        if (account == null) {
            System.out.println("Invalid account");
            return;
        }
        if (customer == null) {
            System.out.println("Invalid customer");
            return;
        }

        if (account.getAccountType() == AccountType.CHECKING) {
            CheckingAccount checkingAccount = (CheckingAccount) account;
            checkingAccount.withdraw(amount, customer);
        } else if (account.getAccountType() == AccountType.SAVINGS) {
            SavingsAccount savingsAccount = (SavingsAccount) account;
            savingsAccount.withdraw(amount, customer);
        } else if (account.getAccountType() == AccountType.MONEY_MARKET) {
            MoneyMarketAccount moneyMarketAccount = (MoneyMarketAccount) account;
            moneyMarketAccount.withdraw(amount, customer);
        }
    }
}
