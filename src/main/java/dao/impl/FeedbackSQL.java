package dao.impl;

import connection.ProxyConnection;
import dao.FeedbackDAO;
import dao.exception.DAOException;
import entity.Alien;
import entity.Feedback;
import entity.Movie;
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

    private static final String GET_FEEDBACK_BY_ID_SQL = "SELECT f.FeedbackId, a.AlienId, a.AlienName, a.Planet, a.Description, a.AverageRating, a.ImagePath, m.MovieId, m.Title, m.RunningTime, m.Budget, m.ReleaseDate, u.UserId, u.Username, u.FirstName, u.LastName, u.StatusId, u.Email, u.Banned, u.BirthDate, f.Rating, f.FeedbackText, f.FeedbackDateTime FROM Feedback f JOIN Alien a ON f.AlienId = a.AlienId JOIN Movie m ON a.MovieId = m.MovieId JOIN User u ON f.UserId = u.UserId WHERE f.FeedbackId = ?";
    private static final String GET_FEEDBACKS_BY_ALIEN_ID_SQL = "SELECT f.FeedbackId, a.AlienId, a.AlienName, a.Planet, a.Description, a.AverageRating, a.ImagePath, m.MovieId, m.Title, m.RunningTime, m.Budget, m.ReleaseDate, u.UserId, u.Username, u.FirstName, u.LastName, u.StatusId, u.Email, u.Banned, u.BirthDate, f.Rating, f.FeedbackText, f.FeedbackDateTime FROM Feedback f JOIN Alien a ON f.AlienId = a.AlienId JOIN Movie m ON a.MovieId = m.MovieId JOIN User u ON f.UserId = u.UserId WHERE f.AlienId = ? ORDER BY FeedbackDateTime DESC";
    private static final String ADD_NEW_FEEDBACK_SQL = "INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_FEEDBACK_SQL = "DELETE FROM Feedback WHERE FeedbackId = ?";
    private DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    @Override
    public Feedback getFeedbackById(long feedbackId) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_FEEDBACK_BY_ID_SQL);
            statement.setLong(1, feedbackId);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Feedback(set.getLong(1),
                        new Alien(set.getLong(2),
                                set.getString(3),
                                new Movie(set.getLong(8),
                                        set.getString(9),
                                        set.getInt(10),
                                        set.getInt(11),
                                        set.getTimestamp(12)),
                                set.getString(4),
                                set.getString(5),
                                set.getDouble(6),
                                set.getString(7)),
                        new User(set.getLong(13),
                                set.getString(14),
                                set.getString(15),
                                set.getString(16),
                                set.getInt(17),
                                set.getString(18),
                                set.getBoolean(19),
                                set.getTimestamp(20)),
                        set.getInt(21),
                        set.getString(22),
                        set.getTimestamp(23));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        throw new DAOException("No feedback with ID " + feedbackId + " in DAO!");
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
                        new Alien(set.getLong(2),
                                set.getString(3),
                                new Movie(set.getLong(8),
                                        set.getString(9),
                                        set.getInt(10),
                                        set.getInt(11),
                                        set.getTimestamp(12)),
                                set.getString(4),
                                set.getString(5),
                                set.getDouble(6),
                                set.getString(7)),
                        new User(set.getLong(13),
                                set.getString(14),
                                set.getString(15),
                                set.getString(16),
                                set.getInt(17),
                                set.getString(18),
                                set.getBoolean(19),
                                set.getTimestamp(20)),
                        set.getInt(21),
                        set.getString(22),
                        set.getTimestamp(23));
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
            statement.setLong(2, feedback.getAlien().getAlienId());
            statement.setLong(3, feedback.getUser().getUserId());
            statement.setInt(4, feedback.getRating());
            statement.setString(5, feedback.getFeedbackText());
            statement.setTimestamp(6, feedback.getFeedbackTimestamp());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteFeedback(long feedbackId) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_FEEDBACK_SQL);
            statement.setLong(1, feedbackId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
