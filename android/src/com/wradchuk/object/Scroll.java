package com.wradchuk.object;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.wradchuk.main.Core;
import com.wradchuk.utils.LogOut;
import com.wradchuk.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Scroll {

    public boolean bought = false;
    public int state = -1;
    public ImageButton scroll_widget; //off on
    public Skin skin;
    public Label title;
    public Label short_info;
    public Label price;
    public int[] cost = new int[3];
    public ImageButton[] cap = new ImageButton[3];
    public boolean openFull = false;


    public Scroll(Skin _skin, JSONObject _scroll_data) {
        this.skin = _skin;

        title = new Label("", skin, "monts", Color.BLACK);
        short_info = new Label("", skin, "podko", Color.BLACK);
        price = new Label("", skin, "patta", Color.BLACK);

        try {
            setBought(_scroll_data.getBoolean("bought"));
            setState(_scroll_data.getInt("state"));
            createButton();

            title.setText(_scroll_data.getString("title"));
            short_info.setText(_scroll_data.getString("short_info"));

            setCost(_scroll_data.getJSONArray("cost"));
            setText();

        } catch(JSONException e) { LogOut.logEx(e.getMessage()); }
        scroll_widget.addActor(title);
        scroll_widget.addActor(short_info);
        scroll_widget.addActor(price);
    }


    public void setBought(boolean _bought) {
        bought = _bought;
    }
    public void setState(int _state) { state = _state; }
    public void createButton() {
        if(bought) scroll_widget = Utils.createImageButton("view/recipe/rec_sheet_activ.png", "view/recipe/rec_sheet_activ.png");
        else scroll_widget = Utils.createImageButton("view/recipe/rec_sheet_deact.png", "view/recipe/rec_sheet_deact.png");
    }

    public ImageButton createButton(String _file) {
        return Utils.createImageButton(_file, _file);
    }
    public void setCost(JSONArray _cost) throws JSONException {
        for (int i = 0; i < _cost.length(); i++) cost[i] = _cost.optInt(i);
    }
    public void setText() {


        switch(state) {
            case 0:  price.setText(" Price: " +cost[0] + " - " + cost[2]); break;
            case 1:  price.setText(" Price: " +cost[0] + " - " + cost[2]); break;
            case 2:  price.setText(" Price: " +cost[0]); break;
            case 3:  price.setText(" Price: " +cost[1]); break;
            case 4:  price.setText(" Price: " +cost[2]); break;
            default: price.setText(" Price: " +cost[0] + " - " + cost[2]);
        }

        title.setPosition(     scroll_widget.getWidth()/2-(title     .getWidth()/2), scroll_widget.getHeight()-20);
        title.setAlignment(Align.center);
        short_info.setPosition(scroll_widget.getWidth()/2-(short_info.getWidth()/2),scroll_widget.getHeight()-60);
        short_info.setAlignment(Align.center);
        price.setPosition(     scroll_widget.getWidth()/2-(price     .getWidth()/2),scroll_widget.getHeight()-100);
        price.setAlignment(Align.center);
    }
    public void setCap() {
        if(state==2) {
           cap[0] = createButton("view/recipe/cap_activ.png");
           cap[1] = createButton("view/recipe/cap_deactiv.png");
           cap[2] = createButton("view/recipe/cap_deactiv.png");
        }
        else if(state==3) {
            cap[0] = createButton("view/recipe/cap_activ.png");
            cap[1] = createButton("view/recipe/cap_activ.png");
            cap[2] = createButton("view/recipe/cap_deactiv.png");
        }
        else if(state==4) {
            cap[0] = createButton("view/recipe/cap_activ.png");
            cap[1] = createButton("view/recipe/cap_activ.png");
            cap[2] = createButton("view/recipe/cap_activ.png");
        }
        else {
            cap[0] = createButton("view/recipe/cap_deactiv.png");
            cap[1] = createButton("view/recipe/cap_deactiv.png");
            cap[2] = createButton("view/recipe/cap_deactiv.png");
        }

        cap[0].setPosition(30, 30);
        cap[1].setPosition(75, 30);
        cap[2].setPosition(120, 30);

        scroll_widget.addActor(cap[0]);
        scroll_widget.addActor(cap[1]);
        scroll_widget.addActor(cap[2]);
    }
}