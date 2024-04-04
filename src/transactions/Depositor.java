package transactions;

import entities.accounts.Account;

public class Depositor implements Runnable {
    private final double amount;
    private final Account account;

    public Depositor(double amount, Account account) {
        super();
        this.amount = amount;
        this.account = account;
    }

    @Override
    public void run() {
        if (account == null) {
            System.out.println("Invalid account");
            return;
        }
        account.deposit(amount);
    }
}
