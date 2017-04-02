package com.viktor235.safenote.json;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by victor.klochkov on 3/21/17.
 */
public class ConvertableDeserializer<T extends Convertable> implements JsonDeserializer<T> {
    private static final String CLASSNAME = "type";

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
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

    private JsonElement get(final JsonObject wrapper, String memberName) {
        final JsonElement elem = wrapper.get(memberName);
        if (elem == null)
            throw new JsonParseException("no '" + memberName + "' member found in what was expected to be an interface wrapper");
        return elem;
    }
}
