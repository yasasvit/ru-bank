/**
* extends the Account class. It defines the behavior of a checking account, 
* including annual interest, monthly fees, and the account type
* @authors Yasasvi Tallapaneni, Pranav Gummaluri
*/
public class Checking extends Account {
    //ANNUAL_INTEREST_RATE: A field representing the annual interest rate for the checking account
    //MONTHLY_FEE: A field representing the monthly fee for the checking account
    private static final double ANNUAL_INTEREST_RATE = 0.01;
    private static final double MONTHLY_FEE = 12.0;

    /*
    * Constructor that takes a Profile (account holder) and an initial balance.
    */
    public Checking(Profile holder, double balance) {
        super(holder, balance);
    }

    
    /*
    * Overrides the getAccountType method from the parent Account class
    * @return "C"
    */
    @Override
    public String getAccountType() {
        return "C";
    }

    /*
    * Calculates the monthly interest. 
    * @return balance * INTEREST_RATE;
    */
    @Override
    public double monthlyInterest() {
        // not sure if divide by 12
        return balance * (ANNUAL_INTEREST_RATE / 12);   
    }

    /*
    * It calculates the monthly fee based on the current balance. 
    * @return If the balance is greater than or equal to $1000, there is no monthly fee (returns 0). 
    */
    @Override
    public double monthlyFee() {

        if (balance >= 1000) {
            return 0;
        } else {
            return MONTHLY_FEE;
        }
    }
}
