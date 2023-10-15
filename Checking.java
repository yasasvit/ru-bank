public class Checking extends Account {
    private static final double ANNUAL_INTEREST_RATE = 0.01;
    private static final double MONTHLY_FEE = 12.0;

    public Checking(Profile holder, double balance) {
        super(holder, balance);
    }

    @Override
    public String getAccountType() {
        return "C";
    }

    @Override
    public double monthlyInterest() {
        // not sure if divide by 12
        return balance * (ANNUAL_INTEREST_RATE / 12);
//        return balance * INTEREST_RATE;
    }

    @Override
    public double monthlyFee() {

        if (balance >= 1000) {
            return 0;
        } else {
            return MONTHLY_FEE;
        }
    }
}