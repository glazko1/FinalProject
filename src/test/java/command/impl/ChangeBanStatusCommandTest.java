package command.impl;

import command.Command;
import command.exception.CommandException;
import org.testng.annotations.Test;
import service.AdminService;
import service.exception.ServiceException;
import service.impl.Admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class ChangeBanStatusCommandTest {

    @Test
    public void execute_validParameters_main() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AdminService service = mock(Admin.class);
        Command command = new ChangeBanStatusCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        doNothing().when(service).changeBanStatus(anyLong());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }
}