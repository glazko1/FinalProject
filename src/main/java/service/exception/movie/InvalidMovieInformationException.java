package service.exception.movie;

import service.exception.ServiceException;

public class InvalidMovieInformationException extends ServiceException {

    static final long serialVersionUID = 1731341242874654177L;

    public InvalidMovieInformationException() {
    }

    public InvalidMovieInformationException(String message) {
        super(message);
    }

    public InvalidMovieInformationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMovieInformationException(Throwable cause) {
        super(cause);
    }

    public InvalidMovieInformationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
