package com.viktor235.safenote.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * "Composite"
 */
public class CompositeNote extends Note {
    private List<Note> childNotes = new ArrayList<>();

    public CompositeNote(String name) {
        super(name);
    }

    public List<Note> getChilds() {
        return childNotes;
    }

    public void setChilds(List<Note> childNotes) {
        this.childNotes = childNotes;
    }

    public void add(Note note) {
        note.setParent(this);
        childNotes.add(note);
    }

    public void remove(Note note) {
        childNotes.remove(note);
    }

}