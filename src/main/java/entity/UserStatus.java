package entity;

public enum UserStatus {
    ADMIN(1),
    MOVIE_FAN(2),
    ALIEN_SPECIALIST(3),
    USER(4);

    private int statusId;

    UserStatus(int status) {
        this.statusId = status;
    }

    public int getStatusId() {
        return statusId;
    }
}
