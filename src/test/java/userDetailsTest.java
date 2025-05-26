import com.mycompany.ivyleaguechat.UserDetails;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class userDetailsTest {
    private final UserDetails userDetails = new UserDetails();

    @Test
    public void testCheckUsername_valid() {
        assertEquals(true, userDetails.checkUsername("kyl_1"), "Expected valid username 'kyl_1' to return true");
    }

    @Test
    public void testCheckUsername_invalid_length() {
        assertEquals(false, userDetails.checkUsername("kyle11"), "Username longer than 5 characters should return false");
    }

    @Test
    public void testCheckUsername_invalid_no_underscore() {
        assertEquals(false, userDetails.checkUsername("kyle1"), "Username without underscore should return false");
    }

    @Test
    public void testCheckUsername_null() {
        assertEquals(false, userDetails.checkUsername(null), "Null username should return false");
    }

    @Test
    public void testCheckPasswordComplexity_valid() {
        assertEquals(true, userDetails.checkPasswordComplexity("Ch&&sec@ke99!"), "Expected strong password to return true");
    }

    @Test
    public void testCheckPasswordComplexity_invalid_too_short() {
        assertEquals(false, userDetails.checkPasswordComplexity("pass123"), "Password shorter than 8 characters should return false");
    }

    @Test
    public void testCheckPasswordComplexity_invalid_no_upper() {
        assertEquals(false, userDetails.checkPasswordComplexity("ch&&sec@ke99!"), "Password without uppercase letter should return false");
    }

    @Test
    public void testCheckPasswordComplexity_invalid_no_digit() {
        assertEquals(false, userDetails.checkPasswordComplexity("Ch&&sec@keaa!"), "Password without digit should return false");
    }

    @Test
    public void testCheckPasswordComplexity_invalid_no_special() {
        assertEquals(false, userDetails.checkPasswordComplexity("Chsecake99"), "Password without special character should return false");
    }

    @Test
    public void testCheckPasswordComplexity_null() {
        assertEquals(false, userDetails.checkPasswordComplexity(null), "Null password should return false");
    }

    @Test
    public void testCheckPhoneNumber_valid() {
        assertEquals(true, userDetails.checkPhoneNumber("+27838968976"), "Valid phone number should return true");
    }

    @Test
    public void testCheckPhoneNumber_invalid_format() {
        assertEquals(false, userDetails.checkPhoneNumber("08966553"), "Incorrect format should return false");
    }

    @Test
    public void testCheckPhoneNumber_invalid_length() {
        assertEquals(false, userDetails.checkPhoneNumber("+278389689761"), "Phone number longer than 12 characters should return false");
    }

    @Test
    public void testCheckPhoneNumber_invalid_no_plus() {
        assertEquals(false, userDetails.checkPhoneNumber("27838968976"), "Phone number without '+' should return false");
    }

    @Test
    public void testCheckPhoneNumber_invalid_no_country_code() {
        assertEquals(false, userDetails.checkPhoneNumber("+28838968976"), "Phone number with wrong country code should return false");
    }

    @Test
    public void testCheckPhoneNumber_null() {
        assertEquals(false, userDetails.checkPhoneNumber(null), "Null phone number should return false");
    }

    @Test
    public void testRegisterUser_valid() {
        String result = userDetails.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Registration successful!", result, "Expected successful registration message");
    }

    @Test
    public void testRegisterUser_invalid_username() {
        String result = userDetails.registerUser("kyle1", "Ch&&sec@ke99!", "+27838968976");
        String expected = "Username must include an underscore and be exactly 5 characters.\n";
        assertEquals(expected, result, "Expected username validation error message");
    }

    @Test
    public void testRegisterUser_invalid_password() {
        String result = userDetails.registerUser("kyl_1", "password", "+27838968976");
        String expected = "Password must be at least 8 characters long and include an uppercase letter, a digit, and a special character.\n";
        assertEquals(expected, result, "Expected password complexity validation error message");
    }

    @Test
    public void testRegisterUser_invalid_phone() {
        String result = userDetails.registerUser("kyl_1", "Ch&&sec@ke99!", "08966553");
        String expected = "Phone number must start with +27 and be exactly 12 characters long (e.g., +27831234567).\n";
        assertEquals(expected, result, "Expected phone number validation error message");
    }
} 
