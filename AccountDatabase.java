public class AccountDatabase {
    private Account[] accounts;
    private int size;

    public AccountDatabase() {
        accounts = new Account[4];
        size = 0;
    }

    public Account findAccount(Profile profile, String accountType) {
        for (int i = 0; i < size; i++) {
            Account account = accounts[i];
            if (account.getHolder().equals(profile) && account.getAccountType().equals(accountType)) {
                return account;
            }
        }
        return null;
    }

    public boolean open(Account account) {
        if (size == accounts.length) {
            grow();
        }

        if (findAccount(account.getHolder(), account.getAccountType()) == null) {
            accounts[size] = account;
            size++;
            return true;
        }
        return false;
    }

    public boolean close(Account account) {
        for (int i = 0; i < size; i++) {
            if (accounts[i].equals(account)) {
                accounts[i] = accounts[size - 1];
                accounts[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    public void calculateFeesAndInterests() {
        for (int i = 0; i < size; i++) {
            Account account = accounts[i];
            double interest = account.monthlyInterest();
            double fee = account.monthlyFee();
            account.balance = account.balance + interest - fee;
        }
    }

    public void resetMoneyMarketWithdrawals() {
        for (int i = 0; i < size; i++) {
            if (accounts[i] instanceof MoneyMarket) {
                ((MoneyMarket) accounts[i]).withdrawal = 0;
            }
        }
    }

    public void printAccountsSorted() {
        Arrays.sort(accounts, 0, size, new AccountComparator());
        for (int i = 0; i < size; i++) {
            System.out.println(accounts[i]);
        }
    }

    public void printFeesAndInterests() {
        for (int i = 0; i < size; i++) {
            Account account = accounts[i];
            double interest = account.monthlyInterest();
            double fee = account.monthlyFee();
            System.out.printf("%s: Interest: $%.2f, Fee: $%.2f%n",
                    account.getHolder().getFullName(), interest, fee);
        }
    }

    public void printUpdatedBalances() {
        calculateFeesAndInterests();
        for (int i = 0; i < size; i++) {
            Account account = accounts[i];
            System.out.printf("%s: Updated Balance: $%.2f%n",
                    account.getHolder().getFullName(), account.getBalance());
        }
    }
    private void grow() {
        accounts = Arrays.copyOf(accounts, accounts.length + 4);
    }
}