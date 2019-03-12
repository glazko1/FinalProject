package util.validator;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class AlienInformationValidatorTest {

    private AlienInformationValidator validator = AlienInformationValidator.getInstance();

    @Test
    public void validateWithTwoParams_shortPlanet_false() {
        //given
        //when
        boolean result = validator.validate("P", "Description");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithTwoParams_longPlanet_false() {
        //given
        //when
        boolean result = validator.validate("PlanetPlanetPlanetPlanet",
                "Description");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithTwoParams_shortDescription_false() {
        //given
        //when
        boolean result = validator.validate("Planet", "");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithTwoParams_longDescription_false() {
        //given
        //when
        boolean result = validator.validate("Planet",
                "DescriptionDescriptionDescriptionDescriptionDescription" +
                        "DescriptionDescriptionDescriptionDescriptionDescription" +
                        "DescriptionDescriptionDescriptionDescriptionDescription" +
                        "DescriptionDescriptionDescriptionDescriptionDescription" +
                        "DescriptionDescriptionDescriptionDescription");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithTwoParams_validParameters_true() {
        //given
        //when
        boolean result = validator.validate("Planet", "Description");
        //then
        assertTrue(result);
    }

    @Test
    public void validateWithThreeParams_shortAlienName_false() {
        //given
        //when
        boolean result = validator.validate("N", "Planet", "Description");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithThreeParams_longAlienName_false() {
        //given
        //when
        boolean result = validator.validate("NameNameNameNameNameNameNameName",
                "Planet", "Description");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithThreeParams_shortPlanet_false() {
        //given
        //when
        boolean result = validator.validate("Name", "P", "Description");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithThreeParams_longPlanet_false() {
        //given
        //when
        boolean result = validator.validate("Name", "PlanetPlanetPlanetPlanet",
                "Description");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithThreeParams_shortDescription_false() {
        //given
        //when
        boolean result = validator.validate("Name", "Planet", "");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithThreeParams_longDescription_false() {
        //given
        //when
        boolean result = validator.validate("Name", "Planet",
                "DescriptionDescriptionDescriptionDescriptionDescription" +
                        "DescriptionDescriptionDescriptionDescriptionDescription" +
                        "DescriptionDescriptionDescriptionDescriptionDescription" +
                        "DescriptionDescriptionDescriptionDescriptionDescription" +
                        "DescriptionDescriptionDescriptionDescription");
        //then
        assertFalse(result);
    }

    @Test
    public void validateWithThreeParams_validParameters_true() {
        //given
        //when
        boolean result = validator.validate("Name", "Planet", "Description");
        //then
        assertTrue(result);
    }
}