package dao.impl;

import connection.ProxyConnection;
import dao.FeedbackDAO;
import dao.exception.DAOException;
import entity.Feedback;
import entity.User;
import pool.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackSQL implements FeedbackDAO {

    private static final FeedbackSQL INSTANCE = new FeedbackSQL();

    public static FeedbackSQL getInstance() {
        return INSTANCE;
    }

    private FeedbackSQL() {}

    private static final String GET_FEEDBACK_BY_ID_SQL = "SELECT f.FeedbackId, f.AlienId, u.UserId, u.Username, u.FirstName, u.LastName, u.Password, u.StatusId, u.Email, u.Banned, u.BirthDate, f.Rating, f.FeedbackText, f.FeedbackDateTime FROM Feedback f JOIN User u ON f.UserId = u.UserId WHERE FeedbackId = ?";
    private static final String GET_FEEDBACKS_BY_ALIEN_ID_SQL = "SELECT f.FeedbackId, f.AlienId, u.UserId, u.Username, u.FirstName, u.LastName, u.Password, u.StatusId, u.Email, u.Banned, u.BirthDate, f.Rating, f.FeedbackText, f.FeedbackDateTime FROM Feedback f JOIN User u ON f.UserId = u.UserId WHERE AlienId = ?";
    private static final String ADD_NEW_FEEDBACK_SQL = "INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (?, ?, ?, ?, ?, ?)";
    private DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    @Override
    public Feedback getFeedbackById(long id) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_FEEDBACK_BY_ID_SQL);
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Feedback(set.getLong(1),
                        set.getLong(2),
                        new User(set.getLong(3),
                                set.getString(4),
                                set.getString(5),
                                set.getString(6),
                                set.getString(7),
                                set.getInt(8),
                                set.getString(9),
                                set.getBoolean(10),
                                set.getDate(11)),
                        set.getInt(12),
                        set.getString(13),
                        set.getDate(14));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        throw new DAOException("No feedback with ID " + id + " in DAO!");
    }

    @Override
    public List<Feedback> getFeedbacksByAlienId(long alienId) throws DAOException {
        List<Feedback> feedbacks = new ArrayList<>();
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_FEEDBACKS_BY_ALIEN_ID_SQL);
            statement.setLong(1, alienId);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Feedback feedback = new Feedback(set.getLong(1),
                        set.getLong(2),
                        new User(set.getLong(3),
                                set.getString(4),
                                set.getString(5),
                                set.getString(6),
                                set.getString(7),
                                set.getInt(8),
                                set.getString(9),
                                set.getBoolean(10),
                                set.getDate(11)),
                        set.getInt(12),
                        set.getString(13),
                        set.getDate(14));
                feedbacks.add(feedback);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return feedbacks;
    }

    @Override
    public void addNewFeedback(Feedback feedback) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_FEEDBACK_SQL);
            statement.setLong(1, feedback.getFeedbackId());
            statement.setLong(2, feedback.getAlienId());
            statement.setLong(3, feedback.getUser().getUserId());
            statement.setInt(4, feedback.getRating());
            statement.setString(5, feedback.getFeedbackText());
            statement.setDate(6, feedback.getFeedbackDateTime());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
