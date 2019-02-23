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
            case "viewAllAliens":
                return new ViewAllAliensCommand(request, response);
            case "addAlien":
                return new AddAlienCommand(request, response);
            case "viewAllMovies":
                return new ViewAllMoviesCommand(request, response);
            case "viewAlien":
                return new ViewAlienCommand(request, response);
            case "addFeedback":
                return new AddFeedbackCommand(request, response);
            case "viewMovie":
                return new ViewMovieCommand(request, response);
            case "viewAllUsers":
                return new ViewAllUsersCommand(request, response);
            case "viewUser":
                return new ViewUserCommand(request, response);
            case "changeBanStatus":
                return new ChangeBanStatusCommand(request, response);
            case "changeUserStatus":
                return new ChangeUserStatusCommand(request, response);
            case "forwardToNewAlien":
                return new ForwardToNewAlienCommand(request, response);
            default:
                break;
        }
        throw new CommandException("No command with name " + name);
    }
}
