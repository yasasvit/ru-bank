public class MoneyMarket extends Savings {
    private static final double INTEREST_RATE = 0.03;
    private static final double FEE = 3.0;
    int withdrawal; // Number of withdrawals

    public MoneyMarket(Profile holder, double balance, boolean isLoyal, int withdrawal) {
        super(holder, balance, isLoyal);
        this.withdrawal = withdrawal;
    }

    @Override
    public double monthlyInterest() {
        return isLoyal ? balance * INTEREST_RATE * 1.5 : balance * INTEREST_RATE;
    }

    @Override
    public double monthlyFee() {
        return withdrawal > 6 ? FEE : 0.0;
    }
}
