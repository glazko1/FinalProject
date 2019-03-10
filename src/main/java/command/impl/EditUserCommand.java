package command.impl;

import command.Command;
import command.exception.CommandException;
import service.CommonService;
import service.exception.ServiceException;
import service.exception.user.InvalidEmailException;
import service.exception.user.InvalidUserInformationException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditUserCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public EditUserCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    public EditUserCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        long userId = Long.parseLong(request.getParameter("userId"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        HttpSession session = request.getSession();
        try {
            service.editUser(userId, firstName, lastName, email);
        } catch (InvalidEmailException e) {
            session.setAttribute("message", "message.invalid_email");
        } catch (InvalidUserInformationException e) {
            session.setAttribute("message", "message.invalid_info");
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
