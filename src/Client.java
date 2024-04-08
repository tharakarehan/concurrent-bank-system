import entities.Bank;
import entities.Customer;
import entities.accounts.CheckingAccount;
import entities.accounts.MoneyMarketAccount;
import entities.accounts.SavingsAccount;
import enums.Level;
import utils.CommonConstants;

public class Client {
    public static void main(String[] args) {

        // Create new bank
        Bank bank = Bank.getInstance("Bank of America");

        // Create new customers
        Customer customer1 = new Customer("VIP001", "VIP001", "Doe", "1234567890", "123 Main St", Level.VIP);
        Customer customer2 = new Customer("REG001", "REG001", "Doe",  "1234567890", "123 Main St", Level.REGULAR);
        Customer customer3 = new Customer("VIP002", "VIP002", "Doe",  "1234567890", "123 Main St", Level.VIP);
        Customer customer4 = new Customer("REG002", "REG002", "Doe",  "1234567890", "123 Main St", Level.REGULAR);
        Customer customer5 = new Customer("VIP003", "VIP003", "Doe",  "1234567890", "123 Main St", Level.VIP);
        Customer customer6 = new Customer("REG003", "REG003", "Doe",  "1234567890", "123 Main St", Level.REGULAR);
        Customer customer7 = new Customer("VIP004", "VIP004", "Doe",  "1234567890", "123 Main St", Level.VIP);
        Customer customer8 = new Customer("REG004", "REG004", "Doe",  "1234567890", "123 Main St", Level.REGULAR);
        Customer customer9 = new Customer("VIP005", "VIP005", "Doe",  "1234567890", "123 Main St", Level.VIP);
        Customer customer10 = new Customer("REG005", "REG005", "Doe",  "1234567890", "123 Main St", Level.REGULAR);

        // Add customers to bank
        bank.addCustomer(customer1);
        bank.addCustomer(customer2);
        bank.addCustomer(customer3);
        bank.addCustomer(customer4);
        bank.addCustomer(customer5);
        bank.addCustomer(customer6);
        bank.addCustomer(customer7);
        bank.addCustomer(customer8);
        bank.addCustomer(customer9);
        bank.addCustomer(customer10);

        // Create new accounts
        CheckingAccount checkingAccount1 = new CheckingAccount("CHK001", CommonConstants.INITIAL_ACCOUNT_BALANCE, CommonConstants.INITIAL_OVERDRAFT_LIMIT);
        CheckingAccount checkingAccount2 = new CheckingAccount("CHK002", CommonConstants.INITIAL_ACCOUNT_BALANCE, CommonConstants.INITIAL_OVERDRAFT_LIMIT);
        SavingsAccount savingsAccount1 = new SavingsAccount("SAV001", CommonConstants.INITIAL_ACCOUNT_BALANCE);
        SavingsAccount savingsAccount2 = new SavingsAccount("SAV002", CommonConstants.INITIAL_ACCOUNT_BALANCE);
        MoneyMarketAccount moneyMarketAccount1 = new MoneyMarketAccount("MMK001", CommonConstants.INITIAL_ACCOUNT_BALANCE, CommonConstants.INITIAL_TRANSACTIONS_REMAINING);
        MoneyMarketAccount moneyMarketAccount2 = new MoneyMarketAccount("MMK002", CommonConstants.INITIAL_ACCOUNT_BALANCE, CommonConstants.INITIAL_TRANSACTIONS_REMAINING);

        // Add customers to accounts
        checkingAccount1.addCustomer(customer1);
        checkingAccount1.addCustomer(customer2);
        checkingAccount1.addCustomer(customer3);
        checkingAccount1.addCustomer(customer4);
        checkingAccount1.addCustomer(customer5);
        checkingAccount1.addCustomer(customer6);
        checkingAccount1.addCustomer(customer7);
        checkingAccount1.addCustomer(customer8);
        checkingAccount1.addCustomer(customer9);
        checkingAccount1.addCustomer(customer10);

        savingsAccount1.addCustomer(customer1);
        savingsAccount1.addCustomer(customer2);
        savingsAccount1.addCustomer(customer3);
        savingsAccount1.addCustomer(customer4);
        savingsAccount1.addCustomer(customer5);
        savingsAccount1.addCustomer(customer6);
        savingsAccount1.addCustomer(customer7);
        savingsAccount1.addCustomer(customer8);
        savingsAccount1.addCustomer(customer9);
        savingsAccount1.addCustomer(customer10);

        moneyMarketAccount1.addCustomer(customer1);
        moneyMarketAccount1.addCustomer(customer2);
        moneyMarketAccount1.addCustomer(customer3);
        moneyMarketAccount1.addCustomer(customer4);
        moneyMarketAccount1.addCustomer(customer5);
        moneyMarketAccount1.addCustomer(customer6);
        moneyMarketAccount1.addCustomer(customer7);
        moneyMarketAccount1.addCustomer(customer8);
        moneyMarketAccount1.addCustomer(customer9);
        moneyMarketAccount1.addCustomer(customer10);

        checkingAccount2.addCustomer(customer2);
        savingsAccount2.addCustomer(customer4);
        moneyMarketAccount2.addCustomer(customer6);
        checkingAccount2.addCustomer(customer8);
        savingsAccount2.addCustomer(customer10);
        moneyMarketAccount2.addCustomer(customer1);

        // Add accounts to bank
        bank.addAccount(checkingAccount1);
        bank.addAccount(checkingAccount2);
        bank.addAccount(savingsAccount1);
        bank.addAccount(savingsAccount2);
        bank.addAccount(moneyMarketAccount1);
        bank.addAccount(moneyMarketAccount2);

        // Run Tests

        savingsAccountWithdrawalPassTest(bank);
        savingsAccountWithdrawalFailTest(bank);
        moneyMarketAccountWithdrawalPassTest(bank);
        moneyMarketAccountWithdrawalFailTest(bank);
        moneyMarketAccountTransactionRemainFailTest(bank);
        checkingAccountWithdrawalPassTest(bank);
        checkingAccountWithdrawalPassOverdraftTest(bank);
        checkingAccountWithdrawalFailTest(bank);
        savingAccountWithdrawalFailCustomerTest(bank);
        randomTest01(bank);
    }

    public static void savingsAccountWithdrawalPassTest(Bank bank) {

        System.out.println("\n################################################ Savings Account Withdrawal Pass Test ################################################\n");
        // Create new transactions
        bank.deposit("SAV001", 100);
        bank.withdraw("SAV001", 50, "VIP001");
        bank.deposit("SAV001", 100);
        bank.withdraw("SAV001", 50, "REG001");
        bank.deposit("SAV001", 100);
        bank.withdraw("SAV001", 50, "VIP002");
        bank.deposit("SAV001", 100);
        bank.withdraw("SAV001", 50, "REG002");
        bank.deposit("SAV001", 100);
        bank.withdraw("SAV001", 50, "VIP003");
        bank.deposit("SAV001", 100);
        bank.withdraw("SAV001", 50, "REG003");

        bank.startTransactions();
        checkAccountBalances(bank);

        initiateBankTransactions(bank);
        checkAccountBalances(bank);

        bank.resetAccounts();
    }

    public static void savingsAccountWithdrawalFailTest(Bank bank) {

        System.out.println("\n################################################ Savings Account Withdrawal Pass Test ################################################\n");
        // Create new transactions
        bank.deposit("SAV001", 100);
        bank.withdraw("SAV001", 500, "VIP001");
        bank.deposit("SAV001", 100);
        bank.withdraw("SAV001", 500, "REG001");
        bank.deposit("SAV001", 100);
        bank.withdraw("SAV001", 500, "VIP002");
        bank.deposit("SAV001", 100);
        bank.withdraw("SAV001", 500, "REG002");
        bank.deposit("SAV001", 100);
        bank.withdraw("SAV001", 500, "VIP003");
        bank.deposit("SAV001", 100);
        bank.withdraw("SAV001", 500, "REG003");

        bank.startTransactions();
        checkAccountBalances(bank);

        initiateBankTransactions(bank);
        checkAccountBalances(bank);

        bank.resetAccounts();
    }

    public static void moneyMarketAccountWithdrawalPassTest(Bank bank) {

        System.out.println("\n################################################ Money Market Account Withdrawal Pass Test ################################################\n");
        // Create new transactions

        bank.deposit("MMK001", 100);
        bank.deposit("MMK001", 100);
        bank.deposit("MMK001", 100);
        bank.withdraw("MMK001", 500, "VIP001");
        bank.withdraw("MMK001", 500, "REG001");
        bank.deposit("MMK001", 100);
        bank.deposit("MMK001", 100);
        bank.deposit("MMK001", 100);

        bank.startTransactions();
        checkAccountBalances(bank);

        initiateBankTransactions(bank);
        checkAccountBalances(bank);

        bank.resetAccounts();
    }

    public static void moneyMarketAccountWithdrawalFailTest(Bank bank) {

        System.out.println("\n################################################ Money Market Account Withdrawal Fail Test ################################################\n");
        // Create new transactions

        bank.deposit("MMK001", 100);
        bank.deposit("MMK001", 100);
        bank.deposit("MMK001", 100);
        bank.withdraw("MMK001", 500, "VIP001");
        bank.withdraw("MMK001", 500, "REG001");
        bank.withdraw("MMK001", 500, "VIP002");
        bank.withdraw("MMK001", 500, "REG002");
        bank.withdraw("MMK001", 500, "VIP003");
        bank.deposit("MMK001", 100);
        bank.deposit("MMK001", 100);
        bank.deposit("MMK001", 100);

        bank.startTransactions();
        checkAccountBalances(bank);

        initiateBankTransactions(bank);
        checkAccountBalances(bank);

        bank.resetAccounts();
    }

    public static void moneyMarketAccountTransactionRemainFailTest(Bank bank) {

        System.out.println("\n################################################ Money Market Account Transaction Remain Fail Test ################################################\n");
        // Create new transactions

        bank.withdraw("MMK001", 50, "VIP001");
        bank.withdraw("MMK001", 50, "REG001");
        bank.withdraw("MMK001", 50, "VIP002");
        bank.withdraw("MMK001", 50, "REG002");
        bank.withdraw("MMK001", 50, "VIP003");
        bank.withdraw("MMK001", 50, "REG003");

        bank.startTransactions();
        checkAccountBalances(bank);

        initiateBankTransactions(bank);
        checkAccountBalances(bank);

        bank.resetAccounts();
    }

    public static void checkingAccountWithdrawalPassTest(Bank bank) {

        System.out.println("\n################################################ Checking Account Withdrawal Pass Test ################################################\n");
        // Create new transactions

        bank.withdraw("CHK001", 50, "VIP001");
        bank.withdraw("CHK001", 50, "REG001");
        bank.withdraw("CHK001", 50, "VIP002");
        bank.withdraw("CHK001", 50, "REG002");
        bank.withdraw("CHK001", 50, "VIP003");
        bank.withdraw("CHK001", 50, "REG003");

        bank.startTransactions();
        checkAccountBalances(bank);

        initiateBankTransactions(bank);
        checkAccountBalances(bank);

        bank.resetAccounts();
    }

    public static void checkingAccountWithdrawalPassOverdraftTest(Bank bank) {

        System.out.println("\n################################################ Checking Account Withdrawal Pass Overdraft Test ################################################\n");
        // Create new transactions

        bank.withdraw("CHK001", 500, "VIP001");
        bank.withdraw("CHK001", 500, "REG001");
        bank.withdraw("CHK001", 50, "VIP002");

        bank.startTransactions();
        checkAccountBalances(bank);

        initiateBankTransactions(bank);
        checkAccountBalances(bank);

        bank.resetAccounts();
    }

    public static void checkingAccountWithdrawalFailTest(Bank bank) {

        System.out.println("\n################################################ Checking Account Withdrawal Fail Test ################################################\n");
        // Create new transactions

        bank.withdraw("CHK001", 500, "VIP001");
        bank.withdraw("CHK001", 500, "REG001");
        bank.withdraw("CHK001", 500, "VIP002");

        bank.startTransactions();
        checkAccountBalances(bank);

        initiateBankTransactions(bank);
        checkAccountBalances(bank);

        bank.resetAccounts();
    }

    public static void savingAccountWithdrawalFailCustomerTest(Bank bank) {

        System.out.println("\n################################################ Saving Account Withdrawal Fail Customer Test ################################################\n");
        // Create new transactions

        bank.withdraw("SAV002", 500, "VIP001");

        bank.startTransactions();
        checkAccountBalances(bank);
    }

    public static void randomTest01(Bank bank){

        System.out.println("\n################################################ Random Test 01 ################################################\n");
        // Create new transactions
        bank.deposit("CHK001", 100);
        bank.withdraw("CHK001", 50, "VIP001");
        bank.deposit("CHK002", 100);
        bank.withdraw("CHK002", 50, "REG001");
        bank.deposit("SAV001", 100);
        bank.withdraw("SAV001", 50, "VIP002");
        bank.deposit("SAV002", 100);
        bank.withdraw("SAV002", 50, "REG002");
        bank.deposit("MMK001", 100);
        bank.withdraw("MMK001", 50, "VIP003");
        bank.deposit("MMK002", 100);
        bank.withdraw("MMK002", 50, "REG003");

        bank.startTransactions();
        checkAccountBalances(bank);

        initiateBankTransactions(bank);
        checkAccountBalances(bank);

        bank.resetAccounts();
    }

    public static void initiateBankTransactions(Bank bank) {
        // Apply fees and overdrawn fees
        bank.applyAnnualFeeForAllAccounts();
        bank.applyOverdraftFeeForAllAccounts();
        bank.startTransactions();

        // Apply interest
        bank.applyInterestForAllAccounts();
        bank.startTransactions();

        // Apply tax
        bank.applyTaxForAllAccounts();
        bank.startTransactions();
    }

    public static void checkAccountBalances(Bank bank) {
        bank.balanceCheck("CHK001");
        bank.balanceCheck("CHK002");
        bank.balanceCheck("SAV001");
        bank.balanceCheck("SAV002");
        bank.balanceCheck("MMK001");
        bank.balanceCheck("MMK002");
        bank.startTransactions();
    }
}