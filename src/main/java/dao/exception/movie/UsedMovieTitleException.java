package dao.exception.movie;

import dao.exception.DAOException;

public class UsedMovieTitleException extends DAOException {

    public UsedMovieTitleException() {
    }

    public UsedMovieTitleException(String message) {
        super(message);
    }

    public UsedMovieTitleException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsedMovieTitleException(Throwable cause) {
        super(cause);
    }

    public UsedMovieTitleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
