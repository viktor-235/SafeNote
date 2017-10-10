package com.viktor235.safenote.notesview;

import com.viktor235.safenote.composite.DefaultNote;
import com.viktor235.safenote.delegator.Thingable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;

public class EncriptedDefaultNotePane extends DefaultNotePane {
    @FXML
    private Pane pane;
    @FXML
    private PasswordField passwordField;

    public EncriptedDefaultNotePane(DefaultNote defaultNote, Thingable saveEvent) {
        super(defaultNote, saveEvent);
    }

//    @Override
//    protected void loadFxml() {
//        Utils.loadFxml("/fxml/notesview/passwordPane.fxml", this);
//    }

    public void handleOk(ActionEvent actionEvent) {
//        delegate.thing();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    @Override
    public Pane getPane() {
        return pane;
    }
}
