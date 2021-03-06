package dao.exception.user;

import dao.exception.DAOException;

public class InvalidUsernameOrPasswordException extends DAOException {

    static final long serialVersionUID = 2546574241390152834L;

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
