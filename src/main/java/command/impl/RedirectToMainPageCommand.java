package command.impl;

import command.Command;
import command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectToMainPageCommand implements Command {

    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs command with specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public RedirectToMainPageCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * @return url to redirect from servlet.
     */
    @Override
    public String execute() throws CommandException {
        return "main";
    }
}
