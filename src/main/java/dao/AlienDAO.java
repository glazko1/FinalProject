package dao;

import dao.exception.DAOException;
import entity.Alien;

import java.util.List;

public interface AlienDAO {

    Alien getAlienById(long alienId) throws DAOException;
    List<Alien> getAllAliens() throws DAOException;
    void addNewAlien(Alien alien) throws DAOException;
    void updateAverageRating(long alienId, double averageRating) throws DAOException;
    void editAlien(long alienId, long movieId, String planet, String description) throws DAOException;
    List<Alien> getAllAliensSorted(String sortedBy, String sortType) throws DAOException;
}
