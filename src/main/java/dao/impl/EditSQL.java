package dao.impl;

import connection.ProxyConnection;
import dao.EditDAO;
import dao.exception.DAOException;
import entity.Alien;
import entity.Edit;
import entity.Movie;
import entity.User;
import entity.UserStatus;
import pool.DatabaseConnectionPool;
import util.builder.AlienBuilder;
import util.builder.EditBuilder;
import util.builder.MovieBuilder;
import util.builder.UserBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditSQL implements EditDAO {

    private static final EditSQL INSTANCE = new EditSQL();

    public static EditSQL getInstance() {
        return INSTANCE;
    }

    private EditSQL() {}

    private static final String ADD_NEW_EDIT_SQL = "INSERT INTO Edit (EditId, AlienId, UserId, EditText, EditDateTime) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_ALL_EDITS_SQL = "SELECT e.EditId, a.AlienId, a.AlienName, a.Planet, a.Description, a.AverageRating, a.ImagePath, m.MovieId, m.Title, m.RunningTime, m.Budget, m.ReleaseDate, u.UserId, u.Username, u.FirstName, u.LastName, u.StatusId, u.Email, u.Banned, u.BirthDate, e.EditText, e.EditDateTime FROM Edit e JOIN Alien a ON e.AlienId = a.AlienId JOIN Movie m ON a.MovieId = m.MovieId JOIN User U ON e.UserId = u.UserId";
    private static final String GET_EDIT_BY_ID_SQL = "SELECT e.EditId, a.AlienId, a.AlienName, a.Planet, a.Description, a.AverageRating, a.ImagePath, m.MovieId, m.Title, m.RunningTime, m.Budget, m.ReleaseDate, u.UserId, u.Username, u.FirstName, u.LastName, u.StatusId, u.Email, u.Banned, u.BirthDate, e.EditText, e.EditDateTime FROM Edit e JOIN Alien a ON e.AlienId = a.AlienId JOIN Movie m ON a.MovieId = m.MovieId JOIN User U ON e.UserId = u.UserId WHERE e.EditId = ?";
    private static final String DELETE_EDIT_SQL = "DELETE FROM Edit WHERE EditId = ?";
    private DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    /**
     * Adds new edit to database according to all fields in given object. Gets
     * proxy connection from database pool, prepares statement on it (by SQL-string)
     * and executes.
     * @param edit object with information about new edit.
     * @throws DAOException if {@link SQLException} was caught.
     */
    @Override
    public void addNewEdit(Edit edit) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_EDIT_SQL);
            statement.setLong(1, edit.getEditId());
            statement.setLong(2, edit.getAlien().getAlienId());
            statement.setLong(3, edit.getUser().getUserId());
            statement.setString(4, edit.getEditText());
            statement.setTimestamp(5, edit.getEditTimestamp());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Creates and returns list of edits existing in database. Gets proxy
     * connection from database pool, prepares statement on it (by SQL-string) and
     * gets result set with all edits in database.
     * @return list of edits existing in database.
     * @throws DAOException if {@link SQLException} was caught.
     */
    @Override
    public List<Edit> getAllSuggestedEdits() throws DAOException {
        List<Edit> edits = new ArrayList<>();
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_EDITS_SQL);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Edit edit = getNextEdit(set);
                edits.add(edit);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return edits;
    }

    /**
     * Creates and returns edit according to information in database and given
     * parameter (edit ID). Gets proxy connection from database pool, prepares
     * statement on it (by SQL-string) and gets result set with edit.
     * @param editId ID of edit to find.
     * @return edit with given ID.
     * @throws DAOException if {@link SQLException} was caught or there is no
     * edit with given ID in database.
     */
    @Override
    public Edit getSuggestedEdit(long editId) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_EDIT_BY_ID_SQL);
            statement.setLong(1, editId);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return getNextEdit(set);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        throw new DAOException("No edit with ID " + editId + " in DAO!");
    }

    /**
     * Deletes edit with given ID from database.
     * @param editId ID of edit to delete.
     * @throws DAOException if {@link SQLException} was caught.
     */
    @Override
    public void deleteEdit(long editId) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_EDIT_SQL);
            statement.setLong(1, editId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private Edit getNextEdit(ResultSet set) throws SQLException {
        int statusId = set.getInt(17) - 1;
        UserStatus status = UserStatus.values()[statusId];
        UserBuilder builder = new UserBuilder(set.getLong(13));
        User user = builder.withUsername(set.getString(14))
                .withFirstName(set.getString(15))
                .withLastName(set.getString(16))
                .withStatus(status)
                .hasEmail(set.getString(18))
                .isBanned(set.getBoolean(19))
                .hasBirthDate(set.getTimestamp(20))
                .build();
        MovieBuilder movieBuilder = new MovieBuilder(set.getLong(8));
        Movie movie = movieBuilder.withTitle(set.getString(9))
                .withRunningTime(set.getInt(10))
                .withBudget(set.getInt(11))
                .hasReleaseDate(set.getTimestamp(12))
                .build();
        AlienBuilder alienBuilder = new AlienBuilder(set.getLong(2));
        Alien alien = alienBuilder.withName(set.getString(3))
                .fromMovie(movie)
                .fromPlanet(set.getString(4))
                .hasDescription(set.getString(5))
                .withAverageRating(set.getDouble(6))
                .withPathToImage(set.getString(7))
                .build();
        EditBuilder editBuilder = new EditBuilder(set.getLong(1));
        return editBuilder.aboutAlien(alien)
                .suggestedBy(user)
                .withText(set.getString(21))
                .withEditDateTime(set.getTimestamp(22))
                .build();
    }
}
