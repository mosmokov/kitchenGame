package com.wradchuk.utils;

import com.badlogic.gdx.Gdx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyJson {

    public static JSONObject load  (String _file) {
        JSONObject res = null;
        String file = Gdx.files.internal("date/"+_file+".json").readString();
        try {
           res = new JSONObject(file);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }
    public static JSONObject object(String _file, int _id) {
        JSONObject res = null;
        JSONObject kitchen = load(_file);

        try {
            JSONArray array = kitchen.getJSONArray(_file);
            res = array.getJSONObject(_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return res;
    }
    public static JSONArray  array (JSONObject _object, String _array) {
        JSONArray array = null;

        try {
            array = _object.getJSONArray(_array);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return array;
    }


    /**
     * Получает целочисленные данные JSON
     * @param _field название поля
     * @return значение поля в виде int
     */
    public static int     getIntField    (JSONObject _object, String _field) {
        int res = -1;

        try {
            res = _object.getInt(_field);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }
    /**
     * Получает строковые данные JSON
     * @param _field название поля
     * @return значение поля в виде String
     */
    public static String  getStringField (JSONObject _object, String _field) {
        String res = null;

        try {
            res = _object.getString(_field);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }
    /**
     * Получает логические данные JSON
     * @param _field название поля
     * @return значение поля в виде boolean
     */
    public static boolean getBooleanField(JSONObject _object, String _field) {
        boolean res = false;

        try {
            res = _object.getBoolean(_field);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }
    /**
     * Получает массив целочисленных данных JSON
     * @param _field название поля
     * @return значение поля в виде int[]
     */
    public static int[]   getIntArray    (JSONObject _object, String _field) {
        int[] res;

        JSONArray array = MyJson.array(_object, _field);
        int size = array.length();

        res = new int[size];

        for(int i = 0; i < size; i++) {
            try {
                res[i] = array.getInt(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
