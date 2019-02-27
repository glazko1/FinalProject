package entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Edit {

    private long editId;
    private Alien alien;
    private User user;
    private String editText;
    private Timestamp editDateTime;

    public Edit(long editId, Alien alien, User user, String editText, Timestamp editDateTime) {
        this.editId = editId;
        this.alien = alien;
        this.user = user;
        this.editText = editText;
        this.editDateTime = editDateTime;
    }

    public long getEditId() {
        return editId;
    }

    public void setEditId(long editId) {
        this.editId = editId;
    }

    public Alien getAlien() {
        return alien;
    }

    public void setAlien(Alien alien) {
        this.alien = alien;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEditText() {
        return editText;
    }

    public void setEditText(String editText) {
        this.editText = editText;
    }

    public String getEditDateTime() {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(editDateTime);
    }

    public Timestamp getEditTimestamp() {
        return editDateTime;
    }

    public void setEditDateTime(Timestamp editDateTime) {
        this.editDateTime = editDateTime;
    }

    @Override
    public String toString() {
        return "Edit{" +
                "editId=" + editId +
                ", alien=" + alien +
                ", user=" + user +
                ", editText='" + editText + '\'' +
                ", editDateTime=" + editDateTime +
                '}';
    }
}
