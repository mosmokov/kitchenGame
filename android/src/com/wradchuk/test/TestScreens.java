package com.wradchuk.test;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.wradchuk.main.Core;
import com.wradchuk.object.Scroll;
import com.wradchuk.utils.LogOut;
import com.wradchuk.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TestScreens implements Screen {
    final Core game;
    public JSONObject jsonScreens;
    public float[] x_scroll = new float[] { 150, 360, 570};
    private ArrayList<Scroll> scrolls = new ArrayList<>();





    public TestScreens(final Core _game) {
        game = _game;

        jsonScreens = Utils.read_json("view/recipe/recipe_list.json", Utils.DISCR.INTERNAL);
        try {
            int all = jsonScreens.getJSONObject("Test0").length();
            for(int i = 0; i < all; i++) scrolls.add(new Scroll(game, jsonScreens.getJSONObject("Test0").getJSONObject("Scroll_"+i)));
        } catch(JSONException e) { LogOut.logEx(e.getMessage()); }

        setPosArrayScroll(scrolls, game.virtualHeight, 3);
        for(int i = 0; i < scrolls.size(); i++) {
            scrolls.get(i).setCap();
            scrolls.get(i).addStage(game.stage);
        }

    }

    @Override public void show() {}
    @Override public void render(float delta) {
        game.update();

        //game.batch.begin();
        //drawArrayListSprite(testData.screen0.get(0));
        //testData.screen0.get(0).get(2).draw(game.batch);
        //game.batch.end();



        game.stage.draw();
        for(int i = 0; i < scrolls.size(); i++) scrolls.get(i).render(game.batch);
    }
    @Override public void resize(int width, int height) {
        game.resize(width, height);
    }
    @Override public void pause() {

    }
    @Override public void resume() {

    }
    @Override public void hide() {

    }
    @Override public void dispose() {

    }


    public void setPosArraySprite1(ArrayList<Sprite> _sprites, float _y, int _len) {
        float ScreeX = game.virtualWidth/(_len+1);
        int line = (_sprites.size()/_len);
        float height_sprite = _sprites.get(0).getHeight();




        for(int i = 0; i < line; i++) {

            if(i!=0) _y = _y-(height_sprite+20);
            else _y = _y - height_sprite;

            LogOut.log("New Line " + _y);
            for(int j = 0; j < _len; j++) {

                if((i*_len+j)<_sprites.size()) {
                    LogOut.log("ID: = "+(i*_len+j) + " y" + _y);
                    Sprite temp = _sprites.get(i*_len+j);
                    temp.setPosition(((j+1)*ScreeX)-temp.getWidth()/2, _y);
                }
            }

        }

    }


    public void setPosArrayScroll(ArrayList<Scroll> _scroll, float _y, int _len) {
        int line = (_scroll.size()/_len+1);
        float height_sprite = _scroll.get(0).scroll_widget.getHeight();

        for(int i = 0; i < line; i++) {

            if(i!=0) _y = _y-(height_sprite+20);
            else _y = _y - height_sprite;

            LogOut.log("New Line " + _y);
            for(int j = 0; j < _len; j++) {

                if((i*_len+j)<_scroll.size()) {
                    LogOut.log("ID: = "+(i*_len+j) + " y" + _y);
                    ImageButton temp = _scroll.get(i*_len+j).scroll_widget;
                    temp.setPosition(x_scroll[j]-(temp.getWidth()/2), _y);
                    _scroll.get(i*_len+j).setPosition(temp.getX(), temp.getY());
                    temp = null;
                }
            }

        }

    }
    public void drawArrayListSprite(ArrayList<Sprite> _sprites) {
        for(int i = 0; i < _sprites.size(); i++) _sprites.get(i).draw(game.batch);
    }
}