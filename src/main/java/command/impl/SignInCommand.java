package command.impl;

import command.Command;
import command.exception.CommandException;
import dao.UserDAO;
import dao.exception.DAOException;
import dao.impl.UserSQL;

public class SignInCommand implements Command {

    private String username;
    private String password;

    @Override
    public void execute() throws CommandException {
        UserDAO userDAO = UserSQL.getInstance();
        try {
            userDAO.getUserByUsername(username);
        } catch (DAOException e) {
            throw new CommandException(e);
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
