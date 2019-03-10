package service.exception.user;

import service.exception.ServiceException;

public class BannedUserException extends ServiceException {

    public BannedUserException() {
    }

    public BannedUserException(String message) {
        super(message);
    }

    public BannedUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public BannedUserException(Throwable cause) {
        super(cause);
    }

    public BannedUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
