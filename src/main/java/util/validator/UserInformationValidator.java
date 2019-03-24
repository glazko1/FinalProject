package util.validator;

public class UserInformationValidator {

    private static final UserInformationValidator INSTANCE = new UserInformationValidator();

    public static UserInformationValidator getInstance() {
        return INSTANCE;
    }

    private UserInformationValidator() {}

    private static final String USERNAME_FORMAT_REGEX = "[A-Za-z0-9][A-Za-z0-9_-]{4,13}[A-Za-z0-9]";
    private static final String NAME_FORMAT_REGEX = ".{2,30}";
    private static final String EMAIL_FORMAT_REGEX = "[a-z][[a-z][0-9][-][_]]{3,15}[@][a-z]{2,10}[.][a-z]{2,4}";
    private static final String PASSWORD_FORMAT_REGEX = "[A-Za-z0-9][A-Za-z0-9_-]{6,28}[A-Za-z0-9]";

    /**
     * Validates given parameters by required formats (checks if username consists
     * of 6-15 symbols and password consists of 8-30 symbols).
     * @param username username to validate.
     * @param password password to validate.
     * @return {@code true} if given parameters match required formats; {@code
     * false} otherwise.
     */
    public boolean validate(String username, String password) {
        return username.matches(USERNAME_FORMAT_REGEX) &&
                password.matches(PASSWORD_FORMAT_REGEX);
    }

    /**
     * Validates given parameters by required formats (checks if first name and
     * last name consist of 2-30 symbols and e-mail matches required format).
     * @param firstName first name to validate.
     * @param lastName last name to validate.
     * @param email e-mail to validate.
     * @return {@code true} if given parameters match required formats; {@code
     * false} otherwise.
     */
    public boolean validate(String firstName, String lastName, String email) {
        return firstName.matches(NAME_FORMAT_REGEX) &&
                lastName.matches(NAME_FORMAT_REGEX) &&
                email.matches(EMAIL_FORMAT_REGEX);
    }

    /**
     * Validates given parameters by required formats (checks if username
     * consists of 6-15 symbols, first name and last name consist of
     * 2-30 symbols, e-mail matches required format, password and confirmed
     * password consist of 8-30 symbols and are equal).
     * @param username username to validate.
     * @param firstName first name to validate.
     * @param lastName last name to validate.
     * @param email e-mail to validate.
     * @param password password to validate.
     * @param confirmedPassword confirmed password to validate.
     * @return {@code true} if given parameters match required formats; {@code
     * false} otherwise.
     */
    public boolean validate(String username, String firstName, String lastName,
                            String email, String password, String confirmedPassword) {
        return username.matches(USERNAME_FORMAT_REGEX) &&
                firstName.matches(NAME_FORMAT_REGEX) &&
                lastName.matches(NAME_FORMAT_REGEX) &&
                email.matches(EMAIL_FORMAT_REGEX) &&
                password.matches(PASSWORD_FORMAT_REGEX) &&
                password.equals(confirmedPassword);
    }

    /**
     * Validates given parameters by required formats (checks if all parameters
     * consist of 8-30 symbols; new password and confirmed password are equal).
     * @param currentPassword current password to validate.
     * @param newPassword new password to validate.
     * @param confirmedPassword confirmed password to validate.
     * @return {@code true} if given parameters match required formats; {@code
     * false} otherwise.
     */
    public boolean validatePasswords(String currentPassword, String newPassword, String confirmedPassword) {
        return currentPassword.matches(PASSWORD_FORMAT_REGEX) &&
                newPassword.matches(PASSWORD_FORMAT_REGEX) &&
                confirmedPassword.matches(PASSWORD_FORMAT_REGEX) &&
                newPassword.equals(confirmedPassword);
    }
}
