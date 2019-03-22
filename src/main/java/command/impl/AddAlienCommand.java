package command.impl;

import command.Command;
import command.exception.CommandException;
import service.AlienSpecialistService;
import service.exception.ServiceException;
import service.exception.alien.InvalidAlienInformationException;
import service.exception.alien.InvalidAlienNameException;
import service.impl.AlienSpecialist;
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

    /**
     * Constructs command with default service, specified request, response and
     * content writer.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public AddAlienCommand(HttpServletRequest request, HttpServletResponse response) {
        this(AlienSpecialist.getInstance(), request, response, ContentWriter.getInstance());
    }

    /**
     * Constructs command with specified service, request, response and content
     * writer.
     * @param service service layer class with opportunities of alien specialist.
     * @param request HTTP-request.
     * @param response HTTP-response.
     * @param writer content writer.
     */
    AddAlienCommand(AlienSpecialistService service, HttpServletRequest request, HttpServletResponse response, ContentWriter writer) {
        this.service = service;
        this.request = request;
        this.response = response;
        this.writer = writer;
    }

    /**
     * Executes command of adding a new alien to app. Request provides information
     * about the alien: name, planet, description, movie and its picture. Service method
     * is called to add information, {@link ContentWriter} adds picture to app, then
     * service sends notifications to all users. Method informs person about errors
     * while adding by showing a message (if {@link InvalidAlienNameException} or
     * {@link InvalidAlienInformationException} were caught).
     * @return url to redirect from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        String alienName = request.getParameter("alienName");
        String planet = request.getParameter("planet");
        String description = request.getParameter("description");
        String movie = request.getParameter("movie");
        String imagePath = "img/" + alienName + ".png";
        String path = request.getServletContext().getRealPath("/") + imagePath;
        HttpSession session = request.getSession();
        try {
            service.addAlien(alienName, planet, description, movie, imagePath);
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
}
