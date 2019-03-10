package service.exception.alien;

import service.exception.ServiceException;

public class InvalidAlienInformationException extends ServiceException {

    public InvalidAlienInformationException() {
    }

    public InvalidAlienInformationException(String message) {
        super(message);
    }

    public InvalidAlienInformationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAlienInformationException(Throwable cause) {
        super(cause);
    }

    public InvalidAlienInformationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
