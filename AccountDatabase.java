
public class AccountDatabase {
    private Account[] accounts;
    private int numAccounts;

    public AccountDatabase() {
        accounts = new Account[4];
        numAccounts = 0;
    }

    public int find(Account account) {
        for (int i = 0; i < numAccounts; i++) {
            if (accounts[i].equals(account)) {
                return i;
            }
        }
        return -1;
    }

    private void grow() {
        int newSize = accounts.length + 4;
        Account[] newAccounts = new Account[newSize];
        for (int i = 0; i < numAccounts; i++) {
            newAccounts[i] = accounts[i];
        }
        accounts = newAccounts;
    }
    public boolean contains(Account account) {
        return find(account) != -1;
    }

    public boolean open(Account account) {
        if (contains(account)) {
            return false;
        }
        if (accounts.length == numAccounts) {
            grow();
        }
        accounts[numAccounts] = account;
        numAccounts++;
        return true;
    }

    public boolean close(Account account) {
        int index = find(account);
        if (index == -1) {
            return false;
        }
        for (int i = index; i < numAccounts - 1; i++) {
            accounts[i] = accounts[i + 1];
        }
        accounts[numAccounts - 1] = null;
        numAccounts--;
        return true;
    }

    public int withdraw(Account account, double amount) {
        int index = find(account);
        if (index == -1) {
            return 0;
        }
        if (accounts[index].getBalance() < amount) {
            return 1;
        }
        accounts[index].withdraw(amount);
        return 2;
    }

    public boolean deposit(Account account, double amount) {
        int index = find(account);
        if (index == -1) {
            return false;
        }
        accounts[index].deposit(amount);
        return true;
    }


    public void printSorted() {
        if (numAccounts == 0) {
            System.out.println("Account Database is empty!");
            return;
        }
        for (int i = 0; i < numAccounts - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < numAccounts; j++) {
                if (accounts[j].compareTo(accounts[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Account temp = accounts[i];
                accounts[i] = accounts[minIndex];
                accounts[minIndex] = temp;
            }
        }

        for (int i = 0; i < numAccounts; i++) {
            Account account = accounts[i];
            String accountType = account.getAccountType();
            switch(accountType) {
                case "C":
                    System.out.print("Checking::");
                    break;
                case "CC":
                    System.out.print("College Checking::");
                    break;
                case "MM":
                    System.out.print("Money Market::Savings::");
                    break;
                case "S":
                    System.out.print("Savings::");
                    break;
            }
            System.out.println(account.toString());
        }
        System.out.println();
    }

    public boolean hasCheckingOrCollegeChecking(Profile profile, String accountType) {
        for (int i = 0; i < numAccounts; i++) {
            Account account = accounts[i];
            if (account.getHolder().equals(profile)) {
                if (account.getAccountType().equals("C") || account.getAccountType().equals("CC")) {
                    if (accountType.equals("C") || accountType.equals("CC")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void printFeesAndInterests() {
        if (numAccounts == 0) {
            System.out.println("Account Database is empty!");
            return;
        }
        for (int i = 0; i < numAccounts - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < numAccounts; j++) {
                if (accounts[j].compareTo(accounts[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Account temp = accounts[i];
                accounts[i] = accounts[minIndex];
                accounts[minIndex] = temp;
            }
        }
        for (int i = 0; i < numAccounts; i++) {
            Account account = accounts[i];
            String accountType = account.getAccountType();
            switch(accountType) {
                case "C":
                    System.out.print("Checking::");
                    break;
                case "CC":
                    System.out.print("College Checking::");
                    break;
                case "MM":
                    System.out.print("Money Market::Savings::");
                    break;
                case "S":
                    System.out.print("Savings::");
                    break;
            }
            System.out.print(account.toString());
            double interest = account.monthlyInterest();
            double fee = account.monthlyFee();
            System.out.printf("::fee $%.2f::monthly interest $%.2f%n", fee, interest);
        }
        System.out.println();

    }

    public void printUpdatedBalances() {
        for (int i = 0; i < numAccounts; i++) {
            Account account = accounts[i];
            System.out.printf("%s: Updated Balance: $%.2f%n",
                    account.getHolder().getFullName(), account.getBalance());
        }
    }

    public void calculateFeesAndInterests() {
        for (int i = 0; i < numAccounts; i++) {
            Account account = accounts[i];
            double interest = account.monthlyInterest();
            double fee = account.monthlyFee();
            account.balance = account.balance + interest - fee;
        }
    }

    public void resetMoneyMarketWithdrawals() {
        for (int i = 0; i < numAccounts; i++) {
            Account account = accounts[i];
            if (account.getAccountType().equals("MM")) {
                account.resetWithdrawals();
            }
        }
    }
}
