import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        ATM atm = new ATM(bank);
        Scanner sc = new Scanner(System.in);

        // Add sample customers
        bank.addCustomer(new Customer("C001", "Ali Khan", "1234"));
        bank.addCustomer(new Customer("C002", "Sara Ahmed", "4321"));

        // Add sample accounts
        bank.addAccount("C001", new SavingsAccount("A1001", 10000, 5.0));
        bank.addAccount("C002", new CheckingAccount("A2001", 5000));

        while (true) {
            System.out.println("\n=== Welcome to the Banking System ===");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login (ATM)");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter admin username: ");
                    String user = sc.nextLine();
                    System.out.print("Enter admin password: ");
                    String pass = sc.nextLine();

                    if (user.equals("admin") && pass.equals("admin123")) {
                        bank.showAdminMenu();
                    } else {
                        System.out.println("Invalid admin credentials!");
                    }
                    break;

                case 2:
                    atm.showATMMenu();
                    break;

                case 3:
                    System.out.println("Thank you for using the Banking System. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
