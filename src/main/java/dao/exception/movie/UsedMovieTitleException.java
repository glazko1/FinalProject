package dao.exception.movie;

import dao.exception.DAOException;

public class UsedMovieTitleException extends DAOException {

    static final long serialVersionUID = 9078139177711296878L;

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
