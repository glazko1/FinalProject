package util.validator;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MovieInformationValidatorTest {

    private MovieInformationValidator validator = MovieInformationValidator.getInstance();

    @Test
    public void validateWithTwoParams_invalidRunningTime_false() {
        //given
        //when
        boolean result = validator.validate("RunningTime", "100000000");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithTwoParams_bigRunningTime_false() {
        //given
        //when
        boolean result = validator.validate("255", "100000000");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithTwoParams_invalidBudget_false() {
        //given
        //when
        boolean result = validator.validate("100", "Budget");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithTwoParams_bigBudget_false() {
        //given
        //when
        boolean result = validator.validate("100", "600000000");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithTwoParams_validParameters_true() {
        //given
        //when
        boolean result = validator.validate("100", "200000000");
        //then
        assertTrue(result);
    }

    @Test
    public void validateWithThreeParams_shortMovieTitle_false() {
        //given
        //when
        boolean result = validator.validate("T", "RunningTime", "100000000");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithThreeParams_longMovieTitle_false() {
        //given
        //when
        boolean result = validator.validate("TitleTitleTitleTitleTitleTitleTitle",
                "RunningTime", "100000000");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithThreeParams_invalidRunningTime_false() {
        //given
        //when
        boolean result = validator.validate("Title", "RunningTime", "100000000");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithThreeParams_bigRunningTime_false() {
        //given
        //when
        boolean result = validator.validate("Title", "255", "100000000");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithThreeParams_invalidBudget_false() {
        //given
        //when
        boolean result = validator.validate("Title", "100", "Budget");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithThreeParams_bigBudget_false() {
        //given
        //when
        boolean result = validator.validate("Title", "100", "600000000");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithThreeParams_validParameters_true() {
        //given
        //when
        boolean result = validator.validate("Title", "100", "200000000");
        //then
        assertTrue(result);
    }
}