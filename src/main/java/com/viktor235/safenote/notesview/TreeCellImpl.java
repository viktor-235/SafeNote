package com.viktor235.safenote.notesview;

import com.viktor235.safenote.composite.Note;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.scene.control.TreeCell;

public class TreeCellImpl extends TreeCell<Note> {
    private final MaterialIconView FOLDER_ICON;

    {
        final String ICONS_SIZE = "18.0";
        FOLDER_ICON = new MaterialIconView(MaterialIcon.FOLDER_OPEN);
        FOLDER_ICON.setSize(ICONS_SIZE);
    }

    @Override
    public void updateItem(Note item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            Data data = new Data(item, widthProperty().add(-50));
            setGraphic(data.getBox());

//        https://stackoverflow.com/questions/26754768/javafx-treeview-setgraphic-for-different-levels-of-treeitem

//            if (item instanceof CompositeNote) {
//                setGraphic(FOLDER_ICON);
//                setText(getString());
//            } else {
//                Data data = new Data(item, widthProperty().add(-100));
//                setGraphic(data.getBox());
////                setText(getString());
//            }
        }
    }

    private String getString() {
        return getItem() == null ? "" : getItem().getName();
    }
}