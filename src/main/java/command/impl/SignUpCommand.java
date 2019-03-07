package command.impl;

import command.Command;
import command.exception.CommandException;
import service.CommonService;
import service.exception.InvalidSignUpInformationException;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;

public class SignUpCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public SignUpCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

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
        String birthDate = request.getParameter("birthDate");
        Date date = Date.valueOf(birthDate);
        HttpSession session = request.getSession();
        try {
            service.signUp(username, firstName, lastName, email, password, confirmedPassword, date);
        } catch (InvalidSignUpInformationException e) {
            session.setAttribute("signUpMessage", "message.invalid_info");
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "index";
    }
}
