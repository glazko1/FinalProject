package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Alien;
import service.CommonService;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewAllAliensSortedCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ViewAllAliensSortedCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

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