package command.factory;

import command.Command;
import command.exception.CommandException;
import command.impl.SignInCommand;

public class CommandFactory {

    private static final CommandFactory INSTANCE = new CommandFactory();

    public static CommandFactory getInstance() {
        return INSTANCE;
    }

    private CommandFactory() {}

    public Command createCommand(String name) throws CommandException {
        switch (name) {
            case "signIn":
                return new SignInCommand();
            default:
                break;
        }
        throw new CommandException("No command with name " + name);
    }
}
