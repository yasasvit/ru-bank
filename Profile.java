/*
* Profile class represents a person's profile, containing their first name, last name, and date of birth. 
* @authors Pranav Gummaluri, Yasasvi Tallapaneni
*/

public class Profile implements Comparable<Profile>{
    private String fname;
    private String lname;
    private Date dob;

    /*
    * Constructor that takes the first name, last name, and date of birth as parameters and initializes them
    * @param Strings fname and lname representing first and last names, and a Date object representing the date
    */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    //Getter methods
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

    /*
    * Checks if the provided object is an instance of Profile and then compares 
    * the first name, last name, and date of birth.
    * @param the Object whcih represents the instance of Profile
    * @return a boolean which indicates it obj matches the Profile information
    */
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

    /*
    * Combines the hash codes of the first name, last name, and date of birth.
    * @return an int representation of the generated hashcode
    */
    @Override
    public int hashCode() {
        int result = fname.hashCode();
        result = 31 * result + lname.hashCode();
        result = 31 * result + dob.hashCode();
        return result;
    }

    //@return a string representation of the Profile object
    @Override
    public String toString() {
        return getFullName() + " " + dob.toString();
    }

    /*
    * Allows profiles to be compared based on their full names.
    * @param Profile object "other" which is to be compared
    * @return an int to represent comparison status
    */
    @Override
    public int compareTo(Profile other) {
        return this.getFullName().compareTo(other.getFullName());
    }
}
