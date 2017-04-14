package com.viktor235.safenote;

import com.beust.jcommander.JCommander;
import com.viktor235.safenote.args.AppArgs;
import com.viktor235.safenote.composite.DefaultNote;
import com.viktor235.safenote.composite.Note;
import javafx.application.Application;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class Main extends Application {
    private static NotesHandler notesHandler;

    @Override
    public void start(Stage primaryStage) throws Exception {
//        System.out.println(notesHandler.getCodec().encode(notesHandler.getNotes()));

        MainWindowController mainWindow = new MainWindowController(notesHandler);
        mainWindow.show();
    }

    public static void main(String[] args) {
        AppArgs appArgs = new AppArgs();
        JCommander jCommander = new JCommander(appArgs, args);
        jCommander.setProgramName("SafeNote");
        if (appArgs.isHelp()) {
            jCommander.usage();
            return;
        }

        notesHandler = new NotesHandler("notes.json");
        if (appArgs.getNoteName() != null) {
            Note foundNote = notesHandler.findNote(appArgs.getNoteName(), true);
            if (foundNote != null) {
                String noteContent = ((DefaultNote) foundNote).getText();
                System.out.println(noteContent);
                if (appArgs.isToClipboard())
                    Utils.copyToClipboard(noteContent);
            }
            return;
        }

        launch(args);
    }
}