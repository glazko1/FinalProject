package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.User;
import entity.UserStatus;
import service.CommonService;
import service.exception.user.BannedUserException;
import service.exception.user.InvalidUserInformationException;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignInCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public SignInCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of user.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    SignInCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    /**
     * Executes user's sign in command. Gets entered by user login and password (from
     * request) and checks it by service layer. If they're correct, puts user's info
     * to session (ID, username, status, first name and last name). Informs user about
     * error if {@link BannedUserException} (user is banned and can't enter
     * the system) or {@link InvalidUserInformationException} were caught.
     * @return url to redirect from servlet: "main" if sign in was successful;
     * "index" otherwise.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        try {
            User user = service.signIn(username, password);
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("username", user.getUsername());
            UserStatus status = user.getStatus();
            session.setAttribute("status", status);
            session.setAttribute("firstName", user.getFirstName());
            session.setAttribute("lastName", user.getLastName());
        } catch (BannedUserException e) {
            session.setAttribute("signInMessage", "message.user_is_banned");
            return "index";
        } catch (InvalidUserInformationException e) {
            session.setAttribute("signInMessage", "message.invalid_username_password");
            return "index";
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
