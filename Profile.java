import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Profile {
    private String fname;
    private String lname;
    private Date dob;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public Profile(String fname, String lname, Date dob) throws ParseException {
        this.fname = fname;
        this.lname = lname;
        this.dob = dateFormat.parse(String.valueOf(dob));
    }

    public String getFullName() {
        return fname + " " + lname;
    }

    public Date getDob() {
        return dob;
    }

    public String getFormattedDob() {
        return dateFormat.format(dob);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Profile other = (Profile) obj;
        return fname.equalsIgnoreCase(other.fname) &&
                lname.equalsIgnoreCase(other.lname) &&
                dob.equals(other.dob);
    }

    @Override
    public int hashCode() {
        int result = fname.hashCode();
        result = 31 * result + lname.hashCode();
        result = 31 * result + dob.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return getFullName() + " " + getFormattedDob();
    }
}