package com.wradchuk.main.test;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.wradchuk.utils.sys.LogOut;
import com.wradchuk.utils.sys.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TestData {

    final Game game;

    public final int ALL_SCREEN = 6;
    public JSONObject jsonScreens;

    public ArrayList<ArrayList<Sprite>> screen0 = new ArrayList<ArrayList<Sprite>>();
    public ArrayList<ArrayList<Sprite>> screen1 = new ArrayList<ArrayList<Sprite>>();
    public ArrayList<ArrayList<Sprite>> screen2 = new ArrayList<ArrayList<Sprite>>();
    public ArrayList<ArrayList<Sprite>> screen3 = new ArrayList<ArrayList<Sprite>>();
    public ArrayList<ArrayList<Sprite>> screen4 = new ArrayList<ArrayList<Sprite>>();
    public ArrayList<ArrayList<Sprite>> screen5 = new ArrayList<ArrayList<Sprite>>();


    public int getSize(int _screen) {
        int res = 0;

        try {
            res = jsonScreens.getJSONObject("Screen"+_screen).length();
        } catch (JSONException e) {
            LogOut.logEx(e.getMessage());
        }

        return res;
    }
    public JSONArray getList(int _screen, int _id_list) {
        JSONArray res = null;

        try {
            res = jsonScreens.getJSONObject("Screen"+_screen).getJSONArray("list"+_id_list);
        } catch (JSONException e) {
            LogOut.logEx(e.getMessage());
        }

        return res;
    }

    public void addScreenData(ArrayList<ArrayList<Sprite>> _screen, int _id_screen) {

            int all_list = getSize(_id_screen);
            LogOut.log("SCREEN "+_id_screen+" ALL_LIST " + all_list);

            for(int j = 0; j < all_list; j++) {
                int list_size = getList(_id_screen, j).length();
                LogOut.log("LIST " + j + " LIST_SIZE " + list_size);

                ArrayList<Sprite> list = new ArrayList<>();
                for(int k = 0; k < list_size; k++) {

                    list.add(new Sprite(new Texture("json/"+getList(_id_screen, j).opt(k))));
                    LogOut.log("LIST ADDED SPRITE " + k);
                }
                LogOut.log("LIST "+j+" ADDED");
                _screen.add(list);

            }
        }




    public TestData(Game _game) {
        game = _game;

        jsonScreens = Utils.read_json("json/test.json", Utils.DISCR.INTERNAL);

        addScreenData(screen0, 0);
        //addScreenData(screen1, 1);
        //addScreenData(screen2, 2);
        //addScreenData(screen3, 3);
        //addScreenData(screen4, 4);
        //addScreenData(screen5, 5);


       // LogOut.log("SCREEN +"+screen0.get(0).get(0));
       // LogOut.log("SCREEN +"+screen0.get(0).get(1));
       // LogOut.log("SCREEN +"+screen0.get(0).get(2));
    }



}
