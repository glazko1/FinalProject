package command.impl;

import command.Command;
import command.exception.CommandException;
import org.testng.annotations.Test;
import service.AlienSpecialistService;
import service.exception.ServiceException;
import util.checker.UserAccessChecker;
import util.writer.ContentWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class AddAlienCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_exceptionFromService_CommandException() throws ServiceException, CommandException, IOException, ServletException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AlienSpecialistService service = mock(AlienSpecialistService.class);
        ContentWriter writer = mock(ContentWriter.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        ServletContext context = mock(ServletContext.class);
        Part part = mock(Part.class);
        Command command = new AddAlienCommand(service, mockRequest, mockResponse, writer, checker);
        //when
        when(checker.checkStatus(any(), any())).thenReturn(true);
        when(mockRequest.getParameter("alienName")).thenReturn("AlienName");
        when(mockRequest.getParameter("planet")).thenReturn("Planet");
        when(mockRequest.getParameter("description")).thenReturn("Description");
        when(mockRequest.getParameter("movie")).thenReturn("Movie");
        when(mockRequest.getServletContext()).thenReturn(context);
        when(context.getRealPath(anyString())).thenReturn("/");
        when(mockRequest.getPart("photo")).thenReturn(part);
        doThrow(ServiceException.class).when(service).addAlien(anyLong(), anyString(), anyString(), anyString(), anyString(), anyString());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_noAccess_main() throws CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AlienSpecialistService service = mock(AlienSpecialistService.class);
        ContentWriter writer = mock(ContentWriter.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        AddAlienCommand command = new AddAlienCommand(service, mockRequest, mockResponse, writer, checker);
        //when
        when(checker.checkStatus(any(), any())).thenReturn(false);
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }

    @Test
    public void execute_validParameters_main() throws ServiceException, CommandException, IOException, ServletException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AlienSpecialistService service = mock(AlienSpecialistService.class);
        ContentWriter writer = mock(ContentWriter.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        ServletContext context = mock(ServletContext.class);
        Part part = mock(Part.class);
        AddAlienCommand command = new AddAlienCommand(service, mockRequest, mockResponse, writer, checker);
        //when
        when(checker.checkStatus(any(), any())).thenReturn(true);
        when(mockRequest.getParameter("alienName")).thenReturn("AlienName");
        when(mockRequest.getParameter("planet")).thenReturn("Planet");
        when(mockRequest.getParameter("description")).thenReturn("Description");
        when(mockRequest.getParameter("movie")).thenReturn("Movie");
        when(mockRequest.getServletContext()).thenReturn(context);
        when(context.getRealPath(anyString())).thenReturn("/");
        when(mockRequest.getPart("photo")).thenReturn(part);
        doNothing().when(service).addAlien(anyLong(), anyString(), anyString(), anyString(), anyString(), anyString());
        doNothing().when(writer).writeContent(any(InputStream.class), anyString());
        doNothing().when(service).sendNotificationToAll(anyString());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }
}