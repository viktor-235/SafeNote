package com.viktor235.safenote.notesview;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class Data {
    @FXML
    private HBox hBox;
    @FXML
    private Label nameLabel;
    @FXML
    private Label textLabel;
    @FXML
    private MaterialDesignIconView icon;

    public Data() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/notesview/listCellItem.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setText(String text) {
        textLabel.setText(text);
    }

    public void setName(String name) {
        nameLabel.setText(name);
    }

    public void setIconName(String iconName) {
        icon.setGlyphName(iconName);
    }

    public HBox getBox() {
        return hBox;
    }
}
