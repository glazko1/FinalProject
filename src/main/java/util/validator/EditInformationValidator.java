package util.validator;

public class EditInformationValidator {

    private static final EditInformationValidator INSTANCE = new EditInformationValidator();

    public static EditInformationValidator getInstance() {
        return INSTANCE;
    }

    private EditInformationValidator() {}

    private static final String EDIT_FORMAT_REGEX = ".{1,255}";

    public boolean validate(String description) {
        return description.matches(EDIT_FORMAT_REGEX);
    }
}
