package command.impl;

import command.Command;
import command.exception.CommandException;
import service.AlienSpecialistService;
import service.exception.ServiceException;
import service.impl.AlienSpecialist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RejectEditCommand implements Command {

    private AlienSpecialistService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public RejectEditCommand(AlienSpecialistService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    public RejectEditCommand(HttpServletRequest request, HttpServletResponse response) {
        this(AlienSpecialist.getInstance(), request, response);
    }

    @Override
    public String execute() throws CommandException {
        long editId = Long.parseLong(request.getParameter("editId"));
        long userId = Long.parseLong(request.getParameter("userId"));
        try {
            String notificationText = "Unfortunately, your suggested edit #" + editId +
                    " has been rejected.";
            service.sendNotification(userId, notificationText);
            service.deleteEdit(editId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
