package MAIN.MENU.BUY;

import MAIN.CUSTOMER.Customer;
import MAIN.PIN.PinService;
import MAIN.TRANSACTION.Transaction;
import MAIN.TRANSACTION.TransactionHistory;

import java.util.Scanner;

public class BuyService {

    private Scanner input;
    private TransactionHistory history;
    private PinService pinService;

    public BuyService(Scanner input, TransactionHistory history, PinService pinService) {
        this.input = input;
        this.history = history;
        this.pinService = pinService;
    }

    public void buy(Customer customer) {

        System.out.println("\n===== BUY =====");
        System.out.println("1. Internet Pack");
        System.out.println("2. Airtime");
        System.out.println("3. Power Bills");
        System.out.println("0. Back");
        System.out.print("Choose option: ");

        int choice = input.nextInt();

        switch (choice) {
            case 1 -> buyInternet(customer);
            case 2 -> buyAirtime(customer);
            case 3 -> payPower(customer);
            case 0 -> {}
            default -> System.out.println("Invalid option.");
        }
    }

    private void buyInternet(Customer customer) {

        System.out.println("\n===== INTERNET PACKS =====");
        System.out.println("1. Daily Pack   - RWF 500   (1GB)");
        System.out.println("2. Weekly Pack  - RWF 2,000 (5GB)");
        System.out.println("3. Monthly Pack - RWF 5,000 (20GB)");
        System.out.println("0. Back");
        System.out.print("Choose pack: ");

        int pack = input.nextInt();
        if (pack == 0) return;

        double price = switch (pack) {
            case 1 -> 500;
            case 2 -> 2000;
            case 3 -> 5000;
            default -> -1;
        };

        String label = switch (pack) {
            case 1 -> "Daily Pack (1GB)";
            case 2 -> "Weekly Pack (5GB)";
            case 3 -> "Monthly Pack (20GB)";
            default -> "Unknown";
        };

        if (price == -1) { System.out.println("Invalid pack."); return; }

        System.out.println("Selected: " + label + " — RWF " + price);

        if (pinService.verify(customer, input)) {
            if (customer.withdraw(price)) {          // FIX: was Customer.withdraw (static call)
                System.out.println("\nInternet pack activated successfully!");
                System.out.printf("New Balance: RWF %.0f%n", customer.getBalance()); // FIX: was Customer.getBalance
                history.add(new Transaction("BUY INTERNET", price, label));
            }
        }
    }

    private void buyAirtime(Customer customer) {

        System.out.print("Enter airtime amount (RWF): ");
        double amount = input.nextDouble();

        if (pinService.verify(customer, input)) {
            if (customer.withdraw(amount)) {
                System.out.println("\nAirtime purchased successfully! RWF " + amount);
                System.out.printf("New Balance: RWF %.0f%n", customer.getBalance());
                history.add(new Transaction("BUY AIRTIME", amount, "Airtime top-up"));
            }
        }
    }

    private void payPower(Customer customer) {

        System.out.print("Enter meter number: ");
        String meter = input.next();

        System.out.print("Enter amount (RWF): ");
        double amount = input.nextDouble();

        if (pinService.verify(customer, input)) {
            if (customer.withdraw(amount)) {
                System.out.println("\nPower bill paid! Meter: " + meter);
                System.out.printf("New Balance: RWF %.0f%n", customer.getBalance());
                history.add(new Transaction("POWER BILL", amount, "Meter " + meter));
            }
        }
    }
}
