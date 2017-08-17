package com.viktor235.safenote;

import javafx.fxml.FXMLLoader;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

/**
 * Created by User on 15.04.2017.
 */
public class Utils {
    public static void copyToClipboard(String text) {
        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public static void loadFxml(String fxmlUrl, Object controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(controller.getClass().getResource(fxmlUrl));
        fxmlLoader.setController(controller);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
