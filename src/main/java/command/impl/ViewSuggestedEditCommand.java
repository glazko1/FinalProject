package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Edit;
import service.AlienSpecialistService;
import service.exception.ServiceException;
import service.impl.AlienSpecialist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewSuggestedEditCommand implements Command {

    private AlienSpecialistService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public ViewSuggestedEditCommand(HttpServletRequest request, HttpServletResponse response) {
        this(AlienSpecialist.getInstance(), request, response);
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of alien specialists.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    ViewSuggestedEditCommand(AlienSpecialistService service, HttpServletRequest request, HttpServletResponse response) {
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
