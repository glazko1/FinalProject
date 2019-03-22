package command.impl;

import command.Command;
import command.exception.CommandException;
import service.AlienSpecialistService;
import service.exception.ServiceException;
import service.exception.alien.InvalidAlienInformationException;
import service.impl.AlienSpecialist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditAlienCommand implements Command {

    private AlienSpecialistService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public EditAlienCommand(HttpServletRequest request, HttpServletResponse response) {
        this(AlienSpecialist.getInstance(), request, response);
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of alien specialist.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    EditAlienCommand(AlienSpecialistService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
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
        String movie = request.getParameter("movie");
        String alienId = request.getParameter("alienId");
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
