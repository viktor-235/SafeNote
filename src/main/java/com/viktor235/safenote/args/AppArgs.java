package com.viktor235.safenote.args;

import com.beust.jcommander.Parameter;

/**
 * Created by User on 10.04.2017.
 */
public class AppArgs {
    @Parameter(names = {"-h", "--help"}, help = true)
    private boolean help;

    @Parameter(names = {"-n", "--noteByName"}, description = "Find note by name. Print result.")
    private String noteName;

    @Parameter(names = {"-c", "--toClipboard"}, description = "Copy note to clipboard")
    private boolean toClipboard;

    public boolean isHelp() {
        return help;
    }

    public String getNoteName() {
        return noteName;
    }

    public boolean isToClipboard() {
        return toClipboard;
    }
}
