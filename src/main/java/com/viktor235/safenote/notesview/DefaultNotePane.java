package com.viktor235.safenote.notesview;

import com.viktor235.safenote.Utils;
import com.viktor235.safenote.composite.DefaultNote;
import com.viktor235.safenote.composite.Note;
import com.viktor235.safenote.delegator.Thingable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class DefaultNotePane implements INotePane {
    @FXML
    private Pane pane;
    @FXML
    private TextArea textArea;

    protected DefaultNote defaultNote;

    public DefaultNotePane(DefaultNote defaultNote, Thingable saveEvent) {
        this.defaultNote = defaultNote;
        loadFxml();
        setContent(defaultNote.getText());

        final BooleanProperty textChanged = new SimpleBooleanProperty(false);

        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(defaultNote.getText())) {
                defaultNote.setText(newValue);
                textChanged.setValue(true);
            }
        });

        textArea.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && textChanged.get()) {
                saveEvent.thing();
                textChanged.setValue(false);
            }
        });
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
