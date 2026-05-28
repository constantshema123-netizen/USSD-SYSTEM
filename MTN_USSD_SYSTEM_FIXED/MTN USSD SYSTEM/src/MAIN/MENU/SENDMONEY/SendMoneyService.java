package MAIN.MENU.SENDMONEY;

import MAIN.CUSTOMER.Customer;
import MAIN.PIN.PinService;
import MAIN.TRANSACTION.Transaction;
import MAIN.TRANSACTION.TransactionHistory;

import java.util.Scanner;

public class SendMoneyService {

    private Scanner input;
    private TransactionHistory history;
    private PinService pinService;

    public SendMoneyService(Scanner input, TransactionHistory history, PinService pinService) {
        this.input = input;
        this.history = history;
        this.pinService = pinService;
    }

    public void sendMoney(Customer customer) {

        System.out.println("\n===== SEND MONEY =====");
        System.out.println("1. MTN User");
        System.out.println("2. Other Network");
        System.out.println("3. International Transfer");
        System.out.println("0. Back");
        System.out.print("Choose option: ");

        int choice = input.nextInt();
        if (choice == 0) return;

        String networkLabel = switch (choice) {
            case 1 -> "MTN User";
            case 2 -> "Other Network";
            case 3 -> "International Transfer";
            default -> "Unknown";
        };

        System.out.print("Enter recipient phone number: ");
        String phone = input.next();

        System.out.print("Enter amount (RWF): ");
        double amount = input.nextDouble();

        if (pinService.verify(customer, input)) {
            if (customer.withdraw(amount)) {
                System.out.println("\nTransaction Successful!");
                System.out.println("Sent RWF " + amount + " to " + phone + " (" + networkLabel + ")");
                System.out.printf("New Balance: RWF %.0f%n", customer.getBalance());
                history.add(new Transaction("SEND MONEY", amount, "To " + phone + " via " + networkLabel));
            }
        }
    }
}
