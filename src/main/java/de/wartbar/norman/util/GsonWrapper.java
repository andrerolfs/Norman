package de.wartbar.norman.util;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class GsonWrapper {

    private static com.google.gson.Gson gson = null;

    public static com.google.gson.Gson get() {

        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.excludeFieldsWithoutExposeAnnotation();
            gson = builder.create();
        }

        return gson;
    }

    public static Boolean getAsBoolean(JsonObject object, String name) {
        return object.get(name).getAsBoolean();
    }

    public static String getAsString(JsonObject object, String name) {
        return object.get(name).getAsString();
    }

    public static List<JsonObject> getAsArray(JsonObject object, String name) {
        List<JsonObject> list = new ArrayList<>();
        JsonArray jArray = object.get(name).getAsJsonArray();

        for (JsonElement jElement : jArray) {
            list.add(jElement.getAsJsonObject());
        }

        return list;
    }

    public static JsonObject get(String jsonString) {
        return GsonWrapper.get().fromJson(jsonString, JsonObject.class);
    }
}
