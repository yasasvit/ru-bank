public class Savings extends Account {
    private static final double ANNUAL_INTEREST_RATE = 0.04;
    private static final double MONTHLY_FEE = 25.0;
    protected boolean isLoyal;

    public Savings(Profile holder, double balance, boolean isLoyal) {
        super(holder, balance);
        this.isLoyal = isLoyal;
    }

    @Override
    public String getAccountType() {
        return "S";
    }

    @Override
    public double monthlyInterest() {

        double interestRate = ANNUAL_INTEREST_RATE;
        if (isLoyal) {
            interestRate += 0.0025;
        }
        return balance * (interestRate / 12);
    }

    @Override
    public double monthlyFee() {

        if (balance >= 500) {
            return 0;
        } else {
            return MONTHLY_FEE;
        }

    }

    @Override
    public String toString() {
        if (isLoyal) {
            return super.toString() + "::is loyal";
        } else {
            return super.toString();
        }
    }
}
