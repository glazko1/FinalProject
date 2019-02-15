package command.factory;

import command.Command;
import command.exception.CommandException;
import command.impl.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommandFactory {

    private static final CommandFactory INSTANCE = new CommandFactory();

    public static CommandFactory getInstance() {
        return INSTANCE;
    }

    private CommandFactory() {}

    public Command createCommand(String name, HttpServletRequest request, HttpServletResponse response) throws CommandException {
        switch (name) {
            case "signIn":
                return new SignInCommand(request, response);
            case "signUp":
                return new SignUpCommand(request, response);
            case "viewAliens":
                return new ViewAliensCommand(request, response);
            case "addAlien":
                return new AddAlienCommand(request, response);
            case "viewMovies":
                return new ViewMoviesCommand(request, response);
            default:
                break;
        }
        throw new CommandException("No command with name " + name);
    }
}
