package dao;

import dao.exception.DAOException;
import entity.Alien;

import java.util.List;

public interface AlienDAO {

    Alien getAlienById(String alienId) throws DAOException;
    List<Alien> getAllAliens() throws DAOException;
    void addNewAlien(Alien alien) throws DAOException;
    void updateAverageRating(String alienId, double averageRating) throws DAOException;
    void editAlien(String alienId, String movieId, String planet, String description) throws DAOException;
    List<Alien> getAllAliensSorted(String sortedBy, String sortType) throws DAOException;
    void updateDescription(String alienId, String description) throws DAOException;
    void deleteAlien(String alienId) throws DAOException;
}
