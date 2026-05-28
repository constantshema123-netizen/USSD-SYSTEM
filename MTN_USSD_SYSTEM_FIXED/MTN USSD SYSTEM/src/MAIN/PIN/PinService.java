package MAIN.PIN;

import MAIN.CUSTOMER.Customer;

import java.util.Scanner;

public class PinService {

    private static final int MAX_TRIES = 3;

    public boolean verify(Customer customer, Scanner input) {
        int tries = 0;
        while (tries < MAX_TRIES) {
            System.out.print("Enter PIN: ");
            int pin = input.nextInt();
            if (customer.checkPin(pin)) {
                return true;
            }
            tries++;
            System.out.println("Wrong PIN. Attempts left: " + (MAX_TRIES - tries));
        }
        System.out.println("Too many wrong attempts. Session blocked.");
        return false;
    }
}
