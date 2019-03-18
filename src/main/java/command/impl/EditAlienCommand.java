package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.UserStatus;
import service.AlienSpecialistService;
import service.exception.ServiceException;
import service.exception.alien.InvalidAlienInformationException;
import service.impl.AlienSpecialist;
import util.checker.UserAccessChecker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditAlienCommand implements Command {

    private AlienSpecialistService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserAccessChecker checker;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public EditAlienCommand(HttpServletRequest request, HttpServletResponse response) {
        this(AlienSpecialist.getInstance(), request, response, UserAccessChecker.getInstance());
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of alien specialists.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    EditAlienCommand(AlienSpecialistService service, HttpServletRequest request, HttpServletResponse response, UserAccessChecker checker) {
        this.service = service;
        this.request = request;
        this.response = response;
        this.checker = checker;
    }

    /**
     * Executes command of alien information change. Request provides alien's ID and
     * new information: movie, planet and description. Then service layer is called
     * to save changes. Informs person about errors (if {@link
     * InvalidAlienInformationException} was caught).
     * @return url to redirect from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        if (!checker.checkStatus(UserStatus.ALIEN_SPECIALIST, request) &&
                !checker.checkStatus(UserStatus.ADMIN, request)) {
            return "main";
        }
        String movie = request.getParameter("movie");
        long alienId = Long.parseLong(request.getParameter("alienId"));
        String planet = request.getParameter("planet");
        String description = request.getParameter("description");
        HttpSession session = request.getSession();
        try {
            service.editAlien(alienId, movie, planet, description);
        } catch (InvalidAlienInformationException e) {
            session.setAttribute("message", "message.invalid_info");
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
