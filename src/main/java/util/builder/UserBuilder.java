package util.builder;

import entity.User;
import entity.UserStatus;

import java.sql.Timestamp;

public class UserBuilder {

    private long userId;
    private String username;
    private String firstName;
    private String lastName;
    private UserStatus status;
    private String email;
    private boolean banned;
    private Timestamp birthDate;

    public UserBuilder(long userId) {
        this.userId = userId;
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder withStatus(UserStatus status) {
        this.status = status;
        return this;
    }

    public UserBuilder hasEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder isBanned(boolean banned) {
        this.banned = banned;
        return this;
    }

    public UserBuilder hasBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
        return this;
    }

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
}
