package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Alien;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewAllAliensSortedCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public ViewAllAliensSortedCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of user.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    ViewAllAliensSortedCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    /**
     * Executes command of getting information about all aliens sorted by parameter
     * and type, provided by request. List of aliens is provided by service layer,
     * then it's put into request with sorting parameters.
     * @return url to redirect from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        String sortedBy = request.getParameter("sortedBy");
        String sortType = request.getParameter("sortType");
        try {
            List<Alien> aliens = service.viewAllAliensSorted(sortedBy, sortType);
            request.setAttribute("aliens", aliens);
            request.setAttribute("sortedBy", sortedBy);
            request.setAttribute("sortType", sortType);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "alien-table";
    }
}
