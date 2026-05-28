package MAIN.LOANSERVICE;

import MAIN.CUSTOMER.Customer;
import MAIN.PIN.PinService;
import MAIN.TRANSACTION.Transaction;
import MAIN.TRANSACTION.TransactionHistory;

import java.util.Scanner;

public class LoanSavingsService {

    private Scanner input;
    private TransactionHistory history;
    private PinService pinService;

    private static final double LOAN_ELIGIBILITY_THRESHOLD = 5000;

    public LoanSavingsService(Scanner input, TransactionHistory history, PinService pinService) {
        this.input = input;
        this.history = history;
        this.pinService = pinService;
    }

    public void loanAndSavings(Customer customer) {

        System.out.println("\n===== LOAN & SAVINGS =====");
        System.out.println("1. Loan");
        System.out.println("2. Savings");
        System.out.println("0. Back");
        System.out.print("Choose option: ");

        int choice = input.nextInt();

        switch (choice) {
            case 1 -> loan(customer);
            case 2 -> savings(customer);
            case 0 -> {}
            default -> System.out.println("Invalid option.");
        }
    }

    private void loan(Customer customer) {

        System.out.println("\n===== LOAN =====");
        System.out.print("Enter loan amount (RWF): ");
        double amount = input.nextDouble();

        if (pinService.verify(customer, input)) {

            System.out.println("\nChecking eligibility...");

            if (customer.getBalance() >= LOAN_ELIGIBILITY_THRESHOLD) {
                customer.deposit(amount);
                System.out.println("Loan APPROVED!");
                System.out.printf("RWF %.0f credited to your account.%n", amount);
                System.out.printf("New Balance: RWF %.0f%n", customer.getBalance());
                history.add(new Transaction("LOAN", amount, "Loan credited"));
            } else {
                System.out.println("Sorry, you are NOT eligible for a loan.");
                System.out.println("Minimum balance required: RWF " + LOAN_ELIGIBILITY_THRESHOLD);
                System.out.printf("Your current balance: RWF %.0f%n", customer.getBalance());
            }
        }
    }

    private void savings(Customer customer) {

        System.out.println("\n===== SAVINGS =====");
        System.out.print("Enter amount to deposit into savings (RWF): ");
        double amount = input.nextDouble();

        if (pinService.verify(customer, input)) {
            if (customer.withdraw(amount)) {
                System.out.println("\nDeposit Successful!");
                System.out.printf("RWF %.0f saved.%n", amount);
                System.out.printf("New Balance: RWF %.0f%n", customer.getBalance());
                history.add(new Transaction("SAVINGS DEPOSIT", amount, "MoMo Savings"));
            }
        }
    }
}
