package dao.impl;

import connection.ProxyConnection;
import dao.MovieDAO;
import dao.exception.DAOException;
import entity.Movie;
import pool.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieSQL implements MovieDAO {

    private static final MovieSQL INSTANCE = new MovieSQL();
    public static MovieSQL getInstance() {
        return INSTANCE;
    }

    private MovieSQL() {}

    private static final String GET_MOVIE_BY_ID_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM movie WHERE MovieId = ?";
    private static final String GET_MOVIE_BY_TITLE_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM movie WHERE Title = ?";
    private static final String GET_ALL_MOVIES_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM movie";
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
                        set.getDate(5));
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
                        set.getDate(5));
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
                        set.getDate(5));
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return movies;
    }
}
