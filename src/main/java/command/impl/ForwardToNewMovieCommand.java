package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.UserStatus;
import util.checker.UserAccessChecker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardToNewMovieCommand implements Command {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserAccessChecker checker;

    /**
     * Constructs command with specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public ForwardToNewMovieCommand(HttpServletRequest request, HttpServletResponse response) {
        this(request, response, UserAccessChecker.getInstance());
    }

    /**
     * Constructs command with specified request, response and checker.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    ForwardToNewMovieCommand(HttpServletRequest request, HttpServletResponse response, UserAccessChecker checker) {
        this.request = request;
        this.response = response;
        this.checker = checker;
    }

    /**
     * @return url to forward from servlet.
     */
    @Override
    public String execute() throws CommandException {
        if (!checker.checkStatus(UserStatus.MOVIE_FAN, request) &&
                !checker.checkStatus(UserStatus.ADMIN, request)) {
            return "main";
        }
        return "new-movie";
    }
}
