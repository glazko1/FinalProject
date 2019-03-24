package util.validator;

public class AlienInformationValidator {

    private static final AlienInformationValidator INSTANCE = new AlienInformationValidator();

    public static AlienInformationValidator getInstance() {
        return INSTANCE;
    }

    private AlienInformationValidator() {}

    private static final String ALIEN_NAME_FORMAT_REGEX = ".{2,30}";
    private static final String PLANET_FORMAT_REGEX = ".{4,20}";
    private static final String DESCRIPTION_FORMAT_REGEX = ".{1,255}";

    /**
     * Validates given parameters by required formats (checks if planet consists
     * of 4-20 symbols and description consists of 1-255 symbols).
     * @param planet planet name to validate.
     * @param description text of description to validate.
     * @return {@code true} if given parameters match required formats; {@code
     * false} otherwise.
     */
    public boolean validate(String planet, String description) {
        return planet.matches(PLANET_FORMAT_REGEX) &&
                description.matches(DESCRIPTION_FORMAT_REGEX);
    }

    /**
     * Validates given parameters by required formats (checks if alien's name
     * consists of 2-30 symbols, planet consists of 4-20 symbols and description
     * consists of 1-255 symbols).
     * @param alienName alien's name to validate.
     * @param planet planet name to validate.
     * @param description text of description to validate.
     * @return {@code true} if given parameters match required formats; {@code
     * false} otherwise.
     */
    public boolean validate(String alienName, String planet, String description) {
        return alienName.matches(ALIEN_NAME_FORMAT_REGEX) &&
                planet.matches(PLANET_FORMAT_REGEX) &&
                description.matches(DESCRIPTION_FORMAT_REGEX);
    }
}
