package command.impl;

import command.Command;
import command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {

    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs command with specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public LogoutCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * Executes command of user's logout from system. Just invalidates current
     * session.
     * @return url to redirect from servlet.
     */
    @Override
    public String execute() throws CommandException {
        HttpSession session = request.getSession();
        session.invalidate();
        return "index";
    }
}
