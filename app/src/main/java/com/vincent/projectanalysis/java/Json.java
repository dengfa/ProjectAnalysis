package com.vincent.projectanalysis.java;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by dengfa on 17/4/11.
 */

public class Json {

    public static void jsonArrayTest() {
        try {
            //抛异常，但是不会崩溃
            //Method threw 'java.lang.NullPointerException' exception. Cannot evaluate org.json.JSONArray.toString()
            JSONArray jsonArrayNull= new JSONArray("");
            //崩溃
            //String str = null;
            //JSONArray jsonArrayNull2= new JSONArray(str);
            JSONArray jsonArrayEmpty = new JSONArray("[]");
            JSONArray jsonArray = new JSONArray("[{id:1},{id:2},{id:3}]");
            System.out.print(jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
