package com.wradchuk.object;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.wradchuk.main.Core;
import com.wradchuk.utils.LogOut;
import com.wradchuk.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Scroll {
    public float x, y;
    public boolean bought = false;
    public int state = -1;
    public ImageButton scroll_widget; //off on
    public Label title;
    public Label short_info;
    public Label price;

    public int[] cost = new int[3];
    public Sprite[] cap = new Sprite[3];


    public Scroll(Core _game, JSONObject _scroll_data) {
        title = new Label("", _game.skin);
        short_info = new Label("", _game.skin);
        price = new Label("", _game.skin);
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

    public void setPosition(float _x, float _y) {
        x = _x; y = _y;
        scroll_widget.setPosition(_x, _y);
    }

    public void setBought(boolean _bought) {
        bought = _bought;
    }
    public void setState(int _state) {
        state = _state;
    }
    public void createButton() {
        if(bought) scroll_widget = Utils.createImageButton("view/recipe/rec_sheet_activ.png", "view/recipe/rec_sheet_activ.png");
        else scroll_widget = Utils.createImageButton("view/recipe/rec_sheet_deact.png", "view/recipe/rec_sheet_deact.png");
    }
    public void setCost(JSONArray _cost) throws JSONException {
        for (int i = 0; i < _cost.length(); i++) cost[i] = _cost.optInt(i);

        LogOut.logEx("cost" + cost[0]);
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
           cap[0] = new Sprite(new Texture("view/recipe/cap_activ.png"));
           cap[1] = new Sprite(new Texture("view/recipe/cap_deactiv.png"));
           cap[2] = new Sprite(new Texture("view/recipe/cap_deactiv.png"));
        }
        else if(state==3) {
            cap[0] = new Sprite(new Texture("view/recipe/cap_activ.png"));
            cap[1] = new Sprite(new Texture("view/recipe/cap_activ.png"));
            cap[2] = new Sprite(new Texture("view/recipe/cap_deactiv.png"));
        }
        else if(state==4) {
            cap[0] = new Sprite(new Texture("view/recipe/cap_activ.png"));
            cap[1] = new Sprite(new Texture("view/recipe/cap_activ.png"));
            cap[2] = new Sprite(new Texture("view/recipe/cap_activ.png"));
        }
        else {
            cap[0] = new Sprite(new Texture("view/recipe/cap_deactiv.png"));
            cap[1] = new Sprite(new Texture("view/recipe/cap_deactiv.png"));
            cap[2] = new Sprite(new Texture("view/recipe/cap_deactiv.png"));
        }

        cap[0].setPosition(x+30, y+30);
        cap[1].setPosition(x+75, y+30);
        cap[2].setPosition(x+120, y+30);
    }

    public void addStage(Stage _stage) {
        _stage.addActor(scroll_widget);
    }
    public void render(SpriteBatch _batch) {
        _batch.begin();
        cap[0].draw(_batch);
        cap[1].draw(_batch);
        cap[2].draw(_batch);
        _batch.end();
    }
    public void dispose() {

        cap[0].getTexture().dispose();
        cap[1].getTexture().dispose();
        cap[2].getTexture().dispose();
    }
}