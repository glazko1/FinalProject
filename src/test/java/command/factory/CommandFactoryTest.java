package command.factory;

import command.Command;
import command.exception.CommandException;
import command.impl.AddAlienCommand;
import command.impl.SignInCommand;
import command.impl.ViewUserCommand;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class CommandFactoryTest {

    private CommandFactory factory = CommandFactory.getInstance();

    @Test(expectedExceptions = CommandException.class)
    public void createCommand_invalidString_commandException() throws CommandException {
        //given
        //when
        factory.createCommand("noSuchCommand", null, null);
        //then
        //expecting CommandException
    }

    @Test
    public void createCommand_addAlien_correct() throws CommandException {
        //given
        //when
        Command command = factory.createCommand("addAlien", null, null);
        //then
        assertTrue(command instanceof AddAlienCommand);
    }

    @Test
    public void createCommand_signIn_correct() throws CommandException {
        //given
        //when
        Command command = factory.createCommand("signIn", null, null);
        //then
        assertTrue(command instanceof SignInCommand);
    }

    @Test
    public void createCommand_viewUser_correct() throws CommandException {
        //given
        //when
        Command command = factory.createCommand("viewUser", null, null);
        //then
        assertTrue(command instanceof ViewUserCommand);
    }
}