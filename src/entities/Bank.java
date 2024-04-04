package entities;

import entities.accounts.Account;
import entities.accounts.CheckingAccount;
import entities.accounts.MoneyMarketAccount;
import entities.accounts.SavingsAccount;
import enums.AccountType;
import enums.Level;
import transactions.*;
import utils.CommonConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Bank {

    private static Bank instance;
    private final String name;
    private final Map<String, Account> accounts;
    private final Map<String, Customer> customers;
    private final List<Thread> threads;
    private final ThreadGroup bankThreadGroup;
    private final ThreadGroup vipThreadGroup;
    private final ThreadGroup regularThreadGroup;

    public static Bank getInstance(String name) {
        if (instance == null) {
            instance = new Bank(name);
        }
        return instance;
    }
    private Bank(String name) {
        this.name = name;
        accounts = new ConcurrentHashMap<>();
        customers = new ConcurrentHashMap<>();
        threads = new ArrayList<>();
        bankThreadGroup = new ThreadGroup("Bank");
        vipThreadGroup = new ThreadGroup(bankThreadGroup, "VIP");
        regularThreadGroup = new ThreadGroup(bankThreadGroup, "Regular");
        bankThreadGroup.setMaxPriority(CommonConstants.BANK_THREAD_PRIORITY);
        vipThreadGroup.setMaxPriority(CommonConstants.VIP_THREAD_PRIORITY);
        regularThreadGroup.setMaxPriority(CommonConstants.REGULAR_THREAD_PRIORITY);
        System.out.printf("Bank %s created\n", name);
    }

    public void startTransactions() {
        if (threads.isEmpty()) {
            System.out.println("No transactions to start");
            return;
        }

        System.out.println("Starting all transactions");
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        threads.clear();
        System.out.println("All transactions completed");
    }

    public void balanceCheck(String accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            System.out.printf("Account : %s not found\n", accountNumber);
            return;
        }
        Account account = accounts.get(accountNumber);
        Thread balanceCheckThread = new Thread(regularThreadGroup, new BalanceChecker(account));
        threads.add(balanceCheckThread);
    }

    public void balanceCheck(String accountNumber, String customerId) {
        if (!accounts.containsKey(accountNumber)) {
            System.out.printf("Account : %s not found\n", accountNumber);
            return;
        }
        if (!customers.containsKey(customerId)) {
            System.out.printf("Customer : %s not found\n", customerId);
            return;
        }
        Account account = accounts.get(accountNumber);
        Customer customer = customers.get(customerId);
        Thread balanceCheckThread = null;
        if (customer.getLevel() == Level.VIP) {
            balanceCheckThread = new Thread(vipThreadGroup, new BalanceChecker(account));
        } else if (customer.getLevel() == Level.REGULAR) {
            balanceCheckThread = new Thread(regularThreadGroup, new BalanceChecker(account));
        }
        if (balanceCheckThread != null) threads.add(balanceCheckThread);
    }

    public void deposit(String accountNumber, double amount) {
        if (!accounts.containsKey(accountNumber)) {
            System.out.printf("Account : %s not found\n", accountNumber);
            return;
        }
        Account account = accounts.get(accountNumber);
        Thread depositThread = new Thread(regularThreadGroup, new Depositor(amount, account));
        threads.add(depositThread);
    }

    public void deposit(String accountNumber, double amount, String customerId) {
        if (!accounts.containsKey(accountNumber)) {
            System.out.printf("Account : %s not found\n", accountNumber);
            return;
        }
        if (!customers.containsKey(customerId)) {
            System.out.printf("Customer : %s not found\n", customerId);
            return;
        }
        Account account = accounts.get(accountNumber);
        Customer customer = customers.get(customerId);
        Thread depositThread = null;
        if (customer.getLevel() == Level.VIP) {
            depositThread = new Thread(vipThreadGroup, new Depositor(amount, account));
        } else if (customer.getLevel() == Level.REGULAR) {
            depositThread = new Thread(regularThreadGroup, new Depositor(amount, account));
        }
        if (depositThread != null) threads.add(depositThread);
    }

    public void withdraw(String accountNumber, double amount, String customerId) {
        if (!accounts.containsKey(accountNumber)) {
            System.out.printf("Account : %s not found\n", accountNumber);
            return;
        }
        if (!customers.containsKey(customerId)) {
            System.out.printf("Customer : %s not found\n", customerId);
            return;
        }
        Account account = accounts.get(accountNumber);
        Customer customer = customers.get(customerId);
        if (customer == null) {
            System.out.println("Customer not found");
            return;
        }
        Thread withdrawThread = null;
        if (customer.getLevel() == Level.VIP) {
            withdrawThread = new Thread(vipThreadGroup, new Withdrawer(amount, account, customer));
        } else if (customer.getLevel() == Level.REGULAR) {
            withdrawThread = new Thread(regularThreadGroup, new Withdrawer(amount, account, customer));
        }
        if (withdrawThread != null) threads.add(withdrawThread);
    }

    public void applyAnnualFee(String accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            System.out.printf("Account : %s not found\n", accountNumber);
            return;
        }
        Account account = accounts.get(accountNumber);
        Thread annualFeeThread = new Thread(bankThreadGroup, new AnnualFeeCalculator(account));
        threads.add(annualFeeThread);
    }

    public void applyAnnualFeeForAllAccounts() {
        for (Account account : accounts.values()) {
            Thread annualFeeThread = new Thread(bankThreadGroup, new AnnualFeeCalculator(account));
            threads.add(annualFeeThread);
        }
    }

    public void applyInterest(String accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            System.out.printf("Account : %s not found\n", accountNumber);
            return;
        }
        if (accounts.get(accountNumber).getAccountType() == AccountType.CHECKING) {
            System.out.printf("Account : %s is a checking account\n", accountNumber);
            return;
        }
        Account account = accounts.get(accountNumber);
        Thread interestThread = new Thread(bankThreadGroup, new InterestCalculator(account));
        threads.add(interestThread);
    }

    public void applyInterestForAllAccounts() {
        for (Account account : accounts.values()) {
            if (account.getAccountType() == AccountType.CHECKING) {
                continue;
            }
            Thread interestThread = new Thread(bankThreadGroup, new InterestCalculator(account));
            threads.add(interestThread);
        }
    }

    public void applyTax(String accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            System.out.printf("Account : %s not found\n", accountNumber);
            return;
        }
        if (accounts.get(accountNumber).getAccountType() == AccountType.CHECKING) {
            System.out.printf("Account : %s is a checking account\n", accountNumber);
            return;
        }
        Account account = accounts.get(accountNumber);
        Thread taxThread = new Thread(bankThreadGroup, new TaxCalculator(account));
        threads.add(taxThread);
    }

    public void applyTaxForAllAccounts() {
        for (Account account : accounts.values()) {
            if (account.getAccountType() == AccountType.CHECKING) {
                continue;
            }
            Thread taxThread = new Thread(bankThreadGroup, new TaxCalculator(account));
            threads.add(taxThread);
        }
    }

    public void applyOverdraftFee(String accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            System.out.printf("Account : %s not found\n", accountNumber);
            return;
        }
        if (accounts.get(accountNumber).getAccountType() != AccountType.CHECKING) {
            System.out.printf("Account : %s is not a checking account\n", accountNumber);
            return;
        }
        Account account = accounts.get(accountNumber);
        Thread overdraftFeeThread = new Thread(bankThreadGroup, new OverdraftFeeCalculator(account));
        threads.add(overdraftFeeThread);
    }

    public void applyOverdraftFeeForAllAccounts() {
        for (Account account : accounts.values()) {
            if (account.getAccountType() != AccountType.CHECKING) {
                continue;
            }
            Thread overdraftFeeThread = new Thread(bankThreadGroup, new OverdraftFeeCalculator(account));
            threads.add(overdraftFeeThread);
        }
    }

    public void addAccount(Account account) {
        System.out.printf("Added %s account : %s\n", account.getAccountType().getStringValue(), account.getAccountNumber());
        accounts.put(account.getAccountNumber(), account);
    }

    public void addCustomer(Customer customer) {
        System.out.printf("Added %s %s to bank\n", customer.getFirstName(), customer.getLastName());
        customers.put(customer.getCustomerID(), customer);
    }



    public void removeAccount(String accountNumber) {
        System.out.printf("Removed %s account : %s\n", accounts.get(accountNumber).getAccountType().getStringValue(), accountNumber);
        accounts.remove(accountNumber);
    }

    public SavingsAccount getSavingsAccount(String accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            System.out.printf("Savings account : %s not found\n", accountNumber);
            return null;
        }
        if (accounts.get(accountNumber).getAccountType() != AccountType.SAVINGS) {
            System.out.printf("Account : %s is not a savings account\n", accountNumber);
            return null;
        }
        return (SavingsAccount) accounts.get(accountNumber);
    }

    public CheckingAccount getCheckingAccount(String accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            System.out.printf("Checking account : %s not found\n", accountNumber);
            return null;
        }
        if (accounts.get(accountNumber).getAccountType() != AccountType.CHECKING) {
            System.out.printf("Account : %s is not a checking account\n", accountNumber);
            return null;
        }
        return (CheckingAccount) accounts.get(accountNumber);
    }

    public MoneyMarketAccount getMoneyMarketAccount(String accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            System.out.printf("Money market account : %s not found\n", accountNumber);
            return null;
        }
        if (accounts.get(accountNumber).getAccountType() != AccountType.MONEY_MARKET) {
            System.out.printf("Account : %s is not a money market account\n", accountNumber);
            return null;
        }
        return (MoneyMarketAccount) accounts.get(accountNumber);
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public boolean hasAccount(String accountNumber) {
        return accounts.containsKey(accountNumber);
    }

    public Customer getCustomer(String customerID) {
        return customers.get(customerID);
    }

    public Map<String, Customer> getCustomers() {
        return customers;
    }

    public boolean hasCustomer(String customerID) {
        return customers.containsKey(customerID);
    }

    public String getName() {
        return name;
    }
}
