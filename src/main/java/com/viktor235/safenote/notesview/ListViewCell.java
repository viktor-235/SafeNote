package com.viktor235.safenote.notesview;

import com.viktor235.safenote.composite.Note;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.ListCell;

public class ListViewCell extends ListCell<Note> {
    private DoubleBinding paneWidthProperty;

    public ListViewCell(DoubleBinding paneWidthProperty) {
        this.paneWidthProperty = paneWidthProperty;
    }

    @Override
    protected void updateItem(Note item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            Data data = new Data(item, paneWidthProperty);
            setGraphic(data.getBox());
        }
    }
}