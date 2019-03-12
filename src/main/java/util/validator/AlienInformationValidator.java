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

    public boolean validate(String planet, String description) {
        return planet.matches(PLANET_FORMAT_REGEX) &&
                description.matches(DESCRIPTION_FORMAT_REGEX);
    }

    public boolean validate(String alienName, String planet, String description) {
        return alienName.matches(ALIEN_NAME_FORMAT_REGEX) &&
                planet.matches(PLANET_FORMAT_REGEX) &&
                description.matches(DESCRIPTION_FORMAT_REGEX);
    }
}
