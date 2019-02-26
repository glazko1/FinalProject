package command.impl;

import command.exception.CommandException;
import org.testng.annotations.Test;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class AddFeedbackCommandTest {

    @Test
    public void execute_validParameters_main() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        AddFeedbackCommand command = new AddFeedbackCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("alienId")).thenReturn("1");
        when(mockRequest.getParameter("username")).thenReturn("Username");
        when(mockRequest.getParameter("rating")).thenReturn("1");
        when(mockRequest.getParameter("feedbackText")).thenReturn("FeedbackText");
        doNothing().when(service).addFeedback(anyLong(), anyString(), anyInt(), anyString());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }
}