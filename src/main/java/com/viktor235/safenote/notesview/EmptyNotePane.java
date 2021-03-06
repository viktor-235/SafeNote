package com.viktor235.safenote.notesview;

import com.viktor235.safenote.Utils;
import com.viktor235.safenote.composite.Note;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class EmptyNotePane implements INotePane {
    @FXML
    private Pane pane;

    public EmptyNotePane() {
        Utils.loadFxml("/fxml/notesview/emptyNotePane.fxml", this);
    }

    @Override
    public Note getNote() {
        return null;
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    @Override
    public void updateNote(String name) {
    }
}
