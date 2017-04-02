package com.viktor235.safenote;

import com.google.gson.stream.JsonReader;
import com.viktor235.safenote.composite.CompositeNote;
import com.viktor235.safenote.composite.DefaultNote;
import com.viktor235.safenote.composite.Note;
import com.viktor235.safenote.json.Codec;

import java.io.*;

/**
 * Created by User on 01.04.2017.
 */
public class NotesHandler {
    private String fileName;
    private Note notesTree;
    private Codec<Note> codec;

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
                fillParents(note, notesTree);
    }

    private void fillParents(Note note, Note parent) {
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

    private Note generateDefaultNotes() {
        CompositeNote rootNote = new CompositeNote("Root");

        DefaultNote defaultNote = new DefaultNote("DefaultNote");
        defaultNote.setText("This is DefaultNote");
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
}
