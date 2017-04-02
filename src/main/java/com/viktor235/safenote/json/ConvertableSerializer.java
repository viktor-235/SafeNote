package com.viktor235.safenote.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by victor.klochkov on 3/21/17.
 */
public class ConvertableSerializer<T extends Convertable> implements JsonSerializer<T> {
    private static final String CLASSNAME = "type";

    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        JsonElement jsonElement = context.serialize(src);
        jsonElement.getAsJsonObject().addProperty(CLASSNAME, src.getClassName());
        return jsonElement;
    }
}
