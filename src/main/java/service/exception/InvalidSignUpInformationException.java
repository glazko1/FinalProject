package service.exception;

public class InvalidSignUpInformationException extends ServiceException {

    public InvalidSignUpInformationException() {
    }

    public InvalidSignUpInformationException(String message) {
        super(message);
    }

    public InvalidSignUpInformationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSignUpInformationException(Throwable cause) {
        super(cause);
    }

    public InvalidSignUpInformationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
