package com.viktor235.safenote.notesview;

import com.viktor235.safenote.Utils;
import com.viktor235.safenote.composite.CompositeNote;
import com.viktor235.safenote.composite.Note;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class CompositeNotePane implements INotePane {
    @FXML
    private Pane pane;

    private CompositeNote compositeNote;

    public CompositeNotePane(CompositeNote compositeNote) {
        this.compositeNote = compositeNote;
        Utils.loadFxml("/fxml/notesview/emptyNotePane.fxml", this);
    }

    @Override
    public Note getNote() {
        return compositeNote;
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    @Override
    public void updateNote(String name) {
        compositeNote.setName(name);
    }
}
