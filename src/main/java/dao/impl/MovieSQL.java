package dao.impl;

import connection.ProxyConnection;
import dao.MovieDAO;
import dao.exception.DAOException;
import entity.Movie;
import pool.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MovieSQL implements MovieDAO {

    private static final MovieSQL INSTANCE = new MovieSQL();
    public static MovieSQL getInstance() {
        return INSTANCE;
    }

    private MovieSQL() {}

    private static final String GET_MOVIE_BY_ID_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM Movie WHERE MovieId = ?";
    private static final String GET_MOVIE_BY_TITLE_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM Movie WHERE Title = ?";
    private static final String GET_ALL_MOVIES_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM Movie";
    private static final String ADD_NEW_MOVIE_SQL = "INSERT INTO Movie (MovieId, Title, RunningTime, Budget, ReleaseDate) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_ALL_MOVIES_SORTED_BY_ID_ASC_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM Movie ORDER BY MovieId ASC";
    private static final String GET_ALL_MOVIES_SORTED_BY_ID_DESC_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM Movie ORDER BY MovieId DESC";
    private static final String GET_ALL_MOVIES_SORTED_BY_TITLE_ASC_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM Movie ORDER BY Title ASC";
    private static final String GET_ALL_MOVIES_SORTED_BY_TITLE_DESC_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM Movie ORDER BY Title DESC";
    private static final String GET_ALL_MOVIES_SORTED_BY_RUNNING_TIME_ASC_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM Movie ORDER BY RunningTime ASC";
    private static final String GET_ALL_MOVIES_SORTED_BY_RUNNING_TIME_DESC_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM Movie ORDER BY RunningTime DESC";
    private static final String EDIT_MOVIE_SQL = "UPDATE Movie SET RunningTime = ?, Budget = ?, ReleaseDate = ? WHERE MovieId = ?";
    private DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    @Override
    public Movie getMovieById(long movieId) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_MOVIE_BY_ID_SQL);
            statement.setLong(1, movieId);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Movie(set.getLong(1),
                        set.getString(2),
                        set.getInt(3),
                        set.getInt(4),
                        set.getTimestamp(5));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        throw new DAOException("No movie with id " + movieId + " in DAO!");
    }

    @Override
    public Movie getMovieByTitle(String title) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_MOVIE_BY_TITLE_SQL);
            statement.setString(1, title);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Movie(set.getLong(1),
                        set.getString(2),
                        set.getInt(3),
                        set.getInt(4),
                        set.getTimestamp(5));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        throw new DAOException("No movie with title " + title + " in DAO!");
    }

    @Override
    public List<Movie> getAllMovies() throws DAOException {
        List<Movie> movies = new ArrayList<>();
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_MOVIES_SQL);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Movie movie = new Movie(set.getLong(1),
                        set.getString(2),
                        set.getInt(3),
                        set.getInt(4),
                        set.getTimestamp(5));
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return movies;
    }

    @Override
    public void addNewMovie(Movie movie) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_MOVIE_SQL);
            statement.setLong(1, movie.getMovieId());
            statement.setString(2, movie.getTitle());
            statement.setInt(3, movie.getRunningTime());
            statement.setInt(4, movie.getBudget());
            statement.setTimestamp(5, movie.getReleaseDateTimestamp());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Movie> getAllMoviesSorted(String sortedBy, String sortType) throws DAOException {
        List<Movie> movies = new ArrayList<>();
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            String sql = getSortingSQL(sortedBy, sortType);
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Movie movie = new Movie(set.getLong(1),
                        set.getString(2),
                        set.getInt(3),
                        set.getInt(4),
                        set.getTimestamp(5));
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return movies;
    }

    private String getSortingSQL(String sortedBy, String sortType) throws DAOException {
        switch (sortedBy) {
            case "movieId":
                return "ASC".equals(sortType) ?
                        GET_ALL_MOVIES_SORTED_BY_ID_ASC_SQL :
                        GET_ALL_MOVIES_SORTED_BY_ID_DESC_SQL;
            case "title":
                return "ASC".equals(sortType) ?
                        GET_ALL_MOVIES_SORTED_BY_TITLE_ASC_SQL :
                        GET_ALL_MOVIES_SORTED_BY_TITLE_DESC_SQL;
            case "runningTime":
                return "ASC".equals(sortType) ?
                        GET_ALL_MOVIES_SORTED_BY_RUNNING_TIME_ASC_SQL :
                        GET_ALL_MOVIES_SORTED_BY_RUNNING_TIME_DESC_SQL;
            default:
                break;
        }
        throw new DAOException("No sorting SQL for parameters " + sortedBy + " and " + sortType);
    }

    @Override
    public void editMovie(long movieId, int runningTime, int budget, Date releaseDate) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(EDIT_MOVIE_SQL);
            statement.setInt(1, runningTime);
            statement.setInt(2, budget);
            statement.setTimestamp(3, new Timestamp(releaseDate.getTime() + 11000 * 1000));
            statement.setLong(4, movieId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
