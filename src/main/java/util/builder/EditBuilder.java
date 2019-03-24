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

    /**
     * Sets alien according to given parameter.
     * @param alien alien about which edit is.
     * @return current builder.
     */
    public EditBuilder aboutAlien(Alien alien) {
        this.alien = alien;
        return this;
    }

    /**
     * Sets user according to given parameter.
     * @param user user who suggested this edit.
     * @return current builder.
     */
    public EditBuilder suggestedBy(User user) {
        this.user = user;
        return this;
    }

    /**
     * Sets edit text according to given parameter.
     * @param editText text of edit.
     * @return current builder.
     */
    public EditBuilder withText(String editText) {
        this.editText = editText;
        return this;
    }

    /**
     * Sets date and time according to given parameter.
     * @param editDateTime date and time of edit.
     * @return current builder.
     */
    public EditBuilder withEditDateTime(Timestamp editDateTime) {
        this.editDateTime = editDateTime;
        return this;
    }

    /**
     * Builds and returns {@link Edit} object in accordance with set earlier
     * parameters (alien, user, edit text, date and time).
     * @return object with information about suggested edit.
     */
    public Edit build() {
        Edit edit = new Edit(editId);
        edit.setAlien(alien);
        edit.setUser(user);
        edit.setEditText(editText);
        edit.setEditDateTime(editDateTime);
        return edit;
    }

    Alien getAlien() {
        return alien;
    }

    User getUser() {
        return user;
    }

    String getEditText() {
        return editText;
    }

    Timestamp getEditDateTime() {
        return editDateTime;
    }
}
