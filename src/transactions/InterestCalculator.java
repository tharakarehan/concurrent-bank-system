package transactions;

import entities.accounts.Account;
import entities.accounts.MoneyMarketAccount;
import entities.accounts.SavingsAccount;
import enums.AccountType;

public class InterestCalculator implements Runnable{
    private final Account account;

    public InterestCalculator(Account account) {
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
            savingsAccount.applyInterest();
        } else if (account.getAccountType() == AccountType.MONEY_MARKET) {
            MoneyMarketAccount moneyMarketAccount = (MoneyMarketAccount) account;
            moneyMarketAccount.applyInterest();
        }
    }
}
