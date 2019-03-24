package util.validator;

public class EditInformationValidator {

    private static final EditInformationValidator INSTANCE = new EditInformationValidator();

    public static EditInformationValidator getInstance() {
        return INSTANCE;
    }

    private EditInformationValidator() {}

    private static final String EDIT_FORMAT_REGEX = ".{1,255}";

    /**
     * Validates given parameter by required format (checks if text of description
     * consists of 1-255 symbols).
     * @param description text of description to validate.
     * @return {@code true} if given parameter matches required format; {@code
     * false} otherwise.
     */
    public boolean validate(String description) {
        return description.matches(EDIT_FORMAT_REGEX);
    }
}
