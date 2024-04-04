package transactions;

import entities.accounts.Account;
import entities.accounts.CheckingAccount;
import enums.AccountType;

public class OverdraftFeeCalculator implements Runnable{

    private final Account account;

    public OverdraftFeeCalculator(Account account) {
        super();
        this.account = account;
    }

    @Override
    public void run() {
        if (account == null || account.getAccountType() != AccountType.CHECKING) {
            System.out.println("Invalid account");
            return;
        }
        CheckingAccount checkingAccount = (CheckingAccount) account;
        checkingAccount.deductOverdraftFee();
    }
}
