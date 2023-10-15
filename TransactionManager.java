import java.util.Calendar;
import java.util.Scanner;

public class TransactionManager {
    private AccountDatabase accountDatabase;
    private Scanner scanner;

    public static void main(String [] args) {
        TransactionManager manager = new TransactionManager();
        manager.run();
    }

    public TransactionManager() {
        this.accountDatabase = new AccountDatabase();
        System.out.println("Transaction Manager is running.");
        System.out.println();
    }

    public void run() {
        scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty() && !processTransaction(input)) {
                System.out.println("Transaction Manager is terminated.");
                break;
            }

        }
        scanner.close();
    }

    public boolean processTransaction(String transaction) {
        if (transaction == null) {
            return true;
        }
        String[] preProcessedTokens = transaction.split("\\s+");
        String [] tokens = new String[7];
        int index = 0;
        for (String preProcessedToken : preProcessedTokens) {
            if (!preProcessedToken.isEmpty()) tokens[index++] = preProcessedToken;
        }

        String command = tokens[0];
        switch (command) {
            case "O":
                openAccount(tokens, index);
                break;
            case "C":
                closeAccount(tokens, index);
                break;
            case "D":
                deposit(tokens, index);
                break;
            case "W":
                withdraw(tokens, index);
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
                return false;
            default:
                System.out.println("Invalid command!");
        }
        return true;
    }

    private void openAccount(String[] tokens, int index){
        if (index < 6) {
            System.out.println("Missing data for opening an account.");
            return;
        }
        String accountType = tokens[1].trim();
        String firstName = tokens[2];
        String lastName = tokens[3];
        String dobString = tokens[4];
        if (!isValidAmount(tokens[5])) {
            return;
        }
        double initialDeposit = Double.parseDouble(tokens[5]);
        Date dob = parseDate(dobString);
        if (dob == null) {
            return;
        }
        Profile profile = new Profile(firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase(), lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase(), dob);
        if (accountType.equals("CC") && !isValidCampusCode(tokens)) {
            return;
        }
        if (accountType.equals("CC") || accountType.equals("C")) {
            if (accountDatabase.hasCheckingOrCollegeChecking(profile, accountType)) {
                printError(firstName, lastName, dob, accountType);
                return;
            }
        }
        Account account = createNewAccount(accountType, profile, initialDeposit, tokens);
        if (account == null) {
            return;
        }
        if (accountDatabase.open(account)) {
            System.out.println(profile.toString() + "(" + accountType + ") opened.");
        } else {
            System.out.println(profile.toString() + "(" + accountType + ") is already in the database.");
        }
    }

    private boolean isValidAmount(String amountStr) {
        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                System.out.println("Initial deposit cannot be 0 or negative.");
                return false;
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Not a valid amount.");
            return false;
        }
        return true;
    }

    private Date parseDate(String dobString) {
        String[] dobTokens = dobString.split("/");
        int month = Integer.parseInt(dobTokens[0]);
        int day = Integer.parseInt(dobTokens[1]);
        int year = Integer.parseInt(dobTokens[2]);

        if (!isValidDOB(month, day, year)) {
            return null;
        }

        return new Date(month, day, year);
    }

    private boolean isValidCampusCode(String[] tokens) {
        if (tokens.length < 7 || tokens[6] == null) {
            System.out.println("Invalid number of tokens");
            return false;
        }

        int campusCode;
        try {
            campusCode = Integer.parseInt(tokens[6]);
            if (campusCode < 0 || campusCode > 2) {
                System.out.println("Invalid campus code.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid campus code.");
            return false;
        }

        return true;
    }


    private void printError(String firstName, String lastName, Date dob, String accountType) {
        System.out.println(firstName + " " + lastName + " " + dob.toString() + "(" + accountType + ") is already in the database.");
    }

    private boolean isValidDOB(int month, int day, int year) {
        Calendar calendar = Calendar.getInstance();
        int currYear = calendar.get(Calendar.YEAR);
        int currMonth = calendar.get(Calendar.MONTH);
        int currDay = calendar.get(Calendar.DATE);

        if (year > currYear || (year == currYear && month - 1 >= currMonth) || (year == currYear && month - 1 == currMonth && day >= currDay)) {
            System.out.println("DOB invalid: " + month + "/" + day + "/" + year + " cannot be today or a future day.");
            return false;
        }
        Date dob = new Date(month, day, year);
        if (!dob.isValid()) {
            System.out.println("DOB invalid: " + month + "/" + day + "/" + year + " not a valid calendar date!");
            return false;
        }

        return true;
    }

    private Account createNewAccount(String accountType, Profile profile, double initial, String [] tokens) {
        Date dob = profile.getDob();
        int age = getAge(dob);
        if (age < 16) {
            System.out.println("DOB invalid: " + dob.toString() + " under 16.");
            return null;
        }
        switch (accountType) {
            case "C":
                return new Checking(profile, initial);
            case "CC":
                if (age >= 24) {
                    System.out.println("DOB invalid: " + dob.toString() + " over 24.");
                    return null;
                }
                int campusCode = Integer.parseInt(tokens[6]);
                return new CollegeChecking(profile, initial, campusCode);
            case "S":
                boolean isLoyal = Integer.parseInt(tokens[6]) != 0;
                return new Savings(profile, initial, isLoyal);
            case "MM":
                if (initial < 2000) {
                    System.out.println("Minimum of $2000 to open a Money Market account.");
                    return null;
                }
                return new MoneyMarket(profile, initial);
            default:
                System.out.println("Invalid account type.");
                return null;
        }
    }

    private Account createTempAccount(String accountType, Profile profile, double initial, String [] tokens) {
        Date dob = profile.getDob();
        switch (accountType) {
            case "C":
                return new Checking(profile, initial);
            case "CC":
                return new CollegeChecking(profile, initial, 0);
            case "S":
                return new Savings(profile, initial, true);
            case "MM":
                if (initial < 2000) {
                    System.out.println("Minimum of $2000 to open a Money Market account.");
                    return null;
                }
                return new MoneyMarket(profile, initial);
            default:
                System.out.println("Invalid account type.");
                return null;
        }
    }

    public int getAge(Date dob) {
        Date currentDate = new Date();
        int age = currentDate.getYear() - dob.getYear();
        if (currentDate.getMonth() < dob.getMonth() || (currentDate.getMonth() == dob.getMonth() && currentDate.getDay() < dob.getDay())) {
            age--;
        }
        return age;
    }

    private void closeAccount(String[] tokens, int index) {
        if (index < 5) {
            System.out.println("Missing data for closing an account.");
            return;
        }

        String accountType = tokens[1];
        accountType = accountType.trim();
        String firstName = tokens[2];
        String lastName = tokens[3];
        String dobString = tokens[4];
        Date dob = new Date(dobString);
        if (!isValidDOB(dob.getMonth(), dob.getDay(), dob.getYear())) {
            return;
        }
        Profile profile = new Profile(firstName, lastName, dob);
        Account accountToClose = createTempAccount(accountType, profile, 5000, tokens);
        if (accountToClose == null) {
            return;
        }
        if (accountDatabase.close(accountToClose)) {
            System.out.println(profile.toString() + "(" + accountType + ") has been closed.");
        } else {
            System.out.println(profile.toString() + "(" + accountType + ") is not in the database.");
        }

    }

    private void deposit(String[] tokens, int index) {
        if (index < 6) {
            System.out.println("Missing data for depositing in an account.");
            return;
        }

        String accountType = tokens[1];
        accountType = accountType.trim();
        String firstName = tokens[2];
        String lastName = tokens[3];
        String dobString = tokens[4];
        double deposit = 0;
        try {
            deposit = Double.parseDouble(tokens[5]);
        } catch (NumberFormatException ne) {
            System.out.println("Not a valid amount.");
            return;
        }
        if (deposit <= 0) {
            System.out.println("Deposit - amount cannot be 0 or negative.");
            return;
        }
        Date dob = new Date(dobString);
        Profile profile = new Profile(firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase(), lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase(), dob);
        Account accountToDeposit = createTempAccount(accountType, profile, 5000, tokens);
        if (accountToDeposit == null) {
            return;
        }
        boolean canDeposit = accountDatabase.deposit(accountToDeposit, deposit);
        if (canDeposit) {
            System.out.println(firstName + " " + lastName + " " + dob.toString() + " (" + accountType + ") Deposit - balance updated.");
        } else {
            System.out.println(firstName + " " + lastName + " " + dob.toString() + " (" + accountType + ") is not in the database.");
        }
    }

    private void withdraw(String[] tokens, int index) {
        if (index < 6) {
            for (int i = 0; i < index; i++) {
                System.out.println(tokens[i]);
            }
            System.out.println("Missing data for withdrawing from an account.");
            return;
        }
        String accountType = tokens[1].trim();
        String firstName = tokens[2];
        String lastName = tokens[3];
        String dobString = tokens[4];
        double withdrawAmount = 0;
        try {
            withdrawAmount = Double.parseDouble(tokens[5]);
        } catch (NumberFormatException ne) {
            System.out.println("Not a valid amount.");
            return;
        }
        if (withdrawAmount <= 0) {
            System.out.println("Withdraw - amount cannot be 0 or negative.");
            return;
        }
        Date dob = new Date(dobString);
        Profile profile = new Profile(firstName, lastName, dob);
        Account accountToWithdraw = createTempAccount(accountType, profile, 5000, tokens);
        if (accountToWithdraw == null) {
            return;
        }
        int canWithdraw = accountDatabase.withdraw(accountToWithdraw, withdrawAmount);
        if (canWithdraw == 2) {
            System.out.println(profile.toString() + "(" + accountType + ") Withdraw - balance updated.");
        } else if (canWithdraw == 0) {
            System.out.println(profile.toString() + "(" + accountType + ") is not in the database.");
        } else {
            System.out.println(profile.toString() + "(" + accountType + ") Withdraw - insufficient fund.");
        }
    }

    private void printSorted() {
        accountDatabase.printSorted();
    }

    private void printFeesAndInterests() {
        accountDatabase.printFeesAndInterests();
    }

    private void updateBalances() {
        accountDatabase.calculateFeesAndInterests();
        accountDatabase.resetMoneyMarketWithdrawals();
        accountDatabase.printSorted();
    }

}