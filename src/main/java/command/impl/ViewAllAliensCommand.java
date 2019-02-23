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

public class ViewAllAliensCommand implements Command {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ViewAllAliensCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        CommonService service = Common.getInstance();
        try {
            List<Alien> aliens = service.viewAllAliens();
            request.setAttribute("aliens", aliens);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "alien-table";
    }
}
