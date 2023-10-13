public class Checking extends Account {
    private static final double INTEREST_RATE = 0.01;
    private static final double FEE = 1.0;

    public Checking(Profile holder, double balance) {
        super(holder, balance);
    }

    @Override
    public String getAccountType() {
        return null;
    }

    @Override
    public double monthlyInterest() {
        return balance * INTEREST_RATE;
    }

    @Override
    public double monthlyFee() {
        return FEE;
    }
}