package entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class Edit {

    private String editId;
    private Alien alien;
    private User user;
    private String editText;
    private Timestamp editDateTime;

    public Edit(String editId) {
        this.editId = editId;
    }

    public String getEditId() {
        return editId;
    }

    public void setEditId(String editId) {
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Edit edit = (Edit) o;
        return Objects.equals(editId, edit.editId) &&
                Objects.equals(alien, edit.alien) &&
                Objects.equals(user, edit.user) &&
                Objects.equals(editText, edit.editText) &&
                Objects.equals(editDateTime, edit.editDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(editId, alien, user, editText, editDateTime);
    }
}
