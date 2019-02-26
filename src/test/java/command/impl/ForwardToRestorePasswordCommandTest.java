package command.impl;

import command.Command;
import command.exception.CommandException;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

public class ForwardToRestorePasswordCommandTest {

    @Test
    public void execute_validParameters_restorePassword() throws CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        Command command = new ForwardToRestorePasswordCommand(mockRequest, mockResponse);
        //when
        String result = command.execute();
        //then
        assertEquals(result, "restore-password");
    }
}