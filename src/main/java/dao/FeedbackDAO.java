package dao;

import dao.exception.DAOException;
import entity.Feedback;

import java.util.List;

public interface FeedbackDAO {

    /**
     * Creates and returns list of feedbacks according to some source of
     * information (file, database, etc.) and given parameter (alien's ID).
     * @param alienId ID of alien to find feedbacks about.
     * @return list of feedbacks about specified alien.
     * @throws DAOException if error occurred during the process of getting
     * information.
     */
    List<Feedback> getFeedbacksByAlienId(String alienId) throws DAOException;

    /**
     * Adds new feedback to some source of information (file, database, etc.)
     * according to all fields in given object.
     * @param feedback object with information about new feedback.
     * @throws DAOException if error occurred during the process of adding
     * information.
     */
    void addNewFeedback(Feedback feedback) throws DAOException;

    /**
     * Deletes feedback with given ID from some source of information (file,
     * database, etc.)
     * @param feedbackId ID of feedback to delete.
     * @throws DAOException if error occurred during the process of deleting
     * information.
     */
    void deleteFeedback(String feedbackId) throws DAOException;

    /**
     * Creates and returns list of feedbacks left by specified user according
     * to some source of information (file, database, etc.) and given parameter
     * (user's ID).
     * @param userId ID of user to find his feedbacks.
     * @return list of feedbacks left by specified user.
     * @throws DAOException if error occurred during the process of getting
     * information.
     */
    List<Feedback> getFeedbacksByUserId(String userId) throws DAOException;
}
