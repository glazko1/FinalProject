package command.impl;

import command.Command;
import command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectToMainPageCommand implements Command {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public RedirectToMainPageCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        return "main";
    }
}
