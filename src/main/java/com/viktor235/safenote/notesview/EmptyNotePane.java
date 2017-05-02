package com.viktor235.safenote.notesview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class EmptyNotePane extends Pane {
    @FXML
    private Pane pane;

    public EmptyNotePane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/notesview/emptyNotePane.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Pane getPane() {
        return pane;
    }
}
