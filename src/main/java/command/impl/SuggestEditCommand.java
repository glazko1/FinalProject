package command.impl;

import command.Command;
import command.exception.CommandException;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SuggestEditCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public SuggestEditCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    public SuggestEditCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        long alienId = Long.parseLong(request.getParameter("alienId"));
        long userId = Long.parseLong(request.getParameter("userId"));
        String description = request.getParameter("description");
        try {
            service.suggestEdit(userId, alienId, description);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
