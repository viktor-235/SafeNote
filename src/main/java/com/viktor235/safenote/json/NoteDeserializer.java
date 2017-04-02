package com.viktor235.safenote.json;

import com.google.gson.*;
import com.viktor235.safenote.composite.CompositeNote;
import com.viktor235.safenote.composite.DefaultNote;
import com.viktor235.safenote.composite.Note;

import java.lang.reflect.Type;

/**
 * Created by victor.klochkov on 3/21/17.
 */
public class NoteDeserializer implements JsonDeserializer<Note> {
    private static final String CLASSNAME = "type";

    @Override
    public Note deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        final JsonElement typeName = get((JsonObject) json, CLASSNAME);
        final Type actualType = typeForClassName(typeName.getAsString());
        return context.deserialize(json, actualType);
    }

    private Type typeForClassName(String type) {
        try {
            return Class.forName("com.viktor235.safenote.composite." + type);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    private Type typeForName(String type) {
        switch (type) {
            case "CompositeNote":
                return CompositeNote.class;
            case "DefaultNote":
                return DefaultNote.class;
            default:
                throw new JsonParseException(type);
        }
    }

    private JsonElement get(final JsonObject wrapper, String memberName) {
        final JsonElement elem = wrapper.get(memberName);
        if (elem == null)
            throw new JsonParseException("no '" + memberName + "' member found in what was expected to be an interface wrapper");
        return elem;
    }
}
