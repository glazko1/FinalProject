package service.exception.edit;

import service.exception.ServiceException;

public class InvalidEditInformationException extends ServiceException {

    static final long serialVersionUID = 1799704336566606085L;

    public InvalidEditInformationException() {
    }

    public InvalidEditInformationException(String message) {
        super(message);
    }

    public InvalidEditInformationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEditInformationException(Throwable cause) {
        super(cause);
    }

    public InvalidEditInformationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
