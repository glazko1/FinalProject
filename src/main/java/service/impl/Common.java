package service.impl;

import command.exception.CommandException;
import command.factory.CommandFactory;
import command.impl.SignInCommand;
import service.CommonService;
import service.exception.ServiceException;

public class Common implements CommonService {

    private static final Common INSTANCE = new Common();

    public static Common getInstance() {
        return INSTANCE;
    }

    private Common() {}

    @Override
    public void signIn(String username, String password) throws ServiceException {
        CommandFactory factory = CommandFactory.getInstance();
        try {
            SignInCommand command = new SignInCommand();
            command.setUsername(username);
            command.setPassword(password);
            command.execute();
        } catch (CommandException e) {
            throw new ServiceException(e);
        }
    }
}
