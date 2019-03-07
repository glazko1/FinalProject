package service.exception;

public class InvalidSignInInformationException extends ServiceException {

    public InvalidSignInInformationException() {
    }

    public InvalidSignInInformationException(String message) {
        super(message);
    }

    public InvalidSignInInformationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSignInInformationException(Throwable cause) {
        super(cause);
    }

    public InvalidSignInInformationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
