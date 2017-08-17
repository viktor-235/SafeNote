package com.viktor235.safenote.notesview;

import com.viktor235.safenote.Utils;
import com.viktor235.safenote.composite.DefaultNote;
import com.viktor235.safenote.composite.Note;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class Data {
    @FXML
    private Pane pane;
    @FXML
    private Label nameLabel;
    @FXML
    private Label textLabel;
    @FXML
    private MaterialIconView icon;
    @FXML
    private Button copyButton;

    private Note note;

    public Data(Note note, DoubleBinding paneWidthProperty) {
        this.note = note;
        Utils.loadFxml("/fxml/notesview/listCellItem.fxml", this);
        pane.prefWidthProperty().bind(paneWidthProperty);
        nameLabel.maxWidthProperty().bind(paneWidthProperty.add(-70));
        updateView();
    }

    public Pane getBox() {
        return pane;
    }

    public void updateView() {
        nameLabel.setText(note.getName());
        if (note instanceof DefaultNote) {
            String textPreview = extractFirstLine(((DefaultNote) note).getText());
            textLabel.setText(textPreview);
            icon.setGlyphName("INSERT_DRIVE_FILE");
        } else {
            copyButton.setVisible(false);
        }
    }

    private String extractFirstLine(String text) {
        if (text == null)
            return null;
        int endIndex = text.indexOf('\n');
        if (endIndex > 0)
            return text.substring(0, endIndex);
        else
            return text;
    }

    public void handleCopy() {
        if (note instanceof DefaultNote)
            Utils.copyToClipboard(((DefaultNote) note).getText());
    }
}
