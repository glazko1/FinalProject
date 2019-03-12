package service.exception.movie;

import service.exception.ServiceException;

public class InvalidMovieTitleException extends ServiceException {

    public InvalidMovieTitleException() {
    }

    public InvalidMovieTitleException(String message) {
        super(message);
    }

    public InvalidMovieTitleException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMovieTitleException(Throwable cause) {
        super(cause);
    }

    public InvalidMovieTitleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
