import entities.Bank;
import entities.Customer;
import entities.accounts.CheckingAccount;
import entities.accounts.MoneyMarketAccount;
import entities.accounts.SavingsAccount;
import enums.Level;

public class Client {
    public static void main(String[] args) {

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
        CheckingAccount checkingAccount1 = new CheckingAccount("CHK001", 1000, 100);
        CheckingAccount checkingAccount2 = new CheckingAccount("CHK002", 1000, 100);
        SavingsAccount savingsAccount1 = new SavingsAccount("SAV001", 1000);
        SavingsAccount savingsAccount2 = new SavingsAccount("SAV002", 1000);
        MoneyMarketAccount moneyMarketAccount1 = new MoneyMarketAccount("MMK001", 1000, 5);
        MoneyMarketAccount moneyMarketAccount2 = new MoneyMarketAccount("MMK002", 1000, 5);

        checkingAccount1.addCustomer(customer1);
        checkingAccount2.addCustomer(customer2);
        savingsAccount1.addCustomer(customer3);
        savingsAccount2.addCustomer(customer4);
        moneyMarketAccount1.addCustomer(customer5);
        moneyMarketAccount2.addCustomer(customer6);
        checkingAccount1.addCustomer(customer7);
        checkingAccount2.addCustomer(customer8);
        savingsAccount1.addCustomer(customer9);
        savingsAccount2.addCustomer(customer10);

        bank.addAccount(checkingAccount1);
        bank.addAccount(checkingAccount2);
        bank.addAccount(savingsAccount1);
        bank.addAccount(savingsAccount2);
        bank.addAccount(moneyMarketAccount1);
        bank.addAccount(moneyMarketAccount2);

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
        bank.applyAnnualFeeForAllAccounts();
        bank.applyInterestForAllAccounts();
        bank.applyOverdraftFeeForAllAccounts();

        bank.startTransactions();

        bank.applyTaxForAllAccounts();

        bank.startTransactions();

        bank.balanceCheck("CHK001");
        bank.balanceCheck("CHK002");
        bank.balanceCheck("SAV001");
        bank.balanceCheck("SAV002");
        bank.balanceCheck("MMK001");
        bank.balanceCheck("MMK002");

        bank.startTransactions();

    }
}