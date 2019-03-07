package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Alien;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardToSuggestEditCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ForwardToSuggestEditCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    public ForwardToSuggestEditCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        long alienId = Long.parseLong(request.getParameter("alienId"));
        try {
            Alien alien = service.viewAlien(alienId);
            request.setAttribute("alien", alien);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "suggest-edit";
    }
}
