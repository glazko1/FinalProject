package command.impl;

import command.Command;
import command.exception.CommandException;
import service.AlienSpecialistService;
import service.exception.ServiceException;
import service.exception.alien.InvalidAlienInformationException;
import service.exception.alien.InvalidAlienNameException;
import service.impl.AlienSpecialist;
import util.generator.IdGenerator;
import util.writer.ContentWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

public class AddAlienCommand implements Command {

    private AlienSpecialistService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ContentWriter writer;

    public AddAlienCommand(HttpServletRequest request, HttpServletResponse response) {
        this(AlienSpecialist.getInstance(), request, response);
    }

    public AddAlienCommand(AlienSpecialistService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
        this.writer = ContentWriter.getInstance();
    }

    @Override
    public String execute() throws CommandException {
        String alienName = request.getParameter("alienName");
        String planet = request.getParameter("planet");
        String description = request.getParameter("description");
        String movie = request.getParameter("movie");
        IdGenerator idGenerator = IdGenerator.getInstance();
        long id = idGenerator.generateId();
        String imagePath = "img/" + id + ".png";
        String path = request.getServletContext().getRealPath("/") + imagePath;
        HttpSession session = request.getSession();
        try {
            service.addAlien(id, alienName, planet, description, movie, imagePath);
            Part filePart = request.getPart("photo");
            InputStream inputStream = filePart.getInputStream();
            writer.writeContent(inputStream, path);
            service.sendNotificationToAll("New alien " + alienName +
                    " was added! Visit his page to learn more!");
        } catch (InvalidAlienNameException e) {
            session.setAttribute("message", "message.invalid_alien_name");
        } catch (InvalidAlienInformationException e) {
            session.setAttribute("message", "message.invalid_info");
        } catch (IOException | ServletException | ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }

    void setWriter(ContentWriter writer) {
        this.writer = writer;
    }
}
