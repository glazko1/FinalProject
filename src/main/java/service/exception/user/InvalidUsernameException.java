package service.exception.user;

import service.exception.ServiceException;

public class InvalidUsernameException extends ServiceException {

    public InvalidUsernameException() {
    }

    public InvalidUsernameException(String message) {
        super(message);
    }

    public InvalidUsernameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUsernameException(Throwable cause) {
        super(cause);
    }

    public InvalidUsernameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
