package service.exception.user;

import service.exception.ServiceException;

public class InvalidUserInformationException extends ServiceException {

    static final long serialVersionUID = 1092011219356618035L;

    public InvalidUserInformationException() {
    }

    public InvalidUserInformationException(String message) {
        super(message);
    }

    public InvalidUserInformationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserInformationException(Throwable cause) {
        super(cause);
    }

    public InvalidUserInformationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
