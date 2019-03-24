package command.impl;

import command.Command;
import command.exception.CommandException;
import org.testng.annotations.Test;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class DeleteFeedbackCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_serviceExceptionFromDeleteFeedback_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Command command = new DeleteFeedbackCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(mockRequest.getParameter("feedbackId")).thenReturn("1");
        when(mockRequest.getParameter("alienId")).thenReturn("1");
        doThrow(ServiceException.class).when(service).deleteFeedback(anyString());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test(expectedExceptions = CommandException.class)
    public void execute_serviceExceptionFromRecountAverageRating_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Command command = new DeleteFeedbackCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(mockRequest.getParameter("feedbackId")).thenReturn("1");
        when(mockRequest.getParameter("alienId")).thenReturn("1");
        doNothing().when(service).deleteFeedback(anyString());
        doThrow(ServiceException.class).when(service).recountAverageRating(anyString());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test(expectedExceptions = CommandException.class)
    public void execute_serviceExceptionFromReviewUserStatus_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Command command = new DeleteFeedbackCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(mockRequest.getParameter("feedbackId")).thenReturn("1");
        when(mockRequest.getParameter("alienId")).thenReturn("1");
        doNothing().when(service).deleteFeedback(anyString());
        doNothing().when(service).recountAverageRating(anyString());
        doThrow(ServiceException.class).when(service).reviewUserStatus(anyString());
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
        Command command = new DeleteFeedbackCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(mockRequest.getParameter("feedbackId")).thenReturn("1");
        when(mockRequest.getParameter("alienId")).thenReturn("1");
        doNothing().when(service).deleteFeedback(anyString());
        doNothing().when(service).recountAverageRating(anyString());
        doNothing().when(service).reviewUserStatus(anyString());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }
}