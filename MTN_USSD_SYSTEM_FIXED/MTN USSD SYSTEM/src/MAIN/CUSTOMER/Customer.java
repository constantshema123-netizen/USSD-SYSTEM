package MAIN.CUSTOMER;

public class Customer {

    private String name;
    private String phone;
    private double balance;
    private int pin;

    public Customer(String name, String phone, double balance, int pin) {
        this.name = name;
        this.phone = phone;
        this.balance = balance;
        this.pin = pin;
    }

    public String getName()   { return name; }
    public String getPhone()  { return phone; }
    public double getBalance(){ return balance; }

    public boolean checkPin(int enteredPin) {
        return this.pin == enteredPin;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        System.out.println("Insufficient balance.");
        return false;
    }
}
