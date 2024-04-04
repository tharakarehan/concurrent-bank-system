package transactions;

import entities.accounts.Account;
import entities.accounts.MoneyMarketAccount;
import entities.accounts.SavingsAccount;
import enums.AccountType;

public class TaxCalculator implements Runnable{

    private final Account account;

    public TaxCalculator(Account account) {
        super();
        this.account = account;
    }
    @Override
    public void run() {
        if (account == null || account.getAccountType() == AccountType.CHECKING) {
            System.out.println("Invalid account");
            return;
        }

        if (account.getAccountType() == AccountType.SAVINGS) {
            SavingsAccount savingsAccount = (SavingsAccount) account;
            savingsAccount.applyTax();
        } else if (account.getAccountType() == AccountType.MONEY_MARKET) {
            MoneyMarketAccount moneyMarketAccount = (MoneyMarketAccount) account;
            moneyMarketAccount.applyTax();
        }
    }
}
