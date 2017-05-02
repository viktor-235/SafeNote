package com.viktor235.safenote.json;

import com.google.gson.*;
import com.viktor235.safenote.composite.CompositeNote;

import java.lang.reflect.Type;

/**
 * Created by victor.klochkov on 3/21/17.
 */
public class ConvertableDeserializer<T extends Convertable> implements JsonDeserializer<T> {
    private static final String TYPE = "type";
    private static final String DATA_CLASS_PACKAGE = "com.viktor235.safenote.composite.";

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        final String typeName = getType((JsonObject) json, TYPE);
        final Type actualType = typeForClassName(typeName);
        return context.deserialize(json, actualType);
    }

    private Type typeForClassName(String type) {
        try {
            return Class.forName(DATA_CLASS_PACKAGE + type);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    private String getType(final JsonObject wrapper, String memberName) {
        final JsonElement elem = wrapper.get(memberName);
        if (elem == null)
            return CompositeNote.class.getSimpleName();
//            throw new JsonParseException("no '" + memberName + "' member found in what was expected to be an interface wrapper");
        return elem.getAsString();
    }
}
