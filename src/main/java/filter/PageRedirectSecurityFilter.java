package filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = { "/alien-page", "/alien-table", "/change-password", "/edit-alien",
        "/edit-user", "/index", "/main", "/movie-page", "/new-alien", "/restore-password",
        "/user-page", "/user-table" })
public class PageRedirectSecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI();
        HttpSession session = request.getSession();
        if (session.getAttribute("status") == null && !path.endsWith("index")) {
            response.sendRedirect("index");
        } else if (session.getAttribute("status") != null && !path.endsWith("main")) {
            response.sendRedirect("main");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}
}
