package com.viktor235.safenote.notesview;

import com.viktor235.safenote.composite.Note;
import javafx.scene.layout.Pane;

/**
 * Created by User on 17.07.2017.
 */
public interface INotePane {
    Note getNote();
    Pane getPane();

    void updateNote(String name);
}
