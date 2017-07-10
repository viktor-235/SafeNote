package com.viktor235.safenote;

import com.google.gson.stream.JsonReader;
import com.viktor235.safenote.composite.CompositeNote;
import com.viktor235.safenote.composite.DefaultNote;
import com.viktor235.safenote.composite.Note;
import com.viktor235.safenote.json.Codec;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.*;

/**
 * Created by User on 01.04.2017.
 */
public class NotesHandler {
    private String fileName;
    private Note notesTree;
    private Codec<Note> codec;
    private static BasicTextEncryptor textEncryptor = new BasicTextEncryptor();

    public NotesHandler(String fileName) {
        this.fileName = fileName;
        codec = new Codec<>(Note.class);

        try (JsonReader jsonReader = new JsonReader(new FileReader(fileName))) {
            notesTree = codec.decode(jsonReader);
            fillParents(notesTree);
        } catch (FileNotFoundException e) {
            notesTree = generateDefaultNotes();
            saveNotes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillParents(Note notesTree) {
        if (notesTree instanceof CompositeNote)
            for (Note note : ((CompositeNote) notesTree).getChilds())
                fillParents(note, (CompositeNote) notesTree);
    }

    private void fillParents(Note note, CompositeNote parent) {
        note.setParent(parent);
        fillParents(note);
    }

    public Note getNotes() {
        return notesTree;
    }

    public Codec<Note> getCodec() {
        return codec;
    }

    public void saveNotes() {
        try (Writer writer = new FileWriter(fileName)) {
            codec.encodeToWriter(notesTree, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Note findNote(String name, boolean excludeComposite) {
        return findNote(notesTree, name, excludeComposite);
    }

    private Note findNote(Note note, String name, boolean excludeComposite) {
        if (note instanceof DefaultNote) {
            if (note.getName().equals(name))
                return note;
            else
                return null;
        }
        if (note instanceof CompositeNote) {
            if (!excludeComposite && note.getName().equals(name))
                return note;
            for (Note subNote : ((CompositeNote) note).getChilds()) {
                Note result = findNote(subNote, name, excludeComposite);
                if (result != null)
                    return result;
            }
        }
        return null;
    }

    public static DefaultNote decryptNote(DefaultNote note, String password) throws EncryptionOperationNotPossibleException {
        textEncryptor.setPassword(password);
        note.setEncrypted(false);
        note.setText(textEncryptor.decrypt(note.getText()));
        return note;
    }

    public static DefaultNote encryptNote(DefaultNote note, String password) {
        textEncryptor.setPassword(password);
        note.setEncrypted(true);
        note.setText(textEncryptor.encrypt(note.getText()));
        return note;
    }

    public static String decryptNoteText(DefaultNote note, String password) throws EncryptionOperationNotPossibleException {
        textEncryptor.setPassword(password);
        return textEncryptor.decrypt(note.getText());
    }

    public static String decryptNoteText(DefaultNote note, char[] password) throws EncryptionOperationNotPossibleException {
        textEncryptor.setPasswordCharArray(password);
        return textEncryptor.decrypt(note.getText());
    }

    public void deleteNote(Note note) {
        CompositeNote parent = note.getParent();
        if (parent != null)
            parent.getChilds().remove(note);
    }

    private Note generateDefaultNotes() {
        CompositeNote rootNote = new CompositeNote("RootNote");

        DefaultNote defaultNote = new DefaultNote("Default Note");
        defaultNote.setText("This is Default Note");
        rootNote.add(defaultNote);

        return rootNote;
    }

    public static CompositeNote generateDebugData() {
        DefaultNote defaultNote1 = new DefaultNote("DefaultNote1");
        defaultNote1.setText("Text");
        DefaultNote defaultNote2 = new DefaultNote("DefaultNote2");
        defaultNote2.setText("Text");
        DefaultNote defaultNote3 = new DefaultNote("DefaultNote3");
        defaultNote3.setText("Text");
        DefaultNote defaultNote4 = new DefaultNote("DefaultNote4");
        defaultNote4.setText("Text");

        defaultNote4 = encryptNote(defaultNote4, "PASS");

        CompositeNote compositeNote1 = new CompositeNote("Root");
        CompositeNote compositeNote2 = new CompositeNote("CompositeNote2");
        CompositeNote compositeNote3 = new CompositeNote("CompositeNote3");

        compositeNote2.add(defaultNote1);
        compositeNote2.add(defaultNote2);
        compositeNote2.add(defaultNote3);

        compositeNote3.add(defaultNote4);

        compositeNote1.add(compositeNote2);
        compositeNote1.add(compositeNote3);

        DefaultNote defaultNote5 = new DefaultNote("DefaultNote5");
        defaultNote5.setText("Text");
        compositeNote1.add(defaultNote5);

        return compositeNote1;
    }

    public static Note generateSimpleNote() {
        return new DefaultNote("DefaultNote1");
    }
}
