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

public class ViewSuggestedEditCommand implements Command {

    private AlienSpecialistService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserAccessChecker checker;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public ViewSuggestedEditCommand(HttpServletRequest request, HttpServletResponse response) {
        this(AlienSpecialist.getInstance(), request, response, UserAccessChecker.getInstance());
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of alien specialists.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    ViewSuggestedEditCommand(AlienSpecialistService service, HttpServletRequest request, HttpServletResponse response, UserAccessChecker checker) {
        this.service = service;
        this.request = request;
        this.response = response;
        this.checker = checker;
    }

    /**
     * Executes command of getting an edit suggested by user. Edit ID is provided by
     * request, then service layer returns edit and it's put into request to show on page.
     * @return url to redirect from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        long editId = Long.parseLong(request.getParameter("editId"));
        if (!checker.checkStatus(UserStatus.ALIEN_SPECIALIST, request) &&
                !checker.checkStatus(UserStatus.ADMIN, request)) {
            return "main";
        }
        try {
            Edit edit = service.viewSuggestedEdit(editId);
            request.setAttribute("edit", edit);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "suggested-edit-page";
    }
}
