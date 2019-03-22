package util.builder;

import entity.Alien;
import entity.Edit;
import entity.User;

import java.sql.Timestamp;

public class EditBuilder {

    private String editId;
    private Alien alien;
    private User user;
    private String editText;
    private Timestamp editDateTime;

    public EditBuilder() {
        this("");
    }

    public EditBuilder(String editId) {
        this.editId = editId;
    }

    public EditBuilder aboutAlien(Alien alien) {
        this.alien = alien;
        return this;
    }

    public EditBuilder suggestedBy(User user) {
        this.user = user;
        return this;
    }

    public EditBuilder withText(String editText) {
        this.editText = editText;
        return this;
    }

    public EditBuilder withEditDateTime(Timestamp editDateTime) {
        this.editDateTime = editDateTime;
        return this;
    }

    public Edit build() {
        Edit edit = new Edit(editId);
        edit.setAlien(alien);
        edit.setUser(user);
        edit.setEditText(editText);
        edit.setEditDateTime(editDateTime);
        return edit;
    }
}
