package com.viktor235.safenote.notesview;

import com.viktor235.safenote.Utils;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class EmptyNotePane implements INotePane {
    @FXML
    private Pane pane;

    public EmptyNotePane() {
        Utils.loadFxml("/fxml/notesview/emptyNotePane.fxml", this);
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void setTitle(String title) {
    }

    @Override
    public Pane getPane() {
        return pane;
    }
}
