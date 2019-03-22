package filter;

import entity.UserStatus;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(urlPatterns = { "*" }, servletNames = { "MainServlet" })
public class ServletSecurityFilter implements Filter {

    private List<String> adminActions;
    private List<String> movieFanActions;
    private List<String> alienSpecialistActions;
    private List<String> userActions;
    private List<String> accessActions;

    @Override
    public void init(FilterConfig filterConfig) {
        adminActions = new ArrayList<>();
        adminActions.add("changeBanStatus");
        adminActions.add("changeUserStatus");
        adminActions.add("viewAllUsers");
        adminActions.add("viewAllUsersSorted");
        movieFanActions = new ArrayList<>();
        movieFanActions.add("addMovie");
        movieFanActions.add("forwardToNewMovie");
        movieFanActions.add("forwardToEditMovie");
        movieFanActions.add("editMovie");
        movieFanActions.add("deleteMovie");
        alienSpecialistActions = new ArrayList<>();
        alienSpecialistActions.add("addAlien");
        alienSpecialistActions.add("editAlien");
        alienSpecialistActions.add("viewAllSuggestedEdits");
        alienSpecialistActions.add("viewSuggestedEdit");
        alienSpecialistActions.add("acceptEdit");
        alienSpecialistActions.add("rejectEdit");
        alienSpecialistActions.add("deleteAlien");
        alienSpecialistActions.add("forwardToNewAlien");
        alienSpecialistActions.add("forwardToEditAlien");
        userActions = new ArrayList<>();
        userActions.add("viewAllAliens");
        userActions.add("viewAllAliensSorted");
        userActions.add("viewAllMovies");
        userActions.add("viewAllMoviesSorted");
        userActions.add("viewAlien");
        userActions.add("addFeedback");
        userActions.add("viewMovie");
        userActions.add("viewUser");
        userActions.add("forwardToEditUser");
        userActions.add("editUser");
        userActions.add("forwardToChangePassword");
        userActions.add("changePassword");
        userActions.add("mainPage");
        userActions.add("logout");
        userActions.add("viewNotifications");
        userActions.add("deleteFeedback");
        userActions.add("forwardToSuggestEdit");
        userActions.add("suggestEdit");
        accessActions = new ArrayList<>();
        accessActions.add("changePassword");
        accessActions.add("forwardToChangePassword");
        accessActions.add("viewNotifications");
        accessActions.add("forwardToEditUser");
        accessActions.add("editUser");
        accessActions.add("deleteFeedback");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        String currentId = (String) session.getAttribute("userId");
        String userId = request.getParameter("userId");
        UserStatus status = (UserStatus) session.getAttribute("status");
        String action = request.getParameter("button");
        if ((adminActions.contains(action) && status != UserStatus.ADMIN) ||
                (movieFanActions.contains(action) && status != UserStatus.MOVIE_FAN && status != UserStatus.ADMIN) ||
                (alienSpecialistActions.contains(action) && status != UserStatus.ALIEN_SPECIALIST && status != UserStatus.ADMIN) ||
                (userActions.contains(action) && status == null) ||
                (accessActions.contains(action) && !currentId.equals(userId))) {
            request.setAttribute("allowed", false);
            if (status == null) {
                request.setAttribute("redirectTo", "index");
            } else {
                request.setAttribute("redirectTo", "main");
            }
            if ("deleteFeedback".equals(action) && status == UserStatus.ADMIN) {
                request.setAttribute("allowed", true);
            }
        } else {
            request.setAttribute("allowed", true);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}
}
