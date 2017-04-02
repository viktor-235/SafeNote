package com.viktor235.safenote.composite;

/**
 * "Leaf"
 */
public class DefaultNote extends Note {
    private String text;

    public DefaultNote(String name) {
        super(name);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}