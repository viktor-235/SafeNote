package com.viktor235.safenote.notesview;

import com.viktor235.safenote.composite.DefaultNote;
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

    private DefaultNote defaultNote;

    public DefaultNotePane(DefaultNote defaultNote) {
        this.defaultNote = defaultNote;
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

    public DefaultNote getDefaultNote() {
        return defaultNote;
    }

    public void updateNote() {
//        defaultNote.setName();
        defaultNote.setText(textArea.getText());
    }
}
