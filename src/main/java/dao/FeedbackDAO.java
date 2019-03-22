package dao;

import dao.exception.DAOException;
import entity.Feedback;

import java.util.List;

public interface FeedbackDAO {

    List<Feedback> getFeedbacksByAlienId(String alienId) throws DAOException;
    void addNewFeedback(Feedback feedback) throws DAOException;
    void deleteFeedback(String feedbackId) throws DAOException;
    List<Feedback> getFeedbacksByUserId(String userId) throws DAOException;
}
