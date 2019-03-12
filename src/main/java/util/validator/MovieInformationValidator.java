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

    public boolean validate(String runningTime, String budget) {
        return runningTime.matches(RUNNING_TIME_FORMAT_REGEX) &&
                !(Integer.parseInt(runningTime) > 250) &&
                budget.matches(BUDGET_FORMAT_REGEX) &&
                !(Integer.parseInt(budget) > 500000000);
    }

    public boolean validate(String title, String runningTime, String budget) {
        return title.matches(MOVIE_TITLE_FORMAT_REGEX) &&
                runningTime.matches(RUNNING_TIME_FORMAT_REGEX) &&
                !(Integer.parseInt(runningTime) > 250) &&
                budget.matches(BUDGET_FORMAT_REGEX) &&
                !(Integer.parseInt(budget) > 500000000);
    }
}
