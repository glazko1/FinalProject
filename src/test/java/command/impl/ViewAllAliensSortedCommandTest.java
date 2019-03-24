package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Alien;
import org.testng.annotations.Test;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class ViewAllAliensSortedCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_serviceExceptionFromViewAllAliensSorted_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Command command = new ViewAllAliensSortedCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("sortedBy")).thenReturn("SortedBy");
        when(mockRequest.getParameter("sortType")).thenReturn("SortType");
        doThrow(ServiceException.class).when(service).viewAllAliensSorted(anyString(), anyString());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_validParameters_alienTable() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        List<Alien> aliens = (List<Alien>) mock(List.class);
        Command command = new ViewAllAliensSortedCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("sortedBy")).thenReturn("SortedBy");
        when(mockRequest.getParameter("sortType")).thenReturn("SortType");
        when(service.viewAllAliensSorted(anyString(), anyString())).thenReturn(aliens);
        String result = command.execute();
        //then
        assertEquals(result, "alien-table");
    }
}