package com.viktor235.safenote;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        NotesHandler notesHandler = new NotesHandler("notes.json");
//        System.out.println(notesHandler.getCodec().encode(notesHandler.getNotes()));

        MainWindowController mainWindow = new MainWindowController(notesHandler);
        mainWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}