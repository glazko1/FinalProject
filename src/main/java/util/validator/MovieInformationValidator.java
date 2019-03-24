package util.validator;

public class MovieInformationValidator {

    private static final MovieInformationValidator INSTANCE = new MovieInformationValidator();

    public static MovieInformationValidator getInstance() {
        return INSTANCE;
    }

    private MovieInformationValidator() {}

    private static final String MOVIE_TITLE_FORMAT_REGEX = ".{4,30}";
    private static final String RUNNING_TIME_FORMAT_REGEX = "[1-9][0-9]{0,2}";
    private static final String BUDGET_FORMAT_REGEX = "[1-9][0-9]{0,2}000000";

    /**
     * Validates given parameters by required formats (checks if running time
     * consists of 1-3 digits and is less than 250; budget consists of 1-3
     * digits and 6 zeros, is less than 500000000).
     * @param runningTime running time to validate.
     * @param budget budget to validate.
     * @return {@code true} if given parameters match required formats; {@code
     * false} otherwise.
     */
    public boolean validate(String runningTime, String budget) {
        return runningTime.matches(RUNNING_TIME_FORMAT_REGEX) &&
                !(Integer.parseInt(runningTime) > 250) &&
                budget.matches(BUDGET_FORMAT_REGEX) &&
                !(Integer.parseInt(budget) > 500000000);
    }

    /**
     * Validates given parameters by required formats (checks if title consists
     * of 4-30 symbols; running time consists of 1-3 digits and is less than
     * 250; budget consists of 1-3 digits and 6 zeros, is less than 500000000).
     * @param title title to validate.
     * @param runningTime running time to validate.
     * @param budget budget to validate.
     * @return {@code true} if given parameters match required formats; {@code
     * false} otherwise.
     */
    public boolean validate(String title, String runningTime, String budget) {
        return title.matches(MOVIE_TITLE_FORMAT_REGEX) &&
                runningTime.matches(RUNNING_TIME_FORMAT_REGEX) &&
                !(Integer.parseInt(runningTime) > 250) &&
                budget.matches(BUDGET_FORMAT_REGEX) &&
                !(Integer.parseInt(budget) > 500000000);
    }
}
