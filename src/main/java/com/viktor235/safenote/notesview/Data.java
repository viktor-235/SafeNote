package com.viktor235.safenote.notesview;

import com.viktor235.safenote.Utils;
import com.viktor235.safenote.composite.DefaultNote;
import com.viktor235.safenote.composite.Note;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class Data {
    @FXML
    private BorderPane pane;
    @FXML
    private Label nameLabel;
    @FXML
    private Label textLabel;
    @FXML
    private MaterialDesignIconView icon;

    private Note note;

    public Data(Note note) {
        this.note = note;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/notesview/listCellItem.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        updateView();
    }

    public BorderPane getBox() {
        return pane;
    }

    public void updateView() {
        nameLabel.setText(note.getName());
        if (note instanceof DefaultNote) {
            textLabel.setText(((DefaultNote) note).getText());
            icon.setGlyphName("FILE_DOCUMENT");
        }
    }

    public void handleCopy() {
        if (note instanceof DefaultNote)
            Utils.copyToClipboard(((DefaultNote) note).getText());
    }
}
