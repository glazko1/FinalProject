package util.validator;

public class FeedbackInformationValidator {

    private static final FeedbackInformationValidator INSTANCE = new FeedbackInformationValidator();

    public static FeedbackInformationValidator getInstance() {
        return INSTANCE;
    }

    private FeedbackInformationValidator() {}

    private static final String FEEDBACK_FORMAT_REGEX = ".{1,255}";

    public boolean validate(String feedbackText) {
        return feedbackText.matches(FEEDBACK_FORMAT_REGEX);
    }
}
