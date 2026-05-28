package MAIN.MENU;

import MAIN.BANKSERVICE.BankService;
import MAIN.CUSTOMER.Customer;
import MAIN.LOANSERVICE.LoanSavingsService;
import MAIN.MENU.BUY.BuyService;
import MAIN.MENU.MOMOPAY.MomoPayService;
import MAIN.MENU.PAY.PayService;
import MAIN.MENU.SENDMONEY.SendMoneyService;
import MAIN.PIN.PinService;
import MAIN.TRANSACTION.TransactionHistory;

import java.util.Scanner;

public class Menu {

    private Scanner input = new Scanner(System.in);
    private TransactionHistory history = new TransactionHistory();
    private PinService pinService = new PinService();

    private Customer customer;

    private SendMoneyService sendService;
    private BuyService buyService;
    private PayService payService;
    private BankService bankService;
    private LoanSavingsService loanService;
    private MomoPayService momoService;

    public Menu() {
        System.out.println("===== MTN MoMo Setup =====");
        System.out.print("Enter your name: ");
        String name = input.nextLine();

        System.out.print("Enter your phone number: ");
        String phone = input.nextLine();

        System.out.print("Enter starting balance (RWF): ");
        double balance = input.nextDouble();

        System.out.print("Set your PIN: ");
        int pin = input.nextInt();
        input.nextLine(); // clear buffer

        customer = new Customer(name, phone, balance, pin);
        System.out.println("\nAccount created! Welcome, " + name + ".\n");

        sendService = new SendMoneyService(input, history, pinService);
        buyService  = new BuyService(input, history, pinService);
        payService  = new PayService(input, history, pinService);
        bankService = new BankService(input, history, pinService);
        loanService = new LoanSavingsService(input, history, pinService);
        momoService = new MomoPayService(input, history, pinService);
    }

    public void start() {

        System.out.println("Dialing *182# ...");
        System.out.println();

        int choice;

        do {
            System.out.println("===================================");
            System.out.println("       Welcome to MTN MoMo");
            System.out.println("===================================");
            System.out.println("1. Send Money");
            System.out.println("2. Buy");
            System.out.println("3. Pay");
            System.out.println("4. Bank Services");
            System.out.println("5. Loan & Savings");
            System.out.println("6. MoMo Pay");
            System.out.println("7. Transaction History");
            System.out.println("0. Exit");
            System.out.println("-----------------------------------");
            System.out.print("Choose option: ");

            choice = input.nextInt();

            switch (choice) {
                case 1 -> sendService.sendMoney(customer);
                case 2 -> buyService.buy(customer);
                case 3 -> payService.pay(customer);
                case 4 -> bankService.bank(customer);
                case 5 -> loanService.loanAndSavings(customer);
                case 6 -> momoService.momoPay(customer);
                case 7 -> history.showAll();
                case 0 -> System.out.println("\nSession ended. Thank you for using MTN MoMo!");
                default -> System.out.println("Invalid option. Please try again.");
            }

            System.out.println();

        } while (choice != 0);

        input.close();
    }
}