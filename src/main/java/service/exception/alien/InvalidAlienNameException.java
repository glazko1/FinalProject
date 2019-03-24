package service.exception.alien;

import service.exception.ServiceException;

public class InvalidAlienNameException extends ServiceException {

    static final long serialVersionUID = 7517079801719305240L;

    public InvalidAlienNameException() {
    }

    public InvalidAlienNameException(String message) {
        super(message);
    }

    public InvalidAlienNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAlienNameException(Throwable cause) {
        super(cause);
    }

    public InvalidAlienNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
