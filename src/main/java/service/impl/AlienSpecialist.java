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
import util.generator.GeneratorId;

public class AlienSpecialist implements AlienSpecialistService {

    private static final AlienSpecialist INSTANCE = new AlienSpecialist();

    public static AlienSpecialist getInstance() {
        return INSTANCE;
    }

    private AlienSpecialist() {}

    private AlienDAO alienDAO = AlienSQL.getInstance();
    private MovieDAO movieDAO = MovieSQL.getInstance();

    @Override
    public void addAlien(String alienName, String planet, String description, String title) throws ServiceException {
        try {
            Movie movie = movieDAO.getMovieByTitle(title);
            GeneratorId generatorId = GeneratorId.getInstance();
            long id = generatorId.generateId();
            Alien alien = new Alien(id, alienName, movie, planet, description, 0.0);
            alienDAO.addNewAlien(alien);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
