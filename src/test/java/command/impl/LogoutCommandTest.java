package command.impl;

import command.Command;
import command.exception.CommandException;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class LogoutCommandTest {

    @Test
    public void execute_validParameters_index() throws CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        HttpSession mockSession = mock(HttpSession.class);
        Command command = new LogoutCommand(mockRequest, mockResponse);
        //when
        when(mockRequest.getSession()).thenReturn(mockSession);
        String result = command.execute();
        //then
        assertEquals(result, "index");
    }
}