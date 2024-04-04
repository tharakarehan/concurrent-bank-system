package transactions;

import entities.accounts.Account;

public class AnnualFeeCalculator implements Runnable{

    private final Account account;

    public AnnualFeeCalculator(Account account) {
        super();
        this.account = account;
    }
    @Override
    public void run() {
        if (account == null) {
            System.out.println("Invalid account");
            return;
        }
        account.applyAnnualFee();
    }
}
