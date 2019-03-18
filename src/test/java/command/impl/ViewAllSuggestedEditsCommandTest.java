package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Edit;
import org.testng.Assert;
import org.testng.annotations.Test;
import service.AlienSpecialistService;
import service.exception.ServiceException;
import util.checker.UserAccessChecker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ViewAllSuggestedEditsCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_exceptionFromService_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AlienSpecialistService service = mock(AlienSpecialistService.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        Command command = new ViewAllSuggestedEditsCommand(service, mockRequest, mockResponse, checker);
        //when
        when(checker.checkStatus(any(), any())).thenReturn(true);
        doThrow(ServiceException.class).when(service).viewAllSuggestedEdits();
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_validParameters_suggestedEditTable() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AlienSpecialistService service = mock(AlienSpecialistService.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        List<Edit> edits = (List<Edit>) mock(List.class);
        Command command = new ViewAllSuggestedEditsCommand(service, mockRequest, mockResponse, checker);
        //when
        when(checker.checkStatus(any(), any())).thenReturn(true);
        when(service.viewAllSuggestedEdits()).thenReturn(edits);
        String result = command.execute();
        //then
        Assert.assertEquals(result, "suggested-edit-table");
    }
}