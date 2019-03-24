package util.builder;

import entity.User;
import entity.UserStatus;

import java.sql.Timestamp;

public class UserBuilder {

    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private UserStatus status;
    private String email;
    private boolean banned;
    private Timestamp birthDate;

    public UserBuilder() {
        this("");
    }

    public UserBuilder(String userId) {
        this.userId = userId;
    }

    /**
     * Sets username according to given parameter.
     * @param username user's login.
     * @return current builder.
     */
    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Sets first name according to given parameter.
     * @param firstName user's first name.
     * @return current builder.
     */
    public UserBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Sets last name according to given parameter.
     * @param lastName user's last name.
     * @return current builder.
     */
    public UserBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Sets status according to given parameter.
     * @param status user's status.
     * @return current builder.
     */
    public UserBuilder withStatus(UserStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Sets e-mail according to given parameter.
     * @param email user's e-mail.
     * @return current builder.
     */
    public UserBuilder hasEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Sets ban status according to given parameter.
     * @param banned user ban status.
     * @return current builder.
     */
    public UserBuilder isBanned(boolean banned) {
        this.banned = banned;
        return this;
    }

    /**
     * Sets birth date according to given parameter.
     * @param birthDate user's birth date.
     * @return current builder.
     */
    public UserBuilder hasBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    /**
     * Builds and returns {@link User} object in accordance with set earlier
     * parameters (username, first name, last name, status, e-mail, ban status
     * and birth date).
     * @return object with information about new user.
     */
    public User build() {
        User user = new User(userId);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setStatus(status);
        user.setEmail(email);
        user.setBanned(banned);
        user.setBirthDate(birthDate);
        return user;
    }

    String getUsername() {
        return username;
    }

    String getFirstName() {
        return firstName;
    }

    String getLastName() {
        return lastName;
    }

    UserStatus getStatus() {
        return status;
    }

    String getEmail() {
        return email;
    }

    boolean isBanned() {
        return banned;
    }

    Timestamp getBirthDate() {
        return birthDate;
    }
}
