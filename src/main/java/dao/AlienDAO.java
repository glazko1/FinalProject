package dao;

import dao.exception.DAOException;
import entity.Alien;

import java.util.List;

public interface AlienDAO {

    /**
     * Creates and returns alien according to some source of information (file,
     * database, etc.) and given parameter (alien's ID).
     * @param alienId ID of alien to find.
     * @return object with information about alien with given ID.
     * @throws DAOException if error occurred during the process of getting
     * information.
     */
    Alien getAlienById(String alienId) throws DAOException;

    /**
     * Creates and returns list of aliens according to some source of information
     * (file, database, etc.)
     * @return list of aliens.
     * @throws DAOException if error occurred during the process of getting
     * information.
     */
    List<Alien> getAllAliens() throws DAOException;

    /**
     * Adds new alien to some source of information (file, database, etc.)
     * according to all fields in given object.
     * @param alien object with information about new alien.
     * @throws DAOException if error occurred during the process of adding
     * information.
     */
    void addNewAlien(Alien alien) throws DAOException;

    /**
     * Updates alien's average rating and writes new information into some
     * source (file, database, etc.)
     * @param alienId alien's ID to update rating.
     * @param averageRating new average rating.
     * @throws DAOException if error occurred during the process of changing
     * information.
     */
    void updateAverageRating(String alienId, double averageRating) throws DAOException;

    /**
     * Updates information about alien with given ID and writes new information
     * into some source (file, database, etc.)
     * @param alienId ID of alien to edit.
     * @param movieId new movie ID.
     * @param planet new alien's home planet.
     * @param description new description.
     * @throws DAOException if error occurred during the process of changing
     * information.
     */
    void editAlien(String alienId, String movieId, String planet, String description) throws DAOException;

    /**
     * Creates and returns list of aliens according to some source of information
     * (file, database, etc.) sorted by given parameter ascending or descending.
     * @param sortedBy sorting parameter.
     * @param sortType type of sorting (ascending/descending).
     * @return sorted list of aliens.
     * @throws DAOException if error occurred during the process of getting
     * information.
     */
    List<Alien> getAllAliensSorted(String sortedBy, String sortType) throws DAOException;

    /**
     * Updates alien's description and writes new information into some source
     * (file, database, etc.)
     * @param alienId ID of alien to update description.
     * @param description new text of description.
     * @throws DAOException if error occurred during the process of changing
     * information.
     */
    void updateDescription(String alienId, String description) throws DAOException;

    /**
     * Deletes alien with given ID from some source of information (file,
     * database, etc.)
     * @param alienId ID of alien to delete.
     * @throws DAOException if error occurred during the process of deleting
     * information.
     */
    void deleteAlien(String alienId) throws DAOException;
}
