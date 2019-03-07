package service.impl;

import dao.MovieDAO;
import dao.exception.DAOException;
import dao.impl.MovieSQL;
import entity.Movie;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.exception.ServiceException;
import util.generator.IdGenerator;

import java.sql.Date;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class MovieFanTest {

    private MovieFan service = MovieFan.getInstance();
    private MovieDAO movieDAO = mock(MovieSQL.class);
    private IdGenerator generator = mock(IdGenerator.class);

    @BeforeClass
    public void init() {
        service.setMovieDAO(movieDAO);
        service.setGenerator(generator);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void addMovie_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(movieDAO).addNewMovie(any(Movie.class));
        service.addMovie("Title", 100, 1000000, Date.valueOf("2000-01-01"));
        //then
        //expecting ServiceException
    }

    @Test
    public void addMovie_validParameters_void() throws DAOException, ServiceException {
        //given
        //when
        doNothing().when(movieDAO).addNewMovie(any(Movie.class));
        service.addMovie("Title", 100, 1000000, Date.valueOf("2000-01-01"));
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void editMovie_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(movieDAO).editMovie(anyLong(), anyInt(), anyInt(), any(Date.class));
        service.editMovie(1, 100, 1000000, Date.valueOf("2000-01-01"));
        //then
        //expecting ServiceException
    }

    @Test
    public void editMovie_validParameters_void() throws DAOException, ServiceException {
        //given
        //when
        doNothing().when(movieDAO).editMovie(anyLong(), anyInt(), anyInt(), any(Date.class));
        service.editMovie(1, 100, 1000000, Date.valueOf("2000-01-01"));
        //then
    }
}