package util.validator;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class FeedbackInformationValidatorTest {

    private FeedbackInformationValidator validator = FeedbackInformationValidator.getInstance();

    @Test
    public void validate_shortFeedback_false() {
        //given
        //when
        boolean result = validator.validate("");
        //then
        assertFalse(result);
    }

    @Test
    public void validate_longFeedback_false() {
        //given
        //when
        boolean result = validator.validate("FeedbackFeedbackFeedback" +
                "FeedbackFeedbackFeedbackFeedbackFeedbackFeedbackFeedbackFeedback" +
                "FeedbackFeedbackFeedbackFeedbackFeedbackFeedbackFeedbackFeedback" +
                "FeedbackFeedbackFeedbackFeedbackFeedbackFeedbackFeedbackFeedback" +
                "FeedbackFeedbackFeedbackFeedbackFeedback");
        //then
        assertFalse(result);
    }

    @Test
    public void validate_validParameter_true() {
        //given
        //when
        boolean result = validator.validate("Feedback");
        //then
        assertTrue(result);
    }
}