package dao.impl;

import connection.ProxyConnection;
import dao.EditDAO;
import dao.exception.DAOException;
import entity.Alien;
import entity.Edit;
import entity.Movie;
import entity.User;
import pool.DatabaseConnectionPool;

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
    private static final String GET_ALL_EDITS_SQL = "SELECT e.EditId, a.AlienId, a.AlienName, a.Planet, a.Description, a.AverageRating, m.MovieId, m.Title, m.RunningTime, m.Budget, m.ReleaseDate, u.UserId, u.Username, u.FirstName, u.LastName, u.StatusId, u.Email, u.Banned, u.BirthDate, e.EditText, e.EditDateTime FROM Edit e JOIN Alien a ON e.AlienId = a.AlienId JOIN Movie m ON a.MovieId = m.MovieId JOIN User U ON e.UserId = u.UserId";
    private static final String GET_EDIT_BY_ID_SQL = "SELECT e.EditId, a.AlienId, a.AlienName, a.Planet, a.Description, a.AverageRating, m.MovieId, m.Title, m.RunningTime, m.Budget, m.ReleaseDate, u.UserId, u.Username, u.FirstName, u.LastName, u.StatusId, u.Email, u.Banned, u.BirthDate, e.EditText, e.EditDateTime FROM Edit e JOIN Alien a ON e.AlienId = a.AlienId JOIN Movie m ON a.MovieId = m.MovieId JOIN User U ON e.UserId = u.UserId WHERE e.EditId = ?";
    private static final String DELETE_EDIT_SQL = "DELETE FROM Edit WHERE EditId = ?";
    private DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

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

    @Override
    public List<Edit> getAllSuggestedEdits() throws DAOException {
        List<Edit> edits = new ArrayList<>();
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_EDITS_SQL);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Edit edit = new Edit(set.getLong(1),
                        new Alien(set.getLong(2),
                                set.getString(3),
                                new Movie(set.getLong(7),
                                        set.getString(8),
                                        set.getInt(9),
                                        set.getInt(10),
                                        set.getDate(11)),
                                set.getString(4),
                                set.getString(5),
                                set.getDouble(6)),
                        new User(set.getLong(12),
                                set.getString(13),
                                set.getString(14),
                                set.getString(15),
                                set.getInt(16),
                                set.getString(17),
                                set.getBoolean(18),
                                set.getTimestamp(19)),
                        set.getString(20),
                        set.getTimestamp(21));
                edits.add(edit);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return edits;
    }

    @Override
    public Edit getSuggestedEdit(long editId) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_EDIT_BY_ID_SQL);
            statement.setLong(1, editId);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Edit(set.getLong(1),
                        new Alien(set.getLong(2),
                                set.getString(3),
                                new Movie(set.getLong(7),
                                        set.getString(8),
                                        set.getInt(9),
                                        set.getInt(10),
                                        set.getDate(11)),
                                set.getString(4),
                                set.getString(5),
                                set.getDouble(6)),
                        new User(set.getLong(12),
                                set.getString(13),
                                set.getString(14),
                                set.getString(15),
                                set.getInt(16),
                                set.getString(17),
                                set.getBoolean(18),
                                set.getTimestamp(19)),
                        set.getString(20),
                        set.getTimestamp(21));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        throw new DAOException("No edit with ID " + editId + " in DAO!");
    }

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
}
