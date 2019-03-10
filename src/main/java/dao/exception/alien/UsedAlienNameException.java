package dao.exception.alien;

import dao.exception.DAOException;

public class UsedAlienNameException extends DAOException {

    public UsedAlienNameException() {
    }

    public UsedAlienNameException(String message) {
        super(message);
    }

    public UsedAlienNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsedAlienNameException(Throwable cause) {
        super(cause);
    }

    public UsedAlienNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
