import java.text.ParseException;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionManager {
    private AccountDatabase accountDatabase;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public TransactionManager() {
        accountDatabase = new AccountDatabase();
    }

    public void processTransaction(String transaction) {
        String[] tokens = transaction.split("\\s+");

        if (tokens.length == 0) {
            return;
        }

        String command = tokens[0].toUpperCase();

        try {
            switch (command) {
                case "O":
                    openAccount(tokens);
                    break;
                case "C":
                    closeAccount(tokens);
                    break;
                case "D":
                    deposit(tokens);
                    break;
                case "W":
                    withdraw(tokens);
                    break;
                case "P":
                    printSorted();
                    break;
                case "PI":
                    printFeesAndInterests();
                    break;
                case "UB":
                    updateBalances();
                    break;
                case "Q":
                    System.out.println("Transaction Manager is terminated.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command: " + command);
            }
        } catch (Exception e) {
            System.out.println("Error processing the transaction: " + e.getMessage());
        }
    }

    private void openAccount(String[] tokens) throws ParseException {
        if (tokens.length < 6) {
            System.out.println("Insufficient data tokens for opening an account.");
            return;
        }

        String accountType = tokens[1].toUpperCase();
        String firstName = tokens[2];
        String lastName = tokens[3];
        String dobString = tokens[4];
        double initialDeposit = Double.parseDouble(tokens[5]);

        Date dob = dateFormat.parse(dobString);

        Account account;

        switch (accountType) {
            case "C":
                account = new Checking(new Profile(firstName, lastName, dob), initialDeposit);
                break;
            case "CC":
                int campusCode = Integer.parseInt(tokens[6]);
                account = new CollegeChecking(new Profile(firstName, lastName, dob), initialDeposit, campusCode);
                break;
            case "S":
                int loyalCustomer = Integer.parseInt(tokens[6]);
                account = new Savings(new Profile(firstName, lastName, dob), initialDeposit, loyalCustomer == 1);
                break;
            case "MM":
                account = new MoneyMarket(new Profile(firstName, lastName, dob), initialDeposit, true, 0);
                break;
            default:
                System.out.println("Invalid account type: " + accountType);
                return;
        }

        if (accountDatabase.open(account)) {
            System.out.println("Account opened and added to the database.");
        } else {
            System.out.println("Account already exists.");
        }
    }

    private void closeAccount(String[] tokens) throws ParseException {
        if (tokens.length < 5) {
            System.out.println("Insufficient data tokens for closing an account.");
            return;
        }

        String accountType = tokens[1].toUpperCase();
        String firstName = tokens[2];
        String lastName = tokens[3];
        String dobString = tokens[4];

        Date dob = dateFormat.parse(dobString);

        Account account = accountDatabase.findAccount(new Profile(firstName, lastName, dob), accountType);

        if (account != null && accountDatabase.close(account)) {
            System.out.println("Account closed and removed from the database.");
        } else {
            System.out.println("Account not found or could not be closed.");
        }
    }

    private void deposit(String[] tokens) throws ParseException {
        if (tokens.length < 6) {
            System.out.println("Insufficient data tokens for depositing to an account.");
            return;
        }

        String accountType = tokens[1].toUpperCase();
        String firstName = tokens[2];
        String lastName = tokens[3];
        String dobString = tokens[4];
        double amount = Double.parseDouble(tokens[5]);

        Date dob = dateFormat.parse(dobString);

        Account account = accountDatabase.findAccount(new Profile(firstName, lastName, dob), accountType);

        if (account != null) {
            account.deposit(amount);
            System.out.println("Amount deposited successfully.");
        } else {
            System.out.println("Account not found or deposit failed.");
        }
    }

    private void withdraw(String[] tokens) throws ParseException {
        if (tokens.length < 6) {
            System.out.println("Insufficient data tokens for withdrawing from an account.");
            return;
        }

        String accountType = tokens[1].toUpperCase();
        String firstName = tokens[2];
        String lastName = tokens[3];
        String dobString = tokens[4];
        double amount = Double.parseDouble(tokens[5]);

        Date dob = dateFormat.parse(dobString);

        Account account = accountDatabase.findAccount(new Profile(firstName, lastName, dob), accountType);

        if (account != null) {
            if (account.withdraw(amount)) {
                System.out.println("Amount withdrawn successfully.");
            } else {
                System.out.println("Insufficient funds or withdrawal failed.");
            }
        } else {
            System.out.println("Account not found or withdrawal failed.");
        }
    }

    private void printSorted() {
        accountDatabase.printAccountsSorted();
    }

    private void printFeesAndInterests() {
        accountDatabase.calculateFeesAndInterests();
        accountDatabase.printFeesAndInterests();
    }

    private void updateBalances() {
        accountDatabase.calculateFeesAndInterests();
        accountDatabase.resetMoneyMarketWithdrawals();
    }

    public static void main(String[] args) {
        TransactionManager transactionManager = new TransactionManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a transaction: ");
            String transaction = scanner.nextLine();
            transactionManager.processTransaction(transaction);
        }
    }
}