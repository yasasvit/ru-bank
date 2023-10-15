public abstract class Account implements Comparable<Account> {
    protected Profile holder;
    protected double balance;

    public Account(Profile holder, double balance) {
        this.holder = holder;
        this.balance = balance;
    }

    public abstract double monthlyInterest();
    public abstract double monthlyFee();
    public Profile getHolder() {
        return holder;
    }

    public double getBalance() {
        return balance;
    }

    public abstract String getAccountType();

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Account other) {
        int accountTypeComparison = this.getAccountType().compareTo(other.getAccountType());
        if (accountTypeComparison != 0) {
            return accountTypeComparison;
        }
        int lastNameComparison = this.getHolder().getLname().compareTo(other.getHolder().getLname());

        if (lastNameComparison != 0) {
            return lastNameComparison;
        }

        int firstNameComparison = this.getHolder().getFname().compareTo(other.getHolder().getFname());

        if (firstNameComparison != 0) {
            return firstNameComparison;
        }

        return this.getHolder().getDob().compareTo(other.getHolder().getDob());
    }




    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Account other = (Account) obj;
        return holder.equals(other.holder);
    }

    @Override
    public int hashCode() {
        return holder.hashCode();
    }

    @Override
    public String toString() {

        return holder.toString() + String.format("::Balance $%,.2f", balance);

    }

    public void resetWithdrawals() {
    }
}