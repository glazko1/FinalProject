package command.factory;

import command.Command;
import command.exception.CommandException;
import command.impl.AddAlienCommand;
import command.impl.AddFeedbackCommand;
import command.impl.ChangeBanStatusCommand;
import command.impl.ChangePasswordCommand;
import command.impl.ChangeUserStatusCommand;
import command.impl.EditUserCommand;
import command.impl.ForwardToChangePasswordCommand;
import command.impl.ForwardToEditUserCommand;
import command.impl.ForwardToNewAlienCommand;
import command.impl.ForwardToRestorePassword;
import command.impl.LogoutCommand;
import command.impl.RedirectToMainPageCommand;
import command.impl.RestorePasswordCommand;
import command.impl.SignInCommand;
import command.impl.SignUpCommand;
import command.impl.ViewAlienCommand;
import command.impl.ViewAllAliensCommand;
import command.impl.ViewAllMoviesCommand;
import command.impl.ViewAllUsersCommand;
import command.impl.ViewMovieCommand;
import command.impl.ViewUserCommand;
import service.impl.Admin;
import service.impl.AlienSpecialist;
import service.impl.Common;

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
                return new AddAlienCommand(AlienSpecialist.getInstance(), request, response);
            case "viewAllMovies":
                return new ViewAllMoviesCommand(request, response);
            case "viewAlien":
                return new ViewAlienCommand(request, response);
            case "addFeedback":
                return new AddFeedbackCommand(Common.getInstance(), request, response);
            case "viewMovie":
                return new ViewMovieCommand(request, response);
            case "viewAllUsers":
                return new ViewAllUsersCommand(request, response);
            case "viewUser":
                return new ViewUserCommand(request, response);
            case "changeBanStatus":
                return new ChangeBanStatusCommand(Admin.getInstance(), request, response);
            case "changeUserStatus":
                return new ChangeUserStatusCommand(Admin.getInstance(), request, response);
            case "forwardToNewAlien":
                return new ForwardToNewAlienCommand(request, response);
            case "forwardToEditUser":
                return new ForwardToEditUserCommand(Common.getInstance(), request, response);
            case "editUser":
                return new EditUserCommand(Common.getInstance(), request, response);
            case "forwardToChangePassword":
                return new ForwardToChangePasswordCommand(Common.getInstance(), request, response);
            case "changePassword":
                return new ChangePasswordCommand(Common.getInstance(), request, response);
            case "mainPage":
                return new RedirectToMainPageCommand(request, response);
            case "logout":
                return new LogoutCommand(request, response);
            case "forwardToRestorePassword":
                return new ForwardToRestorePassword(request, response);
            case "restorePassword":
                return new RestorePasswordCommand(request, response);
            default:
                break;
        }
        throw new CommandException("No command with name " + name);
    }
}
