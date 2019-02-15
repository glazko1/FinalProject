package command.impl;

import command.Command;
import command.exception.CommandException;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInCommand implements Command {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public SignInCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public void execute() throws CommandException {
        Common common = Common.getInstance();
        try {
            common.signIn(request, response);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
