package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Edit;
import service.AlienSpecialistService;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewSuggestedEditCommand implements Command {

    private AlienSpecialistService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ViewSuggestedEditCommand(AlienSpecialistService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        long editId = Long.parseLong(request.getParameter("editId"));
        try {
            Edit edit = service.viewSuggestedEdit(editId);
            request.setAttribute("edit", edit);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "suggested-edit-page";
    }
}
