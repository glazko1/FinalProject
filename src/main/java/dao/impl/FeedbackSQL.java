package dao.impl;

import connection.ProxyConnection;
import dao.FeedbackDAO;
import dao.exception.DAOException;
import entity.Alien;
import entity.Feedback;
import entity.Movie;
import entity.User;
import entity.UserStatus;
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

    private static final String GET_FEEDBACKS_BY_ALIEN_ID_SQL = "SELECT f.FeedbackId, m.MovieId, m.Title, m.RunningTime, m.Budget, m.ReleaseDate, u.UserId, u.Username, u.FirstName, u.LastName, u.StatusId, u.Email, u.Banned, u.BirthDate, a.AlienId, a.AlienName, a.Planet, a.Description, a.AverageRating, a.ImagePath, f.Rating, f.FeedbackText, f.FeedbackDateTime FROM Feedback f JOIN Alien a ON f.AlienId = a.AlienId JOIN Movie m ON a.MovieId = m.MovieId JOIN User u ON f.UserId = u.UserId WHERE f.AlienId = ? ORDER BY FeedbackDateTime DESC";
    private static final String ADD_NEW_FEEDBACK_SQL = "INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_FEEDBACK_SQL = "DELETE FROM Feedback WHERE FeedbackId = ?";
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
    public List<Feedback> getFeedbacksByAlienId(long alienId) throws DAOException {
        List<Feedback> feedbacks = new ArrayList<>();
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_FEEDBACKS_BY_ALIEN_ID_SQL);
            statement.setLong(1, alienId);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Feedback feedback = getNextFeedback(set);
                feedbacks.add(feedback);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
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

    /**
     * Deletes feedback with given ID from database.
     * @param feedbackId ID of feedback to delete.
     * @throws DAOException if {@link SQLException} was caught.
     */
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

    private Feedback getNextFeedback(ResultSet set) throws SQLException {
        int statusId = set.getInt(11) - 1;
        UserStatus status = UserStatus.values()[statusId];
        UserBuilder builder = new UserBuilder(set.getLong(7));
        User user = builder.withUsername(set.getString(8))
                .withFirstName(set.getString(9))
                .withLastName(set.getString(10))
                .withStatus(status)
                .hasEmail(set.getString(12))
                .isBanned(set.getBoolean(13))
                .hasBirthDate(set.getTimestamp(14))
                .build();
        MovieBuilder movieBuilder = new MovieBuilder(set.getLong(2));
        Movie movie = movieBuilder.withTitle(set.getString(3))
                .withRunningTime(set.getInt(4))
                .withBudget(set.getInt(5))
                .hasReleaseDate(set.getTimestamp(6))
                .build();
        AlienBuilder alienBuilder = new AlienBuilder(set.getLong(15));
        Alien alien = alienBuilder.withName(set.getString(16))
                .fromMovie(movie)
                .fromPlanet(set.getString(17))
                .hasDescription(set.getString(18))
                .withAverageRating(set.getDouble(19))
                .withPathToImage(set.getString(20))
                .build();
        FeedbackBuilder feedbackBuilder = new FeedbackBuilder(set.getLong(1));
        return feedbackBuilder.aboutAlien(alien)
                .leftByUser(user)
                .withRating(set.getInt(21))
                .withText(set.getString(22))
                .withFeedbackDateTime(set.getTimestamp(23))
                .build();
    }
}
