public class MoneyMarket extends Savings {
    private static final double ANNUAL_INTEREST_RATE = 0.045;
    private static final double MONTHLY_FEE = 25.0;
    private int withdrawal; // number of withdrawals

    public MoneyMarket(Profile holder, double balance) {
        super(holder, balance, true);
        withdrawal = 0;
    }
    @Override
    public boolean withdraw(double amount) {
        if (balance >= amount) {
            withdrawal += 1;
            balance -= amount;
            if (balance < 2000) {
                isLoyal = false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void deposit(double amount) {

        balance += amount;
        if (balance >= 2000) {
            isLoyal = true;
        }
    }


    @Override
    public String getAccountType() {
        return "MM";
    }

    @Override
    public double monthlyInterest() {

        double interestRate = ANNUAL_INTEREST_RATE;
        if (isLoyal) {
            interestRate += 0.0025;
        }
        return (balance * (interestRate / 12));
    }

    @Override
    public double monthlyFee() {

        if (balance >= 2000) {
            if (withdrawal > 3) {
                return 10.0;
            } else {
                return 0.0;
            }
        } else {
            isLoyal = false;
            if (withdrawal > 3) {
                return MONTHLY_FEE + 10.0;
            } else {
                return MONTHLY_FEE;
            }
        }
    }

    @Override
    public String toString() {
        return super.toString() + "::withdrawal: " + withdrawal;
    }
    @Override
    public void resetWithdrawals() {
        withdrawal = 0;
    }
}
