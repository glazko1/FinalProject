package service.impl;

import dao.MovieDAO;
import dao.exception.DAOException;
import dao.exception.movie.UsedMovieTitleException;
import dao.impl.MovieSQL;
import entity.Movie;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.exception.ServiceException;
import service.exception.movie.InvalidMovieInformationException;
import service.exception.movie.InvalidMovieTitleException;
import util.validator.MovieInformationValidator;

import java.sql.Date;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieFanTest {

    private MovieFan service = MovieFan.getInstance();
    private MovieDAO movieDAO = mock(MovieSQL.class);
    private MovieInformationValidator validator = mock(MovieInformationValidator.class);

    @BeforeClass
    public void init() {
        service.setMovieDAO(movieDAO);
        service.setValidator(validator);
    }

    @Test(expectedExceptions = InvalidMovieInformationException.class)
    public void addMovie_invalidParameters_InvalidMovieInformationException() throws ServiceException {
        //given
        //when
        when(validator.validate(anyString(), anyString(), anyString())).thenReturn(false);
        service.addMovie("Title", "100", "1000000", Date.valueOf("2000-01-01"));
        //then
        //expecting InvalidMovieInformationException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void addMovie_DAOExceptionFromAddNewMovie_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        when(validator.validate(anyString(), anyString(), anyString())).thenReturn(true);
        doThrow(DAOException.class).when(movieDAO).addNewMovie(any(Movie.class));
        service.addMovie("Title", "100", "1000000", Date.valueOf("2000-01-01"));
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = InvalidMovieTitleException.class)
    public void addMovie_UsedMovieTitleExceptionFromAddNewMovie_InvalidMovieTitleException() throws DAOException, ServiceException {
        //given
        //when
        when(validator.validate(anyString(), anyString(), anyString())).thenReturn(true);
        doThrow(UsedMovieTitleException.class).when(movieDAO).addNewMovie(any(Movie.class));
        service.addMovie("Title", "100", "1000000", Date.valueOf("2000-01-01"));
        //then
        //expecting InvalidMovieTitleException
    }

    @Test
    public void addMovie_validParameters_void() throws DAOException, ServiceException {
        //given
        //when
        when(validator.validate(anyString(), anyString(), anyString())).thenReturn(true);
        doNothing().when(movieDAO).addNewMovie(any(Movie.class));
        service.addMovie("Title", "100", "1000000", Date.valueOf("2000-01-01"));
        //then
    }

    @Test(expectedExceptions = InvalidMovieInformationException.class)
    public void editMovie_invalidParameters_InvalidMovieInformationException() throws ServiceException {
        //given
        //when
        when(validator.validate(anyString(), anyString())).thenReturn(false);
        service.editMovie("1", "100", "1000000", Date.valueOf("2000-01-01"));
        //then
        //expecting InvalidMovieInformationException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void editMovie_DAOExceptionFromEditMovie_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        when(validator.validate(anyString(), anyString())).thenReturn(true);
        doThrow(DAOException.class).when(movieDAO).editMovie(anyString(), anyInt(), anyInt(), any(Date.class));
        service.editMovie("1", "100", "1000000", Date.valueOf("2000-01-01"));
        //then
        //expecting ServiceException
    }

    @Test
    public void editMovie_validParameters_void() throws DAOException, ServiceException {
        //given
        //when
        when(validator.validate(anyString(), anyString())).thenReturn(true);
        doNothing().when(movieDAO).editMovie(anyString(), anyInt(), anyInt(), any(Date.class));
        service.editMovie("1", "100", "1000000", Date.valueOf("2000-01-01"));
        //then
    }
}