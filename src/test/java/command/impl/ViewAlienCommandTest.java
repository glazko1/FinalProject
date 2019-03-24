package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Alien;
import entity.Feedback;
import javafx.util.Pair;
import org.testng.annotations.Test;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class ViewAlienCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_serviceExceptionFromViewAlienWithFeedbacks_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Command command = new ViewAlienCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("alienId")).thenReturn("1");
        doThrow(ServiceException.class).when(service).viewAlienWithFeedbacks(anyString());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_validParameters_alienPage() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Pair<Alien, List<Feedback>> pair = (Pair<Alien, List<Feedback>>) mock(Pair.class);
        Command command = new ViewAlienCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("alienId")).thenReturn("1");
        when(service.viewAlienWithFeedbacks(anyString())).thenReturn(pair);
        String result = command.execute();
        //then
        assertEquals(result, "alien-page");
    }
}