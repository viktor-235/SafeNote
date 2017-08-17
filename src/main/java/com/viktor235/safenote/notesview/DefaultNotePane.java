package com.viktor235.safenote.notesview;

import com.viktor235.safenote.Utils;
import com.viktor235.safenote.composite.DefaultNote;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class DefaultNotePane implements INotePane {
    @FXML
    private Pane pane;
    @FXML
    private TextField nameField;
    @FXML
    private TextArea textArea;

    protected DefaultNote defaultNote;

    public DefaultNotePane(DefaultNote defaultNote) {
        this.defaultNote = defaultNote;
        loadFxml();
    }

    protected void loadFxml() {
        Utils.loadFxml("/fxml/notesview/defaultNotePane.fxml", this);
    }

    public void setName(String name) {
        nameField.setText(name);
    }

    public void setContent(String text) {
        textArea.setText(text);
    }

    @Override
    public String getTitle() {
        if (defaultNote != null)
            return defaultNote.getName();
        else
            return null;
    }

    @Override
    public void setTitle(String title) {
        if (defaultNote != null)
            defaultNote.setName(title);
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    public DefaultNote getDefaultNote() {
        return defaultNote;
    }

    public void updateNote() {
        defaultNote.setName(nameField.getText());
        defaultNote.setText(textArea.getText());
    }
}
