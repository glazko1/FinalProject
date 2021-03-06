package command.impl;

import command.Command;
import command.exception.CommandException;
import service.CommonService;
import service.exception.user.InvalidPasswordException;
import service.exception.ServiceException;
import service.exception.user.InvalidUserInformationException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangePasswordCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public ChangePasswordCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of user.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    ChangePasswordCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    /**
     * Executes command of changing user's password. Request provides information
     * about user: ID, entered current password, new and confirmed passwords. Service
     * layer is called to check information and change password. Informs user about
     * errors by showing a message (if {@link InvalidPasswordException} was caught.
     * @return url to redirect from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        String userId = request.getParameter("userId");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmedPassword = request.getParameter("confirmedPassword");
        HttpSession session = request.getSession();
        try {
            service.changePassword(userId, currentPassword, newPassword, confirmedPassword);
        } catch (InvalidUserInformationException e) {
            session.setAttribute("message", "message.passwords_dont_match");
        } catch (InvalidPasswordException e) {
            session.setAttribute("message", "message.invalid_password");
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
