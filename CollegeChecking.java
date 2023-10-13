import java.text.ParseException;

public class CollegeChecking extends Checking {
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
    public double monthlyInterest() {
        return getBalance() * 0.03 / 12;
    }

    @Override
    public double monthlyFee() {
        switch (campusCode) {
            case 0:
                return 0;
            case 1:
                return 10;
            case 2:
                return 20;
            default:
                return 25;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CollegeChecking) {
            CollegeChecking other = (CollegeChecking) obj;
            return super.equals(other) && this.campusCode == other.campusCode;
        }
        return false;
    }

    @Override
    public String toString() {
        String campus;
        switch (campusCode) {
            case 0:
                campus = "New Brunswick";
                break;
            case 1:
                campus = "Newark";
                break;
            case 2:
                campus = "Camden";
                break;
            default:
                campus = "Invalid Campus Code";
        }
        return super.toString() + " Campus: " + campus;
    }
}