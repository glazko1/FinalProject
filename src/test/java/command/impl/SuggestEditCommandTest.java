package command.impl;

import command.Command;
import command.exception.CommandException;
import org.testng.annotations.Test;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class SuggestEditCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_exceptionFromService_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Command command = new SuggestEditCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("alienId")).thenReturn("1");
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(mockRequest.getParameter("description")).thenReturn("Description");
        doThrow(ServiceException.class).when(service).suggestEdit(anyLong(), anyLong(), anyString());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_validParameters_main() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Command command = new SuggestEditCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("alienId")).thenReturn("1");
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(mockRequest.getParameter("description")).thenReturn("Description");
        doNothing().when(service).suggestEdit(anyLong(), anyLong(), anyString());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }
}