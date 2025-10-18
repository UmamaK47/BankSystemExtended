import java.time.LocalDateTime;
import java.util.Scanner;

public class ATM {
    private final Bank bank;
    private final Scanner scanner;

    public ATM(Bank bank) {
        this.bank = bank;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n=== Welcome to the Bank System ===");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login (ATM)");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    adminLogin();
                    break;
                case "2":
                    customerLogin();
                    break;
                case "3":
                    System.out.println("Goodbye.");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void adminLogin() {
        System.out.print("Admin username: ");
        String u = scanner.nextLine();
        System.out.print("Admin password: ");
        String p = scanner.nextLine();
        if (u.equals("admin") && p.equals("admin123")) {
            showAdminMenu();
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    private void showAdminMenu() {
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. View All Customers");
            System.out.println("2. View All Accounts");
            System.out.println("3. View All Transactions");
            System.out.println("4. Create New Account for Customer");
            System.out.println("5. Unblock Customer");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choice: ");
            String ch = scanner.nextLine().trim();

            switch (ch) {
                case "1":
                    bank.displayAllCustomers();
                    break;
                case "2":
                    bank.displayAllAccounts();
                    break;
                case "3":
                    bank.displayAllTransactions();
                    break;
                case "4":
                    createAccountAdminFlow();
                    break;
                case "5":
                    System.out.print("Enter customer ID to unblock: ");
                    String cid = scanner.nextLine().trim();
                    boolean ok = bank.unblockCustomer(cid);
                    System.out.println(ok ? "Customer unblocked." : "Customer not found.");
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void createAccountAdminFlow() {
        System.out.print("Enter existing customer ID: ");
        String cid = scanner.nextLine().trim();
        Customer c = bank.getCustomerById(cid);
        if (c == null) {
            System.out.println("Customer not found.");
            return;
        }
        System.out.println("Select account type: 1. Savings 2. Checking");
        String t = scanner.nextLine().trim();
        System.out.print("Enter new account number: ");
        String accNo = scanner.nextLine().trim();
        System.out.print("Enter initial deposit: ");
        double init = Double.parseDouble(scanner.nextLine().trim());

        if (t.equals("1")) {
            System.out.print("Enter interest rate (%): ");
            double rate = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Enter minimum balance: ");
            double min = Double.parseDouble(scanner.nextLine().trim());
            SavingsAccount sa = new SavingsAccount(accNo, init, cid, rate, min);
            bank.createAccountForCustomer(cid, sa);
        } else if (t.equals("2")) {
            System.out.print("Enter overdraft limit: ");
            double od = Double.parseDouble(scanner.nextLine().trim());
            CheckingAccount ca = new CheckingAccount(accNo, init, cid, od);
            bank.createAccountForCustomer(cid, ca);
        } else {
            System.out.println("Invalid account type.");
        }
    }

    private void customerLogin() {
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine().trim();

        // Find customer by name and PIN
        Customer foundCustomer = null;
        for (Customer c : bank.getAllCustomers()) {
            if (c.getName().equalsIgnoreCase(name)) {
                try {
                    if (c.authenticate(pin)) {
                        foundCustomer = c;
                        break;
                    }
                } catch (AccountBlockedException e) {
                    System.out.println(e.getMessage());
                    return;
                }
            }
        }

        if (foundCustomer == null) {
            System.out.println("Invalid name or PIN.");
            return;
        }

        showCustomerMenu(foundCustomer);
    }

    private void showCustomerMenu(Customer c) {
        while (true) {
            System.out.println("\n=== Customer Menu (" + c.getName() + ") ===");
            System.out.println("1. View Accounts");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Funds");
            System.out.println("5. View Transactions");
            System.out.println("6. Logout");
            System.out.print("Choice: ");
            String ch = scanner.nextLine().trim();

            switch (ch) {
                case "1":
                    for (BankAccount a : c.getAccounts()) System.out.println(a.getAccountNumber() + " | Balance: $" + a.getBalance());
                    break;
                case "2":
                    depositFlow(c);
                    break;
                case "3":
                    withdrawFlow(c);
                    break;
                case "4":
                    transferFlow(c);
                    break;
                case "5":
                    for (BankAccount a : c.getAccounts()) {
                        System.out.println("\nTransactions for " + a.getAccountNumber() + ":");
                        for (Transactions t : a.getTransactions()) System.out.println(t);
                    }
                    break;
                case "6":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void depositFlow(Customer c) {
        System.out.print("Enter account number: ");
        String acc = scanner.nextLine().trim();
        BankAccount a = c.findAccount(acc);
        if (a == null) { System.out.println("Account not found."); return; }
        System.out.print("Enter amount to deposit: ");
        double amt = Double.parseDouble(scanner.nextLine().trim());
        a.deposit(amt);
        System.out.println("Deposit successful. New balance: $" + a.getBalance());
    }

    private void withdrawFlow(Customer c) {
        System.out.print("Enter account number: ");
        String acc = scanner.nextLine().trim();
        BankAccount a = c.findAccount(acc);
        if (a == null) { System.out.println("Account not found."); return; }
        System.out.print("Enter amount to withdraw: ");
        double amt = Double.parseDouble(scanner.nextLine().trim());
        try {
            a.withdraw(amt);
            System.out.println("Withdrawal successful. New balance: $" + a.getBalance());
        } catch (InsufficientFundsException e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
        }
    }

    private void transferFlow(Customer sender) {
        System.out.println("1. Transfer between your accounts");
        System.out.println("2. Transfer to another customer's account");
        System.out.print("Choose: ");
        String opt = scanner.nextLine().trim();
        try {
            if (opt.equals("1")) {
                System.out.print("Enter source account number: ");
                String src = scanner.nextLine().trim();
                System.out.print("Enter destination account number: ");
                String dst = scanner.nextLine().trim();
                System.out.print("Enter amount: ");
                double amt = Double.parseDouble(scanner.nextLine().trim());
                bank.transferWithinCustomer(sender.getId(), src, dst, amt);
                System.out.println("Transfer completed.");
            } else if (opt.equals("2")) {
                System.out.print("Enter your source account number: ");
                String src = scanner.nextLine().trim();
                System.out.print("Enter destination customer ID: ");
                String rcid = scanner.nextLine().trim();
                System.out.print("Enter destination account number: ");
                String racc = scanner.nextLine().trim();
                System.out.print("Enter amount: ");
                double amt = Double.parseDouble(scanner.nextLine().trim());
                bank.transferToOtherCustomer(sender.getId(), src, rcid, racc, amt);
                System.out.println("Transfer completed.");
            } else {
                System.out.println("Invalid option.");
            }
        } catch (InvalidAccountException | InsufficientFundsException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }
}
