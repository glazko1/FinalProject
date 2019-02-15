package service.impl;

import dao.AlienDAO;
import dao.MovieDAO;
import dao.exception.DAOException;
import dao.impl.AlienSQL;
import dao.impl.MovieSQL;
import entity.Alien;
import entity.Movie;
import service.AlienSpecialistService;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

public class AlienSpecialist implements AlienSpecialistService {

    private static final AlienSpecialist INSTANCE = new AlienSpecialist();

    public static AlienSpecialist getInstance() {
        return INSTANCE;
    }

    private AlienSpecialist() {}

    private AlienDAO alienDAO = AlienSQL.getInstance();
    private MovieDAO movieDAO = MovieSQL.getInstance();

    @Override
    public void addAlien(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String alienName = request.getParameter("alienName");
        String planet = request.getParameter("planet");
        String description = request.getParameter("description");
        try {
            Movie movie = movieDAO.getMovieByTitle(request.getParameter("movie"));
            Random random = new Random();
            long id = Math.abs(random.nextLong());
            Alien alien = new Alien(id, alienName, movie, planet, description, 0.0);
            alienDAO.addNewAlien(alien);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
