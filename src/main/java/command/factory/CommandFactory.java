package command.factory;

import command.Command;
import command.exception.CommandException;
import command.impl.AddAlienCommand;
import command.impl.AddFeedbackCommand;
import command.impl.ChangeBanStatusCommand;
import command.impl.ChangePasswordCommand;
import command.impl.ChangeUserStatusCommand;
import command.impl.DeleteFeedbackCommand;
import command.impl.EditAlienCommand;
import command.impl.EditUserCommand;
import command.impl.ForwardToChangePasswordCommand;
import command.impl.ForwardToEditAlienCommand;
import command.impl.ForwardToEditUserCommand;
import command.impl.ForwardToNewAlienCommand;
import command.impl.ForwardToRestorePasswordCommand;
import command.impl.LogoutCommand;
import command.impl.RedirectToMainPageCommand;
import command.impl.RestorePasswordCommand;
import command.impl.SignInCommand;
import command.impl.SignUpCommand;
import command.impl.ViewAlienCommand;
import command.impl.ViewAllAliensCommand;
import command.impl.ViewAllAliensSortedCommand;
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
                return new SignInCommand(Common.getInstance(), request, response);
            case "signUp":
                return new SignUpCommand(Common.getInstance(), request, response);
            case "viewAllAliens":
                return new ViewAllAliensCommand(Common.getInstance(), request, response);
            case "addAlien":
                return new AddAlienCommand(AlienSpecialist.getInstance(), request, response);
            case "viewAllMovies":
                return new ViewAllMoviesCommand(Common.getInstance(), request, response);
            case "viewAlien":
                return new ViewAlienCommand(Common.getInstance(), request, response);
            case "addFeedback":
                return new AddFeedbackCommand(Common.getInstance(), request, response);
            case "viewMovie":
                return new ViewMovieCommand(Common.getInstance(), request, response);
            case "viewAllUsers":
                return new ViewAllUsersCommand(Admin.getInstance(), request, response);
            case "viewUser":
                return new ViewUserCommand(Common.getInstance(), request, response);
            case "changeBanStatus":
                return new ChangeBanStatusCommand(Admin.getInstance(), request, response);
            case "changeUserStatus":
                return new ChangeUserStatusCommand(Admin.getInstance(), request, response);
            case "forwardToNewAlien":
                return new ForwardToNewAlienCommand(Common.getInstance(), request, response);
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
                return new ForwardToRestorePasswordCommand(request, response);
            case "restorePassword":
                return new RestorePasswordCommand(Common.getInstance(), request, response);
            case "deleteFeedback":
                return new DeleteFeedbackCommand(Common.getInstance(), request, response);
            case "forwardToEditAlien":
                return new ForwardToEditAlienCommand(Common.getInstance(), request, response);
            case "editAlien":
                return new EditAlienCommand(AlienSpecialist.getInstance(), request, response);
            case "viewAllAliensSorted":
                return new ViewAllAliensSortedCommand(Common.getInstance(), request, response);
            default:
                break;
        }
        throw new CommandException("No command with name " + name);
    }
}
