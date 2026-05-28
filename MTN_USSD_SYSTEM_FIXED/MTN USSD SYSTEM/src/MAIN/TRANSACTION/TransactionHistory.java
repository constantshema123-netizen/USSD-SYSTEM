package MAIN.TRANSACTION;

import java.util.ArrayList;

public class TransactionHistory {

        private ArrayList<Transaction> transactions = new ArrayList<>();

        public void add(Transaction t) {
            transactions.add(t);
        }

        public void showAll() {
            System.out.println("\n===== TRANSACTION HISTORY =====");
            if (transactions.isEmpty()) {
                System.out.println("  No transactions yet.");
            } else {
                for (Transaction t : transactions) {
                    t.show();
                }
            }
            System.out.println("================================");
        }
}

