package com.vincent.projectanalysis.java;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Map;
import java.util.Set;

/**
 * Created by dengfa on 17/4/11.
 */

public class Json {

    public static void jsonArrayTest() {
        try {
            //抛异常，但是不会崩溃
            //Method threw 'java.lang.NullPointerException' exception. Cannot evaluate org.json.JSONArray.toString()
            JSONArray jsonArrayNull = new JSONArray("");
            //崩溃
            //String str = null;
            //JSONArray jsonArrayNull2= new JSONArray(str);
            JSONArray jsonArrayEmpty = new JSONArray("[]");
            JSONArray jsonArray = new JSONArray("[{id:1},{id:2},{id:3}]");
            System.out.print(jsonArray.toString());
            JsonObject jsonObject = new JsonObject();
            Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
            for (Map.Entry<String, JsonElement> next : entrySet) {
                String key = next.getKey();
                JsonElement value = next.getValue();
                if (value.isJsonArray()) {
                    JsonArray asJsonArray = value.getAsJsonArray();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
