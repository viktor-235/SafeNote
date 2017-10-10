package com.viktor235.safenote;

import com.beust.jcommander.JCommander;
import com.viktor235.safenote.args.AppArgs;
import com.viktor235.safenote.composite.DefaultNote;
import com.viktor235.safenote.composite.Note;
import javafx.application.Application;
import javafx.stage.Stage;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;

public class Main extends Application {
    private static NotesHandler notesHandler;

    @Override
    public void start(Stage primaryStage) throws Exception {
//        System.out.println(notesHandler.getCodec().encode(notesHandler.getNotes()));

        MainWindowController mainWindow = new MainWindowController(notesHandler);
        mainWindow.show();
    }

    public static void main(String[] args) {
//        DefaultNote note = new DefaultNote("Name");
//        note.setText("Secret!");
//        System.out.println(NotesHandler.encryptNote(note, "123").getText());

        AppArgs appArgs = new AppArgs();
        JCommander jCommander = new JCommander(appArgs, args);
        jCommander.setProgramName("SafeNote");
        if (appArgs.isHelp()) {
            jCommander.usage();
            System.exit(0);
        }

        notesHandler = new NotesHandler("notes.json");

        if (appArgs.getNoteName() != null) {
            Note foundNote = notesHandler.findNote(appArgs.getNoteName(), true);
            if (foundNote != null) {
                DefaultNote foundDefaultNote = (DefaultNote) foundNote;
                String noteContent = null;
                if (foundDefaultNote.isEncrypted()) {
                    JCommander.getConsole().print("Enter the password for \"" + foundNote.getName() + "\":");
                    try {
                        noteContent = NotesHandler.decryptNoteText(foundDefaultNote, JCommander.getConsole().readPassword(false));
                    } catch (EncryptionOperationNotPossibleException e) {
                        JCommander.getConsole().println("Incorrect password!");
                        System.exit(0);
                    } catch (IllegalArgumentException e) {
                        JCommander.getConsole().println("Password cannot be empty");
                        System.exit(0);
                    }
                } else
                    noteContent = (foundDefaultNote).getText();

                if (!appArgs.isToClipboard() || !foundDefaultNote.isEncrypted())
                    JCommander.getConsole().println(noteContent);

                if (appArgs.isToClipboard()) {
                    Utils.copyToClipboard(noteContent);
                    JCommander.getConsole().println("Note copied to clipboard");
                    return;
                }
            }
            System.exit(0);
        }

        launch(args);
    }
}