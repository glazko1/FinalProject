package command.impl;

import command.Command;
import command.exception.CommandException;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpCommand implements Command {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public SignUpCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        CommonService service = Common.getInstance();
        String username = request.getParameter("newUsername");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("newPassword");
        String confirmedPassword = request.getParameter("confirmedPassword");
        String birthDate = request.getParameter("year") + "-" +
                request.getParameter("month") + "-" +
                request.getParameter("day");
        try {
            service.signUp(username, firstName, lastName, email, password, confirmedPassword, birthDate);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "index";
    }
}
