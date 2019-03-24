package service;

import entity.Edit;
import service.exception.ServiceException;

import java.util.List;

public interface AlienSpecialistService {

    /**
     * Performs operation of adding information about new alien.
     * @param alienName name of new alien.
     * @param planet planet of new alien.
     * @param description description of new alien.
     * @param movieTitle movie about new alien.
     * @param imagePath path to alien's image.
     * @throws ServiceException if error occurred during the process of adding
     * information.
     */
    void addAlien(String alienName, String planet, String description, String movieTitle, String imagePath) throws ServiceException;

    /**
     * Performs operation of editing information about specified alien.
     * @param alienId ID of alien to edit information about.
     * @param movieTitle movie about alien.
     * @param planet alien's home planet.
     * @param description new description of alien.
     * @throws ServiceException if error occurred during the process of changing
     * information.
     */
    void editAlien(String alienId, String movieTitle, String planet, String description) throws ServiceException;

    /**
     * Performs operation of getting information about all suggested edits.
     * @return list of suggested edits.
     * @throws ServiceException if error occurred during the process of getting
     * information.
     */
    List<Edit> viewAllSuggestedEdits() throws ServiceException;

    /**
     * Performs operation of getting information about specified suggested edit
     * (by ID).
     * @param editId ID of edit to get.
     * @return edit with given ID.
     * @throws ServiceException if error occurred during the process of getting
     * information.
     */
    Edit viewSuggestedEdit(String editId) throws ServiceException;

    /**
     * Performs operation of accepting specified suggested edit (by ID).
     * @param editId ID of edit to accept.
     * @throws ServiceException if error occurred during the process of changing
     * information.
     */
    void acceptEdit(String editId) throws ServiceException;

    /**
     * Performs operation of deleting specified suggested edit (by ID).
     * @param editId ID of edit to delete.
     * @throws ServiceException if error occurred during the process of deleting
     * information.
     */
    void deleteEdit(String editId) throws ServiceException;

    /**
     * Performs operation of sending notification to specified user (by ID).
     * @param userId ID of user to send notification to.
     * @param notificationText text of notification.
     * @throws ServiceException if error occurred during the process of adding
     * information.
     */
    void sendNotification(String userId, String notificationText) throws ServiceException;

    /**
     * Performs operation of sending notification to all users.
     * @param notificationText text of notification.
     * @throws ServiceException if error occurred during the process of adding
     * information.
     */
    void sendNotificationToAll(String notificationText) throws ServiceException;
}
