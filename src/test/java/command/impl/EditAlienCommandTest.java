package command.impl;

import command.Command;
import command.exception.CommandException;
import org.testng.annotations.Test;
import service.AlienSpecialistService;
import service.exception.ServiceException;
import service.exception.alien.InvalidAlienInformationException;
import service.impl.AlienSpecialist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class EditAlienCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_serviceExceptionFromEditAlien_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AlienSpecialistService service = mock(AlienSpecialist.class);
        Command command = new EditAlienCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("movie")).thenReturn("Movie");
        when(mockRequest.getParameter("alienId")).thenReturn("1");
        when(mockRequest.getParameter("planet")).thenReturn("Planet");
        when(mockRequest.getParameter("description")).thenReturn("Description");
        doThrow(ServiceException.class).when(service).editAlien(anyString(), anyString(), anyString(), anyString());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_invalidAlienInformationExceptionFromEditAlien_main() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AlienSpecialistService service = mock(AlienSpecialist.class);
        HttpSession session = mock(HttpSession.class);
        Command command = new EditAlienCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("movie")).thenReturn("Movie");
        when(mockRequest.getParameter("alienId")).thenReturn("1");
        when(mockRequest.getParameter("planet")).thenReturn("Planet");
        when(mockRequest.getParameter("description")).thenReturn("Description");
        when(mockRequest.getSession()).thenReturn(session);
        doThrow(InvalidAlienInformationException.class).when(service).editAlien(anyString(), anyString(), anyString(), anyString());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }

    @Test
    public void execute_validParameters_main() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AlienSpecialistService service = mock(AlienSpecialist.class);
        Command command = new EditAlienCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("movie")).thenReturn("Movie");
        when(mockRequest.getParameter("alienId")).thenReturn("1");
        when(mockRequest.getParameter("planet")).thenReturn("Planet");
        when(mockRequest.getParameter("description")).thenReturn("Description");
        doNothing().when(service).editAlien(anyString(), anyString(), anyString(), anyString());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }
}