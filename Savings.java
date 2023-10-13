public class Savings extends Account {
    private static final double INTEREST_RATE = 0.02;
    private static final double FEE = 2.0;
    private boolean isLoyal; // Loyal customer status

    public Savings(Profile holder, double balance, boolean isLoyal) {
        super(holder, balance);
        this.isLoyal = isLoyal;
    }

    @Override
    public String getAccountType() {
        return null;
    }

    @Override
    public double monthlyInterest() {
        return isLoyal ? balance * INTEREST_RATE * 1.5 : balance * INTEREST_RATE;
    }

    @Override
    public double monthlyFee() {
        return FEE;
    }
}