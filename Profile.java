public class Profile implements Comparable<Profile>{
    private String fname;
    private String lname;
    private Date dob;

    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    public String getFname() {
        return fname;
    }
    public String getLname() {
        return lname;
    }
    public Date getDob() {

        return dob;
    }
    public String getFullName() {

        return fname + " " + lname;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Profile other = (Profile) obj;
        if (!fname.equals(other.fname)) {
            return false;
        }
        if (!lname.equals(other.lname)) {
            return false;
        }
        if (!dob.equals(other.dob)) {
            return false;
        }
        return true;
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
        return getFullName() + " " + dob.toString();
    }
    @Override
    public int compareTo(Profile other) {
        return this.getFullName().compareTo(other.getFullName());
    }
}