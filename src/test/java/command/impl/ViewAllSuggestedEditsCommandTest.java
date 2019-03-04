package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Edit;
import org.testng.Assert;
import org.testng.annotations.Test;
import service.AlienSpecialistService;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

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
        Command command = new ViewAllSuggestedEditsCommand(service, mockRequest, mockResponse);
        //when
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
        List<Edit> edits = (List<Edit>) mock(List.class);
        Command command = new ViewAllSuggestedEditsCommand(service, mockRequest, mockResponse);
        //when
        when(service.viewAllSuggestedEdits()).thenReturn(edits);
        String result = command.execute();
        //then
        Assert.assertEquals(result, "suggested-edit-table");
    }
}