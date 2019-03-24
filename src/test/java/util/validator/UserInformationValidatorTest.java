package util.validator;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class UserInformationValidatorTest {

    private UserInformationValidator validator = UserInformationValidator.getInstance();

    @Test
    public void validateWithTwoParams_shortUsername_false() {
        //given
        //when
        boolean result = validator.validate("Short", "Password");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithTwoParams_hyphenAtUsernameEnd_false() {
        //given
        //when
        boolean result = validator.validate("Incorrect-", "Password");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithTwoParams_longUsername_false() {
        //given
        //when
        boolean result = validator.validate("UsernameUsername", "Password");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithTwoParams_shortPassword_false() {
        //given
        //when
        boolean result = validator.validate("Username", "Short");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithTwoParams_underscoreAtPasswordBeginning_false() {
        //given
        //when
        boolean result = validator.validate("Username", "_WrongPassword");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithTwoParams_longPassword_false() {
        //given
        //when
        boolean result = validator.validate("Username",
                "PasswordPasswordPasswordPassword");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithTwoParams_validParameters_true() {
        //given
        //when
        boolean result = validator.validate("Username", "Password");
        //then
        assertTrue(result);
    }

    @Test
    public void validateWithThreeParams_shortFirstName_false() {
        //given
        //when
        boolean result = validator.validate("F", "LastName", "email@gmail.com");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithThreeParams_longFirstName_false() {
        //given
        //when
        boolean result = validator.validate("FirstNameFirstNameFirstNameFirstName",
                "LastName", "email@gmail.com");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithThreeParams_shortLastName_false() {
        //given
        //when
        boolean result = validator.validate("FirstName", "L", "email@gmail.com");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithThreeParams_longLastName_false() {
        //given
        //when
        boolean result = validator.validate("FirstName",
                "LastNameLastNameLastNameLastName", "email@gmail.com");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithThreeParams_invalidEmail_false() {
        //given
        //when
        boolean result = validator.validate("FirstName", "LastName", "email@gmail,com");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithThreeParams_validParameters_true() {
        //given
        //when
        boolean result = validator.validate("FirstName", "LastName", "email@gmail.com");
        //then
        assertTrue(result);
    }

    @Test
    public void validateWithSixParams_shortUsername_false() {
        //given
        //when
        boolean result = validator.validate("Short", "FirstName",
                "LastName", "email@gmail.com", "Password",
                "Password");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithSixParams_longUsername_false() {
        //given
        //when
        boolean result = validator.validate("UsernameUsernameUsernameUsername",
                "FirstName", "LastName", "email@gmail.com",
                "Password", "Password");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithSixParams_shortFirstName_false() {
        //given
        //when
        boolean result = validator.validate("Username", "F",
                "LastName", "email@gmail.com", "Password",
                "Password");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithSixParams_longFirstName_false() {
        //given
        //when
        boolean result = validator.validate("Username",
                "FirstNameFirstNameFirstNameFirstName", "LastName",
                "email@gmail.com", "Password", "Password");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithSixParams_shortLastName_false() {
        //given
        //when
        boolean result = validator.validate("Username", "FirstName",
                "L", "email@gmail.com", "Password",
                "Password");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithSixParams_longLastName_false() {
        //given
        //when
        boolean result = validator.validate("Username", "FirstName",
                "LastNameLastNameLastNameLastName", "email@gmail.com",
                "Password", "Password");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithSixParams_invalidEmail_false() {
        //given
        //when
        boolean result = validator.validate("Username", "FirstName",
                "LastName", "email@gmail,com", "Password",
                "Password");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithSixParams_shortPassword_false() {
        //given
        //when
        boolean result = validator.validate("Username", "FirstName",
                "LastName", "email@gmail.com", "Short",
                "Short");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithSixParams_longPassword_false() {
        //given
        //when
        boolean result = validator.validate("Username", "FirstName",
                "LastName", "email@gmail.com",
                "PasswordPasswordPasswordPassword",
                "PasswordPasswordPasswordPassword");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithSixParams_passwordsDoNotMatch_false() {
        //given
        //when
        boolean result = validator.validate("Username", "FirstName",
                "LastName", "email@gmail.com", "Password",
                "ConfirmedPassword");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithSixParams_validParameters_true() {
        //given
        //when
        boolean result = validator.validate("Username", "FirstName",
                "LastName", "email@gmail.com", "Password",
                "Password");
        //then
        assertTrue(result);
    }

    @Test
    public void validatePasswords_shortCurrentPassword_false() {
        //given
        //when
        boolean result = validator.validatePasswords("Short",
                "NewPassword", "NewPassword");
        //then
        assertFalse(result);
    }

    @Test
    public void validatePasswords_longCurrentPassword_false() {
        //given
        //when
        boolean result = validator.validatePasswords("PasswordPasswordPasswordPassword",
                "NewPassword", "NewPassword");
        //then
        assertFalse(result);
    }

    @Test
    public void validatePasswords_shortNewPassword_false() {
        //given
        //when
        boolean result = validator.validatePasswords("Password",
                "Short", "Short");
        //then
        assertFalse(result);
    }

    @Test
    public void validatePasswords_longNewPassword_false() {
        //given
        //when
        boolean result = validator.validatePasswords("Password",
                "PasswordPasswordPasswordPassword",
                "PasswordPasswordPasswordPassword");
        //then
        assertFalse(result);
    }

    @Test
    public void validatePasswords_passwordsDoNotMatch_false() {
        //given
        //when
        boolean result = validator.validatePasswords("Password",
                "NewPassword", "ConfirmedPassword");
        //then
        assertFalse(result);
    }

    @Test
    public void validatePasswords_validParameters_true() {
        //given
        //when
        boolean result = validator.validatePasswords("Password",
                "NewPassword", "NewPassword");
        //then
        assertTrue(result);
    }
}