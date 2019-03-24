package dao.exception.user;

import dao.exception.DAOException;

public class UsedUsernameException extends DAOException {

    static final long serialVersionUID = 8336554758055622936L;

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
