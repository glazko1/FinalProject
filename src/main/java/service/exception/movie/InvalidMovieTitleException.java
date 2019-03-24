package service.exception.movie;

import service.exception.ServiceException;

public class InvalidMovieTitleException extends ServiceException {

    static final long serialVersionUID = 4476860599764022637L;

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
