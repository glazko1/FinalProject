package command.impl;

import command.Command;
import command.exception.CommandException;
import service.CommonService;
import service.exception.user.InvalidEmailException;
import service.exception.ServiceException;
import service.exception.user.InvalidUserInformationException;
import service.exception.user.InvalidUsernameException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;

public class SignUpCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public SignUpCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of alien specialists.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    SignUpCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    /**
     * Executes user's sign up command. Gets entered by user information: username,
     * first name, last name, e-mail, password, confirmed password and birth date.
     * Checks and writes information about new user by service layer. Informs user about
     * error if {@link InvalidUsernameException}, {@link InvalidEmailException} or
     * {@link InvalidUserInformationException} were caught.
     * @return url to redirect from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
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
        } catch (InvalidUsernameException e) {
            session.setAttribute("signUpMessage", "message.invalid_username");
        } catch (InvalidEmailException e) {
            session.setAttribute("signUpMessage", "message.invalid_email");
        } catch (InvalidUserInformationException e) {
            session.setAttribute("signUpMessage", "message.invalid_info");
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "index";
    }
}
