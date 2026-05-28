package MAIN.TRANSACTION;

public class Transaction {

    private String type;
    private double amount;
    private String description;

    public Transaction(String type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public void show() {
        System.out.printf("  [%-15s]  RWF %-10.0f  %s%n", type, amount, description);
    }
}
