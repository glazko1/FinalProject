package command.factory;

import command.Command;
import command.exception.CommandException;
import command.impl.AcceptEditCommand;
import command.impl.AddAlienCommand;
import command.impl.AddFeedbackCommand;
import command.impl.AddMovieCommand;
import command.impl.ChangeBanStatusCommand;
import command.impl.ChangePasswordCommand;
import command.impl.ChangeUserStatusCommand;
import command.impl.DeleteAlienCommand;
import command.impl.DeleteFeedbackCommand;
import command.impl.EditAlienCommand;
import command.impl.EditMovieCommand;
import command.impl.EditUserCommand;
import command.impl.ForwardToChangePasswordCommand;
import command.impl.ForwardToEditAlienCommand;
import command.impl.ForwardToEditMovieCommand;
import command.impl.ForwardToEditUserCommand;
import command.impl.ForwardToNewAlienCommand;
import command.impl.ForwardToNewMovieCommand;
import command.impl.ForwardToRestorePasswordCommand;
import command.impl.ForwardToSuggestEditCommand;
import command.impl.LogoutCommand;
import command.impl.RedirectToMainPageCommand;
import command.impl.RejectEditCommand;
import command.impl.RestorePasswordCommand;
import command.impl.SignInCommand;
import command.impl.SignUpCommand;
import command.impl.SuggestEditCommand;
import command.impl.ViewAlienCommand;
import command.impl.ViewAllAliensCommand;
import command.impl.ViewAllAliensSortedCommand;
import command.impl.ViewAllMoviesCommand;
import command.impl.ViewAllMoviesSortedCommand;
import command.impl.ViewAllSuggestedEditsCommand;
import command.impl.ViewAllUsersCommand;
import command.impl.ViewMovieCommand;
import command.impl.ViewNotificationsCommand;
import command.impl.ViewSuggestedEditCommand;
import command.impl.ViewUserCommand;

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
            case "forwardToEditUser":
                return new ForwardToEditUserCommand(request, response);
            case "editUser":
                return new EditUserCommand(request, response);
            case "forwardToChangePassword":
                return new ForwardToChangePasswordCommand(request, response);
            case "changePassword":
                return new ChangePasswordCommand(request, response);
            case "mainPage":
                return new RedirectToMainPageCommand(request, response);
            case "logout":
                return new LogoutCommand(request, response);
            case "viewNotifications":
                return new ViewNotificationsCommand(request, response);
            case "forwardToRestorePassword":
                return new ForwardToRestorePasswordCommand(request, response);
            case "restorePassword":
                return new RestorePasswordCommand(request, response);
            case "deleteFeedback":
                return new DeleteFeedbackCommand(request, response);
            case "forwardToEditAlien":
                return new ForwardToEditAlienCommand(request, response);
            case "editAlien":
                return new EditAlienCommand(request, response);
            case "viewAllAliensSorted":
                return new ViewAllAliensSortedCommand(request, response);
            case "forwardToSuggestEdit":
                return new ForwardToSuggestEditCommand(request, response);
            case "suggestEdit":
                return new SuggestEditCommand(request, response);
            case "viewAllSuggestedEdits":
                return new ViewAllSuggestedEditsCommand(request, response);
            case "viewSuggestedEdit":
                return new ViewSuggestedEditCommand(request, response);
            case "acceptEdit":
                return new AcceptEditCommand(request, response);
            case "rejectEdit":
                return new RejectEditCommand(request, response);
            case "forwardToNewMovie":
                return new ForwardToNewMovieCommand(request, response);
            case "addMovie":
                return new AddMovieCommand(request, response);
            case "deleteAlien":
                return new DeleteAlienCommand(request, response);
            case "viewAllMoviesSorted":
                return new ViewAllMoviesSortedCommand(request, response);
            case "forwardToEditMovie":
                return new ForwardToEditMovieCommand(request, response);
            case "editMovie":
                return new EditMovieCommand(request, response);
            default:
                break;
        }
        throw new CommandException("No command with name " + name);
    }
}
