package dao;

import dao.exception.DAOException;
import entity.Feedback;

import java.util.List;

public interface FeedbackDAO {

    List<Feedback> getFeedbacksByAlienId(long alienId) throws DAOException;
    void addNewFeedback(Feedback feedback) throws DAOException;
    void deleteFeedback(long feedbackId) throws DAOException;
}
