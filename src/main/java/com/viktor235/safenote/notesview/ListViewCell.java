package com.viktor235.safenote.notesview;

import com.viktor235.safenote.composite.DefaultNote;
import com.viktor235.safenote.composite.Note;
import javafx.scene.control.ListCell;

public class ListViewCell extends ListCell<Note> {
    @Override
    protected void updateItem(Note item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            Data data = new Data();
            data.setName(item.getName());
            if (item instanceof DefaultNote) {
                data.setText(((DefaultNote) item).getText());
                data.setIconName("FILE_DOCUMENT");
            }
            setGraphic(data.getBox());
        }
    }
}