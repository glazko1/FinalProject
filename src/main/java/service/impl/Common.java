package service.impl;

import dao.AlienDAO;
import dao.FeedbackDAO;
import dao.MovieDAO;
import dao.UserDAO;
import dao.exception.DAOException;
import dao.impl.AlienSQL;
import dao.impl.FeedbackSQL;
import dao.impl.MovieSQL;
import dao.impl.UserSQL;
import entity.Alien;
import entity.Feedback;
import entity.Movie;
import entity.User;
import service.CommonService;
import service.exception.ServiceException;
import util.hasher.PasswordHashKeeper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;
import java.util.Random;

public class Common implements CommonService {

    private static final Common INSTANCE = new Common();

    public static Common getInstance() {
        return INSTANCE;
    }

    private Common() {}

    private static final String EMAIL_FORMAT_REGEX = "[a-z][[a-z][0-9][-][_]]{3,15}[@][a-z]{2,10}[.][a-z]{2,4}";
    private UserDAO userDAO = UserSQL.getInstance();
    private AlienDAO alienDAO = AlienSQL.getInstance();
    private MovieDAO movieDAO = MovieSQL.getInstance();
    private FeedbackDAO feedbackDAO = FeedbackSQL.getInstance();

    @Override
    public void signIn(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            PasswordHashKeeper keeper = PasswordHashKeeper.getInstance();
            String encoded = keeper.generateHash(password);
            User user = userDAO.getUserByUsernameAndPassword(username, encoded);
            response.setHeader("username", user.getUsername());
            response.setIntHeader("status", user.getStatusId());
            response.setHeader("firstName", user.getFirstName());
            response.setHeader("lastName", user.getLastName());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void signUp(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String username = request.getParameter("newUsername");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String birthDate = request.getParameter("year") + "-" +
                request.getParameter("month") + "-" +
                request.getParameter("day");
        String password = request.getParameter("newPassword");
        String confirmedPassword = request.getParameter("confirmedPassword");
        if (username.length() < 6 ||
                firstName.length() < 2 ||
                lastName.length() < 2 ||
                !email.matches(EMAIL_FORMAT_REGEX) ||
                password.length() < 8 ||
                !password.equals(confirmedPassword)) {
            throw new ServiceException("Information is not valid!");
        }
        PasswordHashKeeper keeper = PasswordHashKeeper.getInstance();
        String encoded = keeper.generateHash(password);
        try {
            Random random = new Random();
            long id = Math.abs(random.nextLong());
            User user = new User(id, username, firstName, lastName, encoded, 4,
                    email, false, Date.valueOf(birthDate));
            userDAO.addNewUser(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void viewAllAliens(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            List<Alien> aliens = alienDAO.getAllAliens();
            request.setAttribute("aliens", aliens);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void viewAllMovies(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            List<Movie> movies = movieDAO.getAllMovies();
            request.setAttribute("movies", movies);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void viewAlien(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            long id = Long.parseLong((String) request.getAttribute("id"));
            Alien alien = alienDAO.getAlienById(id);
            request.setAttribute("alien", alien);
            List<Feedback> feedbacks = feedbackDAO.getFeedbacksByAlienId(id);
            request.setAttribute("feedbacks", feedbacks);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addFeedback(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            Random random = new Random();
            long id = Math.abs(random.nextLong());
            long alienId = Long.parseLong(request.getParameter("alienId"));
            User user = userDAO.getUserByUsername(request.getParameter("username"));
            int rating = Integer.parseInt(request.getParameter("rating"));
            String feedbackText = request.getParameter("feedbackText");
            Date feedbackDateTime = new Date(System.currentTimeMillis());
            Feedback feedback = new Feedback(id, alienId, user, rating, feedbackText, feedbackDateTime);
            feedbackDAO.addNewFeedback(feedback);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void viewMovie(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            long id = Long.parseLong((String) request.getAttribute("id"));
            Movie movie = movieDAO.getMovieById(id);
            request.setAttribute("movie", movie);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
