package MAIN.BANKSERVICE;

import MAIN.CUSTOMER.Customer;
import MAIN.PIN.PinService;
import MAIN.TRANSACTION.Transaction;
import MAIN.TRANSACTION.TransactionHistory;

import java.util.Scanner;

public class BankService {

    private Scanner input;
    private TransactionHistory history;
    private PinService pinService;

    private static final String[] BANKS = {
            "Equity Bank", "Bank of Kigali", "I&M Bank", "BPR Bank", "Ecobank"
    };

    public BankService(Scanner input, TransactionHistory history, PinService pinService) {
        this.input = input;
        this.history = history;
        this.pinService = pinService;
    }

    public void bank(Customer customer) {

        System.out.println("\n===== BANK SERVICES =====");
        System.out.println("1. Send to Bank");
        System.out.println("2. Save Money");
        System.out.println("3. Check Balance");
        System.out.println("4. ATM Withdrawal");
        System.out.println("0. Back");
        System.out.print("Choose option: ");

        int choice = input.nextInt();

        switch (choice) {
            case 1 -> bankTransfer(customer);
            case 2 -> saveMoney(customer);
            case 3 -> checkBalance(customer);
            case 4 -> atmWithdrawal(customer);
            case 0 -> {}
            default -> System.out.println("Invalid option.");
        }
    }

    private void bankTransfer(Customer customer) {

        System.out.println("\n===== CHOOSE BANK =====");
        for (int i = 0; i < BANKS.length; i++) {
            System.out.println((i + 1) + ". " + BANKS[i]);
        }
        System.out.print("Choose bank: ");
        int bankChoice = input.nextInt();

        if (bankChoice < 1 || bankChoice > BANKS.length) {
            System.out.println("Invalid bank."); return;
        }

        String selectedBank = BANKS[bankChoice - 1];
        System.out.println("Selected: " + selectedBank);

        System.out.print("Enter bank account number: ");
        String acc = input.next();

        System.out.print("Enter amount (RWF): ");
        double amount = input.nextDouble();

        if (pinService.verify(customer, input)) {
            if (customer.withdraw(amount)) {
                System.out.println("\nTransfer Successful!");
                System.out.println("Bank: " + selectedBank);
                System.out.println("Account: " + acc);
                System.out.printf("Amount: RWF %.0f%n", amount);
                System.out.printf("New Balance: RWF %.0f%n", customer.getBalance());
                history.add(new Transaction("BANK TRANSFER", amount, selectedBank + " acc:" + acc));
            }
        }
    }

    private void saveMoney(Customer customer) {

        System.out.print("Enter amount to save (RWF): ");
        double amount = input.nextDouble();

        if (pinService.verify(customer, input)) {
            if (customer.withdraw(amount)) {
                System.out.println("\nRWF " + amount + " saved successfully!");
                history.add(new Transaction("SAVINGS", amount, "MoMo Savings"));
            }
        }
    }

    private void checkBalance(Customer customer) {
        if (pinService.verify(customer, input)) {
            System.out.printf("%nYour MoMo Balance: RWF %.0f%n", customer.getBalance());
        }
    }

    private void atmWithdrawal(Customer customer) {

        System.out.print("Enter ATM withdrawal amount (RWF): ");
        double amount = input.nextDouble();

        if (pinService.verify(customer, input)) {
            if (customer.withdraw(amount)) {
                System.out.println("\nATM withdrawal successful!");
                System.out.printf("Withdrawn: RWF %.0f%n", amount);
                System.out.printf("New Balance: RWF %.0f%n", customer.getBalance());
                history.add(new Transaction("ATM", amount, "ATM Withdrawal"));
            }
        }
    }
}
