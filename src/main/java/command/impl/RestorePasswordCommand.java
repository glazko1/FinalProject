package command.impl;

import command.Command;
import command.exception.CommandException;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RestorePasswordCommand implements Command {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public RestorePasswordCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        CommonService service = Common.getInstance();
        String username = request.getParameter("username");
        String newPassword = request.getParameter("newPassword");
        String confirmedPassword = request.getParameter("confirmedPassword");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        try {
            service.restorePassword(username, firstName, lastName, email, newPassword, confirmedPassword);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "index";
    }
}
