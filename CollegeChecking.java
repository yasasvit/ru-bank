public class CollegeChecking extends Checking {
    private static final double ANNUAL_INTEREST_RATE = 0.01;
    private static final double MONTHLY_FEE = 0.0;

    private int campusCode;

    public CollegeChecking(Profile holder, double balance, int campusCode) {
        super(holder, balance);
        this.campusCode = campusCode;
    }

    public int getCampusCode() {
        return campusCode;
    }

    @Override
    public String getAccountType() {

        return "CC";
    }

    @Override
    public double monthlyFee() {
        // no monthly fee for college checking
        return MONTHLY_FEE;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CollegeChecking) {
            CollegeChecking other = (CollegeChecking) obj;
            return super.equals(other);
        }
        return false;
    }

    @Override
    public String toString() {
        String campus = "";
        switch (campusCode) {
            case 0:
                campus = "NEW_BRUNSWICK";
                break;
            case 1:
                campus = "NEWARK";
                break;
            case 2:
                campus = "CAMDEN";
                break;
            default:
                break;
        }
        return super.toString() + "::" + campus;

    }
}