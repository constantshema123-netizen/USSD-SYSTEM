package MAIN.MENU.MOMOPAY;

import MAIN.CUSTOMER.Customer;
import MAIN.PIN.PinService;
import MAIN.TRANSACTION.Transaction;
import MAIN.TRANSACTION.TransactionHistory;

import java.util.Scanner;

public class MomoPayService {

    private Scanner input;
    private TransactionHistory history;
    private PinService pinService;

    public MomoPayService(Scanner input, TransactionHistory history, PinService pinService) {
        this.input = input;
        this.history = history;
        this.pinService = pinService;
    }

    public void momoPay(Customer customer) {

        System.out.println("\n===== MOMO PAY =====");
        System.out.println("1. Pay Merchant (Food, Utilities, Shopping & more)");
        System.out.println("0. Back");
        System.out.print("Choose option: ");

        int choice = input.nextInt();
        if (choice == 0) return;

        System.out.print("Enter merchant name or MoMo Pay code: ");
        String merchant = input.next();

        System.out.print("Enter amount (RWF): ");
        double amount = input.nextDouble();

        System.out.println("Merchant: " + merchant);
        System.out.printf("Amount:   RWF %.0f%n", amount);
        System.out.print("Confirm? (1=Yes / 0=No): ");
        int confirm = input.nextInt();

        if (confirm != 1) {
            System.out.println("Payment cancelled.");
            return;
        }

        if (pinService.verify(customer, input)) {
            if (customer.withdraw(amount)) {
                System.out.println("\nPayment Successful!");
                System.out.println("Paid RWF " + amount + " to " + merchant);
                System.out.printf("New Balance: RWF %.0f%n", customer.getBalance());
                history.add(new Transaction("MOMO PAY", amount, "Paid to " + merchant));
            }
        }
    }
}
