package service.exception.feedback;

import service.exception.ServiceException;

public class InvalidFeedbackInformationException extends ServiceException {

    public InvalidFeedbackInformationException() {
    }

    public InvalidFeedbackInformationException(String message) {
        super(message);
    }

    public InvalidFeedbackInformationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFeedbackInformationException(Throwable cause) {
        super(cause);
    }

    public InvalidFeedbackInformationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
