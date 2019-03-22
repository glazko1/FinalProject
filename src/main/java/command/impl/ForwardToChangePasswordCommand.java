package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.User;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardToChangePasswordCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public ForwardToChangePasswordCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of user.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    ForwardToChangePasswordCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    /**
     * Executes command forwarding user to the page of password change. Request
     * provides information about user's ID, then service layer is called to get
     * information about user (his username), and it's put into request.
     * @return url to forward from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        String userId = request.getParameter("userId");
        try {
            User user = service.viewUser(userId);
            request.setAttribute("username", user.getUsername());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "change-password";
    }
}
