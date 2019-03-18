package util.checker;

import entity.UserStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserAccessChecker {

    private static final UserAccessChecker INSTANCE = new UserAccessChecker();

    public static UserAccessChecker getInstance() {
        return INSTANCE;
    }

    private UserAccessChecker() {}

    public boolean checkAccess(long requiredId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long id = Long.parseLong(String.valueOf(session.getAttribute("userId")));
        return id == requiredId;
    }

    public boolean checkStatus(UserStatus required, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserStatus current = (UserStatus) session.getAttribute("status");
        return current == required;
    }
}
