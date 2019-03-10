package dao.exception.user;

import dao.exception.DAOException;

public class UsedEmailException extends DAOException {

    public UsedEmailException() {
    }

    public UsedEmailException(String message) {
        super(message);
    }

    public UsedEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsedEmailException(Throwable cause) {
        super(cause);
    }

    public UsedEmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
