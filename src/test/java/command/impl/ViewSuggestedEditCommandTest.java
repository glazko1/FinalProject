package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Edit;
import org.testng.annotations.Test;
import service.AlienSpecialistService;
import service.exception.ServiceException;
import service.impl.AlienSpecialist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class ViewSuggestedEditCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_exceptionFromService_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AlienSpecialistService service = mock(AlienSpecialist.class);
        Command command = new ViewSuggestedEditCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("editId")).thenReturn("1");
        doThrow(ServiceException.class).when(service).viewSuggestedEdit(anyLong());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_validParameters_suggestedEditPage() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AlienSpecialistService service = mock(AlienSpecialist.class);
        Edit edit = mock(Edit.class);
        Command command = new ViewSuggestedEditCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("editId")).thenReturn("1");
        when(service.viewSuggestedEdit(anyLong())).thenReturn(edit);
        String result = command.execute();
        //then
        assertEquals(result, "suggested-edit-page");
    }
}