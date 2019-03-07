package dao.exception;

public class InvalidUsernameOrPasswordException extends DAOException {

    public InvalidUsernameOrPasswordException() {
    }

    public InvalidUsernameOrPasswordException(String message) {
        super(message);
    }

    public InvalidUsernameOrPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUsernameOrPasswordException(Throwable cause) {
        super(cause);
    }

    public InvalidUsernameOrPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
