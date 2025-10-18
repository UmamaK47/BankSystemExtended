public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        // Sample customers and accounts
        Customer c1 = new Customer("Umama", "1234");
        Customer c2 = new Customer("Sara", "4321");

        SavingsAccount s1 = new SavingsAccount("SA001", 5000, c1.getId(), 2.5, 500);
        CheckingAccount cA1 = new CheckingAccount("CA001", 2000, c1.getId(), 500);

        SavingsAccount s2 = new SavingsAccount("SA002", 3000, c2.getId(), 2.0, 300);
        CheckingAccount cA2 = new CheckingAccount("CA002", 1000, c2.getId(), 200);

        c1.addAccount(s1);
        c1.addAccount(cA1);

        c2.addAccount(s2);
        c2.addAccount(cA2);

        bank.addCustomer(c1);
        bank.addCustomer(c2);

        ATM atm = new ATM(bank);
        atm.start();
    }
}
