package transactions;

import entities.accounts.Account;

public class BalanceChecker implements Runnable {

    private final Account account;

    public BalanceChecker(Account account) {
        super();
        this.account = account;
    }
    @Override
    public void run() {
        if (account == null) {
            System.out.println("Invalid account");
            return;
        }
        account.getBalance();
    }
}
