package com.viktor235.safenote.notesview;

import com.viktor235.safenote.Utils;
import com.viktor235.safenote.composite.DefaultNote;
import com.viktor235.safenote.composite.Note;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class DefaultNotePane implements INotePane {
    @FXML
    private Pane pane;
    @FXML
    private TextArea textArea;

    protected DefaultNote defaultNote;

    public DefaultNotePane(DefaultNote defaultNote) {
        this.defaultNote = defaultNote;
        loadFxml();
        setContent(defaultNote.getText());
    }

    protected void loadFxml() {
        Utils.loadFxml("/fxml/notesview/defaultNotePane.fxml", this);
    }

    public void setContent(String text) {
        textArea.setText(text);
    }

    @Override
    public Note getNote() {
        return defaultNote;
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    @Override
    public void updateNote(String name) {
        defaultNote.setName(name);
        defaultNote.setText(textArea.getText());
    }
}
