package dao.impl;

import connection.ProxyConnection;
import dao.AlienDAO;
import dao.exception.DAOException;
import entity.Alien;
import entity.Movie;
import pool.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlienSQL implements AlienDAO {

    private static final AlienSQL INSTANCE = new AlienSQL();

    public static AlienSQL getInstance() {
        return INSTANCE;
    }

    private AlienSQL() {}

    private static final String GET_ALIEN_BY_ID_SQL = "SELECT a.AlienId, a.AlienName, m.MovieId, m.Title, m.RunningTime, m.Budget, m.ReleaseDate, a.Planet, a.Description, a.AverageRating FROM Alien a JOIN Movie m ON a.MovieId = m.MovieId WHERE AlienId = ?";
    private static final String GET_ALL_ALIENS_SQL = "SELECT a.AlienId, a.AlienName, m.MovieId, m.Title, m.RunningTime, m.Budget, m.ReleaseDate, a.Planet, a.Description, a.AverageRating FROM Alien a JOIN Movie m ON a.MovieId = m.MovieId";
    private static final String ADD_NEW_ALIEN_SQL = "INSERT INTO Alien (AlienId, AlienName, MovieId, Planet, Description, AverageRating) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_AVERAGE_RATING_SQL = "UPDATE Alien SET AverageRating = ? WHERE AlienId = ?";
    private static final String EDIT_ALIEN_SQL = "UPDATE Alien SET MovieId = ?, Planet = ?, Description = ? WHERE AlienId = ?";
    private static final String GET_ALL_ALIENS_SORTED_BY_ID_ASC_SQL = "SELECT a.AlienId, a.AlienName, m.MovieId, m.Title, m.RunningTime, m.Budget, m.ReleaseDate, a.Planet, a.Description, a.AverageRating FROM Alien a JOIN Movie m ON a.MovieId = m.MovieId ORDER BY a.AlienId ASC";
    private static final String GET_ALL_ALIENS_SORTED_BY_ID_DESC_SQL = "SELECT a.AlienId, a.AlienName, m.MovieId, m.Title, m.RunningTime, m.Budget, m.ReleaseDate, a.Planet, a.Description, a.AverageRating FROM Alien a JOIN Movie m ON a.MovieId = m.MovieId ORDER BY a.AlienId DESC";
    private static final String GET_ALL_ALIENS_SORTED_BY_NAME_ASC_SQL = "SELECT a.AlienId, a.AlienName, m.MovieId, m.Title, m.RunningTime, m.Budget, m.ReleaseDate, a.Planet, a.Description, a.AverageRating FROM Alien a JOIN Movie m ON a.MovieId = m.MovieId ORDER BY a.AlienName ASC";
    private static final String GET_ALL_ALIENS_SORTED_BY_NAME_DESC_SQL = "SELECT a.AlienId, a.AlienName, m.MovieId, m.Title, m.RunningTime, m.Budget, m.ReleaseDate, a.Planet, a.Description, a.AverageRating FROM Alien a JOIN Movie m ON a.MovieId = m.MovieId ORDER BY a.AlienName DESC";
    private static final String GET_ALL_ALIENS_SORTED_BY_MOVIE_TITLE_ASC_SQL = "SELECT a.AlienId, a.AlienName, m.MovieId, m.Title, m.RunningTime, m.Budget, m.ReleaseDate, a.Planet, a.Description, a.AverageRating FROM Alien a JOIN Movie m ON a.MovieId = m.MovieId ORDER BY m.Title ASC";
    private static final String GET_ALL_ALIENS_SORTED_BY_MOVIE_TITLE_DESC_SQL = "SELECT a.AlienId, a.AlienName, m.MovieId, m.Title, m.RunningTime, m.Budget, m.ReleaseDate, a.Planet, a.Description, a.AverageRating FROM Alien a JOIN Movie m ON a.MovieId = m.MovieId ORDER BY m.Title DESC";
    private static final String GET_ALL_ALIENS_SORTED_BY_PLANET_ASC_SQL = "SELECT a.AlienId, a.AlienName, m.MovieId, m.Title, m.RunningTime, m.Budget, m.ReleaseDate, a.Planet, a.Description, a.AverageRating FROM Alien a JOIN Movie m ON a.MovieId = m.MovieId ORDER BY a.Planet ASC";
    private static final String GET_ALL_ALIENS_SORTED_BY_PLANET_DESC_SQL = "SELECT a.AlienId, a.AlienName, m.MovieId, m.Title, m.RunningTime, m.Budget, m.ReleaseDate, a.Planet, a.Description, a.AverageRating FROM Alien a JOIN Movie m ON a.MovieId = m.MovieId ORDER BY a.Planet DESC";
    private static final String UPDATE_DESCRIPTION_SQL = "UPDATE Alien SET Description = ? WHERE AlienId = ?";
    private DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    @Override
    public Alien getAlienById(long alienId) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(GET_ALIEN_BY_ID_SQL);
            statement.setLong(1, alienId);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Alien(set.getLong(1),
                        set.getString(2),
                        new Movie(set.getInt(3),
                                set.getString(4),
                                set.getInt(5),
                                set.getInt(6),
                                set.getDate(7)),
                        set.getString(8),
                        set.getString(9),
                        set.getDouble(10));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<Alien> getAllAliens() throws DAOException {
        List<Alien> aliens = new ArrayList<>();
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_ALIENS_SQL);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Alien alien = new Alien(set.getLong(1),
                        set.getString(2),
                        new Movie(set.getInt(3),
                                set.getString(4),
                                set.getInt(5),
                                set.getInt(6),
                                set.getDate(7)),
                        set.getString(8),
                        set.getString(9),
                        set.getDouble(10));
                aliens.add(alien);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return aliens;
    }

    @Override
    public void addNewAlien(Alien alien) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_ALIEN_SQL);
            statement.setLong(1, alien.getAlienId());
            statement.setString(2, alien.getAlienName());
            statement.setLong(3, alien.getMovie().getMovieId());
            statement.setString(4, alien.getPlanet());
            statement.setString(5, alien.getDescription());
            statement.setDouble(6, alien.getAverageRating());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void updateAverageRating(long alienId, double averageRating) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_AVERAGE_RATING_SQL);
            statement.setDouble(1, averageRating);
            statement.setLong(2, alienId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void editAlien(long alienId, long movieId, String planet, String description) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(EDIT_ALIEN_SQL);
            statement.setLong(1, movieId);
            statement.setString(2, planet);
            statement.setString(3, description);
            statement.setLong(4, alienId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Alien> getAllAliensSorted(String sortedBy, String sortType) throws DAOException {
        List<Alien> aliens = new ArrayList<>();
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            String sql = getSortingSQL(sortedBy, sortType);
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Alien alien = new Alien(set.getLong(1),
                        set.getString(2),
                        new Movie(set.getInt(3),
                                set.getString(4),
                                set.getInt(5),
                                set.getInt(6),
                                set.getDate(7)),
                        set.getString(8),
                        set.getString(9),
                        set.getDouble(10));
                aliens.add(alien);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return aliens;
    }

    @Override
    public void updateDescription(long alienId, String description) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_DESCRIPTION_SQL);
            statement.setString(1, description);
            statement.setLong(2, alienId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private String getSortingSQL(String sortedBy, String sortType) throws DAOException {
        switch (sortedBy) {
            case "alienId":
                return "ASC".equals(sortType) ?
                        GET_ALL_ALIENS_SORTED_BY_ID_ASC_SQL :
                        GET_ALL_ALIENS_SORTED_BY_ID_DESC_SQL;
            case "alienName":
                return "ASC".equals(sortType) ?
                        GET_ALL_ALIENS_SORTED_BY_NAME_ASC_SQL :
                        GET_ALL_ALIENS_SORTED_BY_NAME_DESC_SQL;
            case "movieTitle":
                return "ASC".equals(sortType) ?
                        GET_ALL_ALIENS_SORTED_BY_MOVIE_TITLE_ASC_SQL :
                        GET_ALL_ALIENS_SORTED_BY_MOVIE_TITLE_DESC_SQL;
            case "planet":
                return "ASC".equals(sortType) ?
                        GET_ALL_ALIENS_SORTED_BY_PLANET_ASC_SQL :
                        GET_ALL_ALIENS_SORTED_BY_PLANET_DESC_SQL;
            default:
                break;
        }
        throw new DAOException("No sorting SQL for parameters " + sortedBy + " and " + sortType);
    }
}
