package command.impl;

import command.Command;
import command.exception.CommandException;
import service.AlienSpecialistService;
import service.exception.ServiceException;
import util.generator.GeneratorId;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AddAlienCommand implements Command {

    private AlienSpecialistService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public AddAlienCommand(AlienSpecialistService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        String alienName = request.getParameter("alienName");
        String planet = request.getParameter("planet");
        String description = request.getParameter("description");
        String movie = request.getParameter("movie");
        GeneratorId generatorId = GeneratorId.getInstance();
        long id = generatorId.generateId();
        String imagePath = "img/" + id + ".png";
        String path = request.getServletContext().getRealPath("/") + imagePath;
        try {
            Part filePart = request.getPart("photo");
            InputStream fileContent = filePart.getInputStream();
            service.addAlien(id, alienName, planet, description, movie, imagePath);
            FileOutputStream outputStream = new FileOutputStream(path);
            System.out.println(1);
            outputStream.write(fileContent.readAllBytes());
            service.sendNotificationToAll("New alien " + alienName +
                    " was added! Visit his page to learn more!");
        } catch (IOException | ServletException | ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
