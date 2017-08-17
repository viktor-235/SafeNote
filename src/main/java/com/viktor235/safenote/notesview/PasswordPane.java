package com.viktor235.safenote.notesview;

import com.viktor235.safenote.Utils;
import com.viktor235.safenote.composite.Note;
import com.viktor235.safenote.delegator.Thingable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;

public class PasswordPane implements INotePane {
    @FXML
    private Pane pane;
    @FXML
    private PasswordField passwordField;

    private Note note;
    private Thingable delegate;

    public PasswordPane(Note note, Thingable delegate) {
        this.note = note;
        this.delegate = delegate;
        Utils.loadFxml("/fxml/notesview/passwordPane.fxml", this);
    }

    public void handleOk(ActionEvent actionEvent) {
        delegate.thing();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    @Override
    public String getTitle() {
        if (note != null)
            return note.getName();
        else
            return null;
    }

    @Override
    public void setTitle(String title) {
        if (note != null)
            note.setName(title);
    }

    @Override
    public Pane getPane() {
        return pane;
    }
}
