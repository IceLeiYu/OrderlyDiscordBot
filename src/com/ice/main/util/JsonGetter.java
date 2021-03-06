package com.ice.main.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonGetter {
    public static JSONObject getOrDefault(JSONObject input, String key) {
        if (input.has(key))
            return input.getJSONObject(key);
        else {
            JSONObject data = new JSONObject();
            input.put(key, data);
            return data;
        }
    }

    public static JSONArray getOrDefaultArray(JSONObject input, String key) {
        if (input.has(key))
            return input.getJSONArray(key);
        else {
            JSONArray data = new JSONArray();
            input.put(key, data);
            return data;
        }
    }
}
