package command.impl;

import command.Command;
import command.exception.CommandException;
import service.CommonService;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class SignUpCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public SignUpCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        String username = request.getParameter("newUsername");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("newPassword");
        String confirmedPassword = request.getParameter("confirmedPassword");
        String birthDate = request.getParameter("year") + "-" +
                request.getParameter("month") + "-" +
                request.getParameter("day");
        Date date = Date.valueOf(birthDate);
        try {
            service.signUp(username, firstName, lastName, email, password, confirmedPassword, date);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "index";
    }
}
