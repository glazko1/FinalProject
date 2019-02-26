package command.impl;

import command.Command;
import command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardToRestorePassword implements Command {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ForwardToRestorePassword(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        return "restore-password";
    }
}
