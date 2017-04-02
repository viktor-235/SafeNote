package com.viktor235.safenote.notesview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class DefaultNotePane {
    @FXML
    private Pane pane;
    @FXML
    private TextArea textArea;

    public DefaultNotePane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/notesview/defaultNotePane.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setContent(String text) {
        textArea.setText(text);
    }

    public Pane getPane() {
        return pane;
    }
}
