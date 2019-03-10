package dao.exception.user;

import dao.exception.DAOException;

public class UsedUsernameException extends DAOException {

    public UsedUsernameException() {
    }

    public UsedUsernameException(String message) {
        super(message);
    }

    public UsedUsernameException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsedUsernameException(Throwable cause) {
        super(cause);
    }

    public UsedUsernameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
