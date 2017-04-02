package com.viktor235.safenote.composite;

import com.viktor235.safenote.json.Convertable;

/**
 * Created by User on 02.04.2017.
 */
public class Note implements Convertable {
    private String name;
    protected transient Note parent;

    public Note(String name) {
        this.name = name;
    }

    @Override
    public String getClassName() {
        return this.getClass().getSimpleName();
    }

    public Note getParent() {
        return parent;
    }

    public void setParent(Note parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
