package com.viktor235.safenote.composite;

import com.viktor235.safenote.json.Convertable;

/**
 * Created by User on 02.04.2017.
 */
public abstract class Note implements Convertable {
    private String name;
    protected transient CompositeNote parent;
    private boolean encrypted = false;

    public Note(String name) {
        this.name = name;
    }

    @Override
    public String getClassName() {
        return this.getClass().getSimpleName();
    }

    public CompositeNote getParent() {
        return parent;
    }

    public void setParent(CompositeNote parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }
}
