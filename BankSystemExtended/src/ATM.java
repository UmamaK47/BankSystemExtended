import java.util.Scanner;

public class ATM {
    private Bank bank;

    public ATM(Bank bank) {
        this.bank = bank;
    }

    public void showATMMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== ATM Menu ===");
        System.out.print("Enter Customer ID: ");
        String id = sc.nextLine();
        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();

        Customer loggedIn = authenticate(id, pin);
        if (loggedIn == null) {
            System.out.println("Invalid credentials!");
            return;
        }

        int choice;
        do {
            System.out.println("\n=== Banking Menu ===");
            System.out.println("1. Show Balances");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    for (BankAccount acc : loggedIn.getAccounts()) {
                        System.out.println(acc.getAccountNumber() + " -> Balance: " + acc.getBalance());
                    }
                    break;

                case 2:
                    System.out.print("Enter account number: ");
                    sc.nextLine();
                    String accNum = sc.nextLine();
                    System.out.print("Enter amount to deposit: ");
                    double depAmt = sc.nextDouble();
                    for (BankAccount acc : loggedIn.getAccounts()) {
                        if (acc.getAccountNumber().equals(accNum)) {
                            acc.deposit(depAmt);
                            System.out.println("Deposit successful!");
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter account number: ");
                    sc.nextLine();
                    String wAccNum = sc.nextLine();
                    System.out.print("Enter amount to withdraw: ");
                    double wAmt = sc.nextDouble();
                    for (BankAccount acc : loggedIn.getAccounts()) {
                        if (acc.getAccountNumber().equals(wAccNum)) {
                            acc.withdraw(wAmt);
                            System.out.println("Withdrawal successful!");
                        }
                    }
                    break;

                case 4:
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }

    private Customer authenticate(String id, String pin) {
        try {
            java.lang.reflect.Field field = Bank.class.getDeclaredField("customers");
            field.setAccessible(true);
            java.util.Map<String, Customer> customers = (java.util.Map<String, Customer>) field.get(bank);
            Customer c = customers.get(id);
            if (c != null && c.getPin().equals(pin)) {
                return c;
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
