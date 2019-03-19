package util.validator;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class EditInformationValidatorTest {

    private EditInformationValidator validator = EditInformationValidator.getInstance();

    @Test
    public void validate_shortDescription_false() {
        //given
        //when
        boolean result = validator.validate("");
        //then
        assertFalse(result);
    }

    @Test
    public void validate_longDescription_false() {
        //given
        //when
        boolean result = validator.validate("DescriptionDescription" +
                "DescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
                "DescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
                "DescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
                "DescriptionDescriptionDescriptionDescription");
        //then
        assertFalse(result);
    }

    @Test
    public void validate_validParameter_true() {
        //given
        //when
        boolean result = validator.validate("Description");
        //then
        assertTrue(result);
    }
}