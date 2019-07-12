package com.wradchuk.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {

    public static int arraySize(JSONObject object, String array_name) {
        int result = 0;

        try {
            result = object.getJSONArray(array_name).length();
        } catch (JSONException e) {
           Debug.debug(e.getMessage());
        }

        return result;
    }
    public static String arrayValue(JSONObject object, String array_name, int elem) {
        String result = "";

        try {
            result = object.getJSONArray(array_name).optString(elem);
        } catch (JSONException e) {
           Debug.debug(e.getMessage());
        }

        return result;
    }
    public static String getValue(JSONObject object, String value) {
        String result = "";

        try {
            result = object.get(value).toString();
        } catch (JSONException e) {
            Debug.debug(e.getMessage());
        }

        return result;
    }
}
