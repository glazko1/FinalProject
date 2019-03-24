package dao.impl;

import connection.ProxyConnection;
import dao.MovieDAO;
import dao.exception.DAOException;
import dao.exception.movie.UsedMovieTitleException;
import entity.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pool.DatabaseConnectionPool;
import util.builder.MovieBuilder;

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

    private static final Logger LOGGER = LogManager.getLogger(MovieSQL.class);
    private static final String GET_MOVIE_BY_ID_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM Movie WHERE MovieId = ?";
    private static final String GET_MOVIE_BY_TITLE_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM Movie WHERE Title = ?";
    private static final String GET_ALL_MOVIES_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM Movie";
    private static final String ADD_NEW_MOVIE_SQL = "INSERT INTO Movie (MovieId, Title, RunningTime, Budget, ReleaseDate) VALUES (UUID(), ?, ?, ?, ?)";
    private static final String GET_ALL_MOVIES_SORTED_BY_ID_ASC_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM Movie ORDER BY MovieId ASC";
    private static final String GET_ALL_MOVIES_SORTED_BY_ID_DESC_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM Movie ORDER BY MovieId DESC";
    private static final String GET_ALL_MOVIES_SORTED_BY_TITLE_ASC_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM Movie ORDER BY Title ASC";
    private static final String GET_ALL_MOVIES_SORTED_BY_TITLE_DESC_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM Movie ORDER BY Title DESC";
    private static final String GET_ALL_MOVIES_SORTED_BY_RUNNING_TIME_ASC_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM Movie ORDER BY RunningTime ASC";
    private static final String GET_ALL_MOVIES_SORTED_BY_RUNNING_TIME_DESC_SQL = "SELECT MovieId, Title, RunningTime, Budget, ReleaseDate FROM Movie ORDER BY RunningTime DESC";
    private static final String EDIT_MOVIE_SQL = "UPDATE Movie SET RunningTime = ?, Budget = ?, ReleaseDate = ? WHERE MovieId = ?";
    private static final String DELETE_MOVIE_SQL = "DELETE FROM Movie WHERE MovieId = ?";
    private DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    /**
     * Creates and returns movie according to information in database and given
     * parameter (movie ID). Gets proxy connection from database pool, prepares
     * statement on it (by SQL-string) and gets result set with movie.
     * @param movieId ID of movie to find.
     * @return movie with given ID.
     * @throws DAOException if {@link SQLException} was caught or there is no
     * movie with given ID in database.
     */
    @Override
    public Movie getMovieById(String movieId) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(GET_MOVIE_BY_ID_SQL);
            statement.setString(1, movieId);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return getNextMovie(set);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
        throw new DAOException("No movie with id " + movieId + " in DAO!");
    }

    /**
     * Creates and returns movie according to information in database and given
     * parameter (movie title). Gets proxy connection from database pool, prepares
     * statement on it (by SQL-string) and gets result set with movie.
     * @param title title of movie to find.
     * @return movie with given title.
     * @throws DAOException if {@link SQLException} was caught or there is no
     * movie with given ID in database.
     */
    @Override
    public Movie getMovieByTitle(String title) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(GET_MOVIE_BY_TITLE_SQL);
            statement.setString(1, title);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return getNextMovie(set);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
        throw new DAOException("No movie with title " + title + " in DAO!");
    }

    /**
     * Creates and returns list of movies existing in database. Gets proxy
     * connection from database pool, prepares statement on it (by SQL-string) and
     * gets result set with all movies in database.
     * @return list of movies existing in database.
     * @throws DAOException if {@link SQLException} was caught.
     */
    @Override
    public List<Movie> getAllMovies() throws DAOException {
        PreparedStatement statement = null;
        List<Movie> movies = new ArrayList<>();
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(GET_ALL_MOVIES_SQL);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Movie movie = getNextMovie(set);
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
        return movies;
    }

    /**
     * Adds new movie to database according to all fields in given object. Movie
     * can't be added if movie with the same title already exists. Gets proxy
     * connection from database pool, prepares statement on it (by SQL-string)
     * and executes.
     * @param movie object with information about new movie.
     * @throws UsedMovieTitleException if movie with the same title already exists.
     * @throws DAOException if {@link SQLException} was caught.
     */
    @Override
    public void addNewMovie(Movie movie) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(ADD_NEW_MOVIE_SQL);
            if (movieTitleIsUsed(connection, movie.getTitle())) {
                throw new UsedMovieTitleException("Movie title is already in use!");
            }
            statement.setString(1, movie.getTitle());
            statement.setInt(2, movie.getRunningTime());
            statement.setInt(3, movie.getBudget());
            statement.setTimestamp(4, movie.getReleaseDateTimestamp());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    /**
     * Creates and returns list of movies existing in database sorted by given
     * parameter (may be ID, title or running time) ascending or descending.
     * Gets proxy connection from database pool, prepares statement on it (by
     * SQL-string) and gets result set with all movies in database.
     * @param sortedBy sorting parameter (ID, title or running time).
     * @param sortType type of sorting (ascending/descending).
     * @return sorted list of movies.
     * @throws DAOException if {@link SQLException} was caught.
     */
    @Override
    public List<Movie> getAllMoviesSorted(String sortedBy, String sortType) throws DAOException {
        PreparedStatement statement = null;
        List<Movie> movies = new ArrayList<>();
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            String sql = getSortingSQL(sortedBy, sortType);
            statement = connection.prepareStatement(sql);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Movie movie = getNextMovie(set);
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
        return movies;
    }

    /**
     * Updates information (running time, budget or release date) about movie with
     * given ID according to given parameters. Gets proxy connection from database
     * pool, prepares statement on it (by SQL-string) and executes.
     * @param movieId ID of movie to edit.
     * @param runningTime new value of running time.
     * @param budget new value of budget.
     * @param releaseDate new release date.
     * @throws DAOException if {@link SQLException} was caught.
     */
    @Override
    public void editMovie(String movieId, int runningTime, int budget, Date releaseDate) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(EDIT_MOVIE_SQL);
            statement.setInt(1, runningTime);
            statement.setInt(2, budget);
            statement.setTimestamp(3, new Timestamp(releaseDate.getTime() + 11000 * 1000));
            statement.setString(4, movieId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    /**
     * Deletes movie with given ID from database.
     * @param movieId ID of movie to delete.
     * @throws DAOException if {@link SQLException} was caught.
     */
    @Override
    public void deleteMovie(String movieId) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(DELETE_MOVIE_SQL);
            statement.setString(1, movieId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    private Movie getNextMovie(ResultSet set) throws SQLException {
        MovieBuilder movieBuilder = new MovieBuilder(set.getString(1));
        return movieBuilder.withTitle(set.getString(2))
                .withRunningTime(set.getInt(3))
                .withBudget(set.getInt(4))
                .hasReleaseDate(set.getTimestamp(5))
                .build();
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

    private boolean movieTitleIsUsed(Connection connection, String movieTitle) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GET_MOVIE_BY_TITLE_SQL)) {
            statement.setString(1, movieTitle);
            ResultSet set = statement.executeQuery();
            return set.first();
        }
    }
}
