/*
* A JUnit test class used to test the isValid method of the Date class 
* @authors Yasasvi Tallapaneni, Pranav Gummaluri
*/

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DateTest {
    private Date validDate1;
    private Date validDate2;
    private Date invalidDate1;
    private Date invalidDate2;
    private Date invalidDate3;
    private Date invalidDate4;
    private Date invalidDate5;

    @Test
    void testIsValidWithValidCases() {
        // Valid date for Leap year
        Date validDate1 = new Date("2/29/2020");
        assertTrue(validDate1.isValid());

        // Valid date for Non-leap year
        Date validDate2 = new Date("3/15/2022");
        assertTrue(validDate2.isValid());
    }

    @Test
    void testIsValidWithInvalidCases() {
        // Invalid date for Non-leap year
        Date invalidDate1 = new Date("2/29/2011");
        assertFalse(invalidDate1.isValid());

        // Month out of range
        Date invalidDate2 = new Date("10/32/2022");
        assertFalse(invalidDate2.isValid());

        // Day out of range
        Date invalidDate3 = new Date("6/0/2022");
        assertFalse(invalidDate3.isValid());

        // Year out of range
        Date invalidDate4 = new Date("2/15/2032");
        assertFalse(invalidDate4.isValid());

        // Invalid leap year date
        Date invalidDate5 = new Date("2/30/2020");
        assertFalse(invalidDate5.isValid());
    }
}
