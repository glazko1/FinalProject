package command.factory;

import command.Command;
import command.exception.CommandException;
import command.impl.*;
import service.MovieFanService;
import service.impl.Admin;
import service.impl.AlienSpecialist;
import service.impl.Common;
import service.impl.MovieFan;

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
            case "viewNotifications":
                return new ViewNotificationsCommand(Common.getInstance(), request, response);
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
            case "forwardToSuggestEdit":
                return new ForwardToSuggestEditCommand(Common.getInstance(), request, response);
            case "suggestEdit":
                return new SuggestEditCommand(Common.getInstance(), request, response);
            case "viewAllSuggestedEdits":
                return new ViewAllSuggestedEditsCommand(AlienSpecialist.getInstance(), request, response);
            case "viewSuggestedEdit":
                return new ViewSuggestedEditCommand(AlienSpecialist.getInstance(), request, response);
            case "acceptEdit":
                return new AcceptEditCommand(AlienSpecialist.getInstance(), request, response);
            case "rejectEdit":
                return new RejectEditCommand(AlienSpecialist.getInstance(), request, response);
            case "forwardToNewMovie":
                return new ForwardToNewMovieCommand(request, response);
            case "addMovie":
                return new AddMovieCommand(MovieFan.getInstance(), request, response);
            default:
                break;
        }
        throw new CommandException("No command with name " + name);
    }
}
