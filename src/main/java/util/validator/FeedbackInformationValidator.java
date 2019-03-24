package util.validator;

public class FeedbackInformationValidator {

    private static final FeedbackInformationValidator INSTANCE = new FeedbackInformationValidator();

    public static FeedbackInformationValidator getInstance() {
        return INSTANCE;
    }

    private FeedbackInformationValidator() {}

    private static final String FEEDBACK_FORMAT_REGEX = ".{1,255}";

    /**
     * Validates given parameter by required format (checks if text of feedback
     * consists of 1-255 symbols).
     * @param feedbackText text of feedback to validate.
     * @return {@code true} if given parameter matches required format; {@code
     * false} otherwise.
     */
    public boolean validate(String feedbackText) {
        return feedbackText.matches(FEEDBACK_FORMAT_REGEX);
    }
}
