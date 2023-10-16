/*
* A specific type of account that extends the Account class. 
* @authors Pranav Gummaluri, Yasasvi Tallapaneni
*/

public class Savings extends Account {
    private static final double ANNUAL_INTEREST_RATE = 0.04;
    private static final double MONTHLY_FEE = 25.0;
    protected boolean isLoyal;

    /*
    * Aonstructor that takes a Profile (account holder), an initial balance, and a boolean isLoyal as parameters
    * @param Profile (account holder), double initial balance, and boolean isLoyal
    */
    public Savings(Profile holder, double balance, boolean isLoyal) {
        super(holder, balance);
        this.isLoyal = isLoyal;
    }

    //Getter method
    @Override
    public String getAccountType() {
        return "S";
    }

    /*
    * Calculates the monthly interest based on the balance and loyalty status
    * @return the balance with a potential 0.25% interest based on the conditions
    */
    @Override
    public double monthlyInterest() {

        double interestRate = ANNUAL_INTEREST_RATE;
        if (isLoyal) {
            interestRate += 0.0025;
        }
        return balance * (interestRate / 12);
    }

    /*
    * Calculates the monthly fee based on the balance.
    * @return a double indicating a monthly fee based on the conditions
    */
    @Override
    public double monthlyFee() {

        if (balance >= 500) {
            return 0;
        } else {
            return MONTHLY_FEE;
        }

    }

    //@return String representation of the savings account
    @Override
    public String toString() {
        if (isLoyal) {
            return super.toString() + "::is loyal";
        } else {
            return super.toString();
        }
    }
}
