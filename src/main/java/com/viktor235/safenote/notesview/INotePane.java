package com.viktor235.safenote.notesview;

import javafx.scene.layout.Pane;

/**
 * Created by User on 17.07.2017.
 */
public interface INotePane {
    String getTitle();
    void setTitle(String title);

    Pane getPane();
}
