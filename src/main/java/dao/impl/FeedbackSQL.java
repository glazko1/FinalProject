package dao.impl;

import connection.ProxyConnection;
import dao.FeedbackDAO;
import dao.exception.DAOException;
import entity.Alien;
import entity.Feedback;
import entity.Movie;
import entity.User;
import entity.UserStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pool.DatabaseConnectionPool;
import util.builder.AlienBuilder;
import util.builder.FeedbackBuilder;
import util.builder.MovieBuilder;
import util.builder.UserBuilder;

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

    private static final Logger LOGGER = LogManager.getLogger(FeedbackSQL.class);
    private static final String GET_FEEDBACKS_BY_ALIEN_ID_SQL = "SELECT f.FeedbackId, m.MovieId, m.Title, m.RunningTime, m.Budget, m.ReleaseDate, u.UserId, u.Username, u.FirstName, u.LastName, u.StatusId, u.Email, u.Banned, u.BirthDate, a.AlienId, a.AlienName, a.Planet, a.Description, a.AverageRating, a.ImagePath, f.Rating, f.FeedbackText, f.FeedbackDateTime FROM Feedback f JOIN Alien a ON f.AlienId = a.AlienId JOIN Movie m ON a.MovieId = m.MovieId JOIN User u ON f.UserId = u.UserId WHERE f.AlienId = ? ORDER BY FeedbackDateTime DESC";
    private static final String ADD_NEW_FEEDBACK_SQL = "INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (UUID(), ?, ?, ?, ?, ?)";
    private static final String DELETE_FEEDBACK_SQL = "DELETE FROM Feedback WHERE FeedbackId = ?";
    private static final String GET_FEEDBACKS_BY_USER_ID_SQL = "SELECT f.FeedbackId, m.MovieId, m.Title, m.RunningTime, m.Budget, m.ReleaseDate, u.UserId, u.Username, u.FirstName, u.LastName, u.StatusId, u.Email, u.Banned, u.BirthDate, a.AlienId, a.AlienName, a.Planet, a.Description, a.AverageRating, a.ImagePath, f.Rating, f.FeedbackText, f.FeedbackDateTime FROM Feedback f JOIN Alien a ON f.AlienId = a.AlienId JOIN Movie m ON a.MovieId = m.MovieId JOIN User u ON f.UserId = u.UserId WHERE f.UserId = ?";
    private DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    /**
     * Creates and returns list of feedbacks about specified alien (alien's ID is
     * given as parameter). Such a feedback list is used in pair with information
     * about alien to show on page. Gets proxy connection from database pool,
     * prepares statement on it (by SQL-string) and gets result set with all
     * feedbacks about alien in database.
     * @param alienId ID of alien to find feedbacks about.
     * @return list of feedbacks about specified alien.
     * @throws DAOException if {@link SQLException} was caught.
     */
    @Override
    public List<Feedback> getFeedbacksByAlienId(String alienId) throws DAOException {
        PreparedStatement statement = null;
        List<Feedback> feedbacks = new ArrayList<>();
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(GET_FEEDBACKS_BY_ALIEN_ID_SQL);
            statement.setString(1, alienId);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Feedback feedback = getNextFeedback(set);
                feedbacks.add(feedback);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
        return feedbacks;
    }

    /**
     * Adds new feedback to database according to all fields in given object. Gets
     * proxy connection from database pool, prepares statement on it (by SQL-string)
     * and executes.
     * @param feedback object with information about new feedback.
     * @throws DAOException if {@link SQLException} was caught.
     */
    @Override
    public void addNewFeedback(Feedback feedback) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(ADD_NEW_FEEDBACK_SQL);
            statement.setString(1, feedback.getAlien().getAlienId());
            statement.setString(2, feedback.getUser().getUserId());
            statement.setInt(3, feedback.getRating());
            statement.setString(4, feedback.getFeedbackText());
            statement.setTimestamp(5, feedback.getFeedbackTimestamp());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
    }

    /**
     * Deletes feedback with given ID from database.
     * @param feedbackId ID of feedback to delete.
     * @throws DAOException if {@link SQLException} was caught.
     */
    @Override
    public void deleteFeedback(String feedbackId) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(DELETE_FEEDBACK_SQL);
            statement.setString(1, feedbackId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
    }

    /**
     * Creates and returns list of feedbacks left by specified user. Gets proxy
     * connection from database pool, prepares statement on it (by SQL-string)
     * and gets result set with all feedbacks left by user in database.
     * @param userId ID of user to find his feedbacks.
     * @return list of feedbacks left by specified user.
     * @throws DAOException if {@link SQLException} was caught.
     */
    @Override
    public List<Feedback> getFeedbacksByUserId(String userId) throws DAOException {
        PreparedStatement statement = null;
        List<Feedback> feedbacks = new ArrayList<>();
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(GET_FEEDBACKS_BY_USER_ID_SQL);
            statement.setString(1, userId);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                feedbacks.add(getNextFeedback(set));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
        return feedbacks;
    }

    private Feedback getNextFeedback(ResultSet set) throws SQLException {
        int statusId = set.getInt(11) - 1;
        UserStatus status = UserStatus.values()[statusId];
        UserBuilder builder = new UserBuilder(set.getString(7));
        User user = builder.withUsername(set.getString(8))
                .withFirstName(set.getString(9))
                .withLastName(set.getString(10))
                .withStatus(status)
                .hasEmail(set.getString(12))
                .isBanned(set.getBoolean(13))
                .hasBirthDate(set.getTimestamp(14))
                .build();
        MovieBuilder movieBuilder = new MovieBuilder(set.getString(2));
        Movie movie = movieBuilder.withTitle(set.getString(3))
                .withRunningTime(set.getInt(4))
                .withBudget(set.getInt(5))
                .hasReleaseDate(set.getTimestamp(6))
                .build();
        AlienBuilder alienBuilder = new AlienBuilder(set.getString(15));
        Alien alien = alienBuilder.withName(set.getString(16))
                .fromMovie(movie)
                .fromPlanet(set.getString(17))
                .hasDescription(set.getString(18))
                .withAverageRating(set.getDouble(19))
                .withPathToImage(set.getString(20))
                .build();
        FeedbackBuilder feedbackBuilder = new FeedbackBuilder(set.getString(1));
        return feedbackBuilder.aboutAlien(alien)
                .leftByUser(user)
                .withRating(set.getInt(21))
                .withText(set.getString(22))
                .withFeedbackDateTime(set.getTimestamp(23))
                .build();
    }
}
