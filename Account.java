import java.text.ParseException;

public abstract class Account implements Comparable<Account> {
    protected Profile holder;
    protected double balance;

    public Account(Profile holder, double balance) {
        this.holder = holder;
        this.balance = balance;
    }

    public Profile getHolder() {
        return holder;
    }

    public double getBalance() {
        return balance;
    }

    public abstract String getAccountType();

    public abstract double monthlyInterest();

    public abstract double monthlyFee();

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
        int typeComparison = getAccountType().compareTo(other.getAccountType());
        if (typeComparison != 0) return typeComparison;

        return holder.getFullName().compareTo(other.holder.getFullName());
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
        return holder.toString() + " Account Type: " + getAccountType() + " Balance: $" + balance;
    }
}