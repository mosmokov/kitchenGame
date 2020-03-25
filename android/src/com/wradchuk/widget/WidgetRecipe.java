package com.wradchuk.widget;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.wradchuk.main.Core;
import com.wradchuk.object.Scroll;
import com.wradchuk.utils.LogOut;
import com.wradchuk.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WidgetRecipe {

    public float x;
    public float y;

    public JSONObject jsonScreens;
    public float[] x_scroll = new float[] { 150, 360, 570};
    public ArrayList<Scroll> scrolls = new ArrayList<>();


    public WidgetRecipe(Core _core, SpriteBatch _batch, Stage _stage, String _cont, float _x, float _y) {
        this.x = _x;
        this.y = _y;

        jsonScreens = Utils.read_json("view/recipe/recipe_list.json", Utils.DISCR.INTERNAL);
        try {
            int all = jsonScreens.getJSONObject(_cont).length();
            for(int i = 0; i < all; i++) scrolls.add(new Scroll(_core, jsonScreens.getJSONObject(_cont).getJSONObject("Scroll_"+i)));
        } catch(JSONException e) { LogOut.logEx(e.getMessage()); }

        setPosArrayScroll(scrolls, y, x, 3);

        for(int i = 0; i < scrolls.size(); i++) {
            scrolls.get(i).setCap();
            scrolls.get(i).addStage(_stage);
        }
    }

    public void render(SpriteBatch _batch, Stage _stage) {
        for(int i = 0; i < scrolls.size(); i++) scrolls.get(i).render(_batch);
        _stage.draw();
    }

    public void setPosArrayScroll(ArrayList<Scroll> _scroll, float _y, float _x,int _len) {
        int line = (_scroll.size()/_len+1);
        float height_sprite = _scroll.get(0).scroll_widget.getHeight();

        for(int i = 0; i < line; i++) {

            if(i!=0) _y = _y-(height_sprite+30);
            else _y = _y - height_sprite;

            LogOut.log("New Line " + _y);
            for(int j = 0; j < _len; j++) {

                if((i*_len+j)<_scroll.size()) {
                    ImageButton temp = _scroll.get(i*_len+j).scroll_widget;
                    temp.setPosition(x+x_scroll[j]-(temp.getWidth()/2), _y);
                    _scroll.get(i*_len+j).setPosition(temp.getX(), temp.getY());
                }
            }

        }

    }
    public void moveScroll(float _mouse_py, float _mouse_sy) {
            for(int i = 0; i < scrolls.size(); i++)
                if(_mouse_py-_mouse_sy>0) {
                scrolls.get(i).setPosition(scrolls.get(i).x, scrolls.get(i).y+200);
                scrolls.get(i).setCap();
                } else {
                    scrolls.get(i).setPosition(scrolls.get(i).x, scrolls.get(i).y-200);
                    scrolls.get(i).setCap();
                }

    }



}