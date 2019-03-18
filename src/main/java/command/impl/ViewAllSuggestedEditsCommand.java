package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Edit;
import entity.UserStatus;
import service.AlienSpecialistService;
import service.exception.ServiceException;
import service.impl.AlienSpecialist;
import util.checker.UserAccessChecker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewAllSuggestedEditsCommand implements Command {

    private AlienSpecialistService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserAccessChecker checker;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public ViewAllSuggestedEditsCommand(HttpServletRequest request, HttpServletResponse response) {
        this(AlienSpecialist.getInstance(), request, response, UserAccessChecker.getInstance());
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of alien specialists.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    ViewAllSuggestedEditsCommand(AlienSpecialistService service, HttpServletRequest request, HttpServletResponse response, UserAccessChecker checker) {
        this.service = service;
        this.request = request;
        this.response = response;
        this.checker = checker;
    }

    /**
     * Executes command of getting information about all edits suggested by users
     * sorted by parameter and type, provided by request. List of edits is provided
     * by service layer, then it's put into request with sorting parameters.
     * @return url to redirect from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        if (!checker.checkStatus(UserStatus.ALIEN_SPECIALIST, request) &&
                !checker.checkStatus(UserStatus.ADMIN, request)) {
            return "main";
        }
        try {
            List<Edit> edits = service.viewAllSuggestedEdits();
            request.setAttribute("edits", edits);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "suggested-edit-table";
    }
}
