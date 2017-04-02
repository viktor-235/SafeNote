package com.viktor235.safenote.json;

import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.Writer;

/**
 * Created by User on 30.03.2017.
 */
public class Codec<T extends Convertable> {

    public final Class<T> type;

    public Codec(Class<T> type) {
        this.type = type;
    }

    public void encodeToWriter(T objectForEncode, Writer writer) {
        new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(type, new ConvertableSerializer())
                .create()
                .toJson(objectForEncode, writer);
    }

    public String encode(T objectForEncode) {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(type, new ConvertableSerializer())
                .create()
                .toJson(objectForEncode);
    }

    public T decode(JsonReader jsonReader) {
        return new GsonBuilder()
                .registerTypeAdapter(type, new ConvertableDeserializer())
                .create()
                .fromJson(jsonReader, type);
    }
}
