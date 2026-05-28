package MAIN.MENU.PAY;

import MAIN.CUSTOMER.Customer;
import MAIN.PIN.PinService;
import MAIN.TRANSACTION.Transaction;
import MAIN.TRANSACTION.TransactionHistory;

import java.util.Scanner;

public class PayService {

    private Scanner input;
    private TransactionHistory history;
    private PinService pinService;

    public PayService(Scanner input, TransactionHistory history, PinService pinService) {
        this.input = input;
        this.history = history;
        this.pinService = pinService;
    }

    public void pay(Customer customer) {

        System.out.println("\n===== PAY =====");
        System.out.println("1. Water Bills");
        System.out.println("2. School Fees");
        System.out.println("3. TV Subscription");
        System.out.println("0. Back");
        System.out.print("Choose option: ");

        int choice = input.nextInt();

        String label = switch (choice) {
            case 1 -> "Water Bills";
            case 2 -> "School Fees";
            case 3 -> "TV Subscription";
            case 0 -> null;
            default -> null;
        };

        if (label == null) return;

        System.out.print("Enter reference number: ");
        String ref = input.next();

        System.out.print("Enter amount (RWF): ");
        double amount = input.nextDouble();

        if (pinService.verify(customer, input)) {
            if (customer.withdraw(amount)) {
                System.out.println("\n" + label + " paid successfully!");
                System.out.println("Reference: " + ref);
                System.out.printf("New Balance: RWF %.0f%n", customer.getBalance());
                history.add(new Transaction("PAY", amount, label + " ref:" + ref));
            }
        }
    }
}
