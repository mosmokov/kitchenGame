package com.wradchuk.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.wradchuk.main.Core;
import com.wradchuk.object.Scroll;
import com.wradchuk.object.ToolBar;
import com.wradchuk.utils.LogOut;
import com.wradchuk.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Recipe implements Screen, InputProcessor {
    final Core game;
    public JSONObject jsonScreens;
    public float[] x_scroll = new float[] { 150, 360, 570};
    private ArrayList<Scroll> scrolls = new ArrayList<>();

    private int mouse_sx=0, mouse_sy=0            ;   // Текушие координаты миши
    private int mouse_px=0, mouse_py=0            ;   // Предыдущие координаты мыши

    // Bg
    private Sprite recipe_bg;
    private Sprite up_hide_pan;
    private Sprite down_hide_pan;

    private ToolBar toolBar;

    private Sprite namebar_cont;
    private Sprite arrow_left;
    private Sprite arrow_right;
    private Sprite find;


    public Recipe(final Core _game) {
        game = _game;


        recipe_bg      = Utils.createSprite("view/recipe/recipe_bg.png"       , 0, 0);
        up_hide_pan    = Utils.createSprite("view/recipe/up_hide_pan.png"     , 0, 1110);
        down_hide_pan  = Utils.createSprite("view/recipe/down_hide_pan.png"   , 0, 0);

        toolBar = new ToolBar();

        namebar_cont   = Utils.createSprite("view/recipe/namebar_cont.png"    , 10, 1110);
        arrow_left     = Utils.createSprite("view/recipe/arrow.png"      , 20, 1125);
        arrow_right    = Utils.createSprite("view/recipe/arrow.png"      , 675, 1125);
        //arrow_right.setRotation(-90);

        find           =  Utils.createSprite("view/recipe/find.png"           , 610, 1120);



        jsonScreens = Utils.read_json("view/recipe/recipe_list.json", Utils.DISCR.INTERNAL);
        try {
            int all = jsonScreens.getJSONObject("Test0").length();
            for(int i = 0; i < all; i++) scrolls.add(new Scroll(game, jsonScreens.getJSONObject("Test0").getJSONObject("Scroll_"+i)));
        } catch(JSONException e) { LogOut.logEx(e.getMessage()); }

        setPosArrayScroll(scrolls, 1090, 3);

        for(int i = 0; i < scrolls.size(); i++) {
            scrolls.get(i).setCap();
            scrolls.get(i).addStage(game.stage);
        }

        game.multiplexer.addProcessor(this);

    }

    @Override public void show() {}
    @Override public void render(float delta) {
        game.update();

        game.batch.begin();
        recipe_bg.draw(game.batch);
        game.batch.end();

        game.stage.draw();


        for(int i = 0; i < scrolls.size(); i++) scrolls.get(i).render(game.batch);

        game.batch.begin();
        up_hide_pan.draw(game.batch);
        down_hide_pan.draw(game.batch);
        game.batch.end();

        toolBar.render(game.batch);

        game.batch.begin();
        namebar_cont.draw(game.batch);
        arrow_left.draw(game.batch);
        find.draw(game.batch);
        arrow_right.draw(game.batch);
        game.batch.end();



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
        Utils.dispose(recipe_bg);
        Utils.dispose(up_hide_pan);
        Utils.dispose(down_hide_pan);

        toolBar.dispose();

        Utils.dispose(namebar_cont);
        Utils.dispose(arrow_left);
        Utils.dispose(arrow_right);
        Utils.dispose(find);
    }


    public void setPosArrayScroll(ArrayList<Scroll> _scroll, float _y, int _len) {
        int line = (_scroll.size()/_len+1);
        float height_sprite = _scroll.get(0).scroll_widget.getHeight();

        for(int i = 0; i < line; i++) {

            if(i!=0) _y = _y-(height_sprite+30);
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

    public void moveScroll(float _new_y) {
        for(int i = 0; i < scrolls.size(); i++) {
            scrolls.get(i).setPosition(scrolls.get(i).x, scrolls.get(i).y+_new_y);
            scrolls.get(i).setCap();
        }
    }

    @Override public boolean keyDown(int keycode) {
        return false;
    }
    @Override public boolean keyUp(int keycode) {
        return false;
    }
    @Override public boolean keyTyped(char character) {
        return false;
    }
    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        mouse_py = Gdx.graphics.getHeight()-screenY;
        return false;
    }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        mouse_sy = Gdx.graphics.getHeight()-screenY;
        if(mouse_py-mouse_sy>0) moveScroll(scrolls.get(0).scroll_widget.getHeight());
        else moveScroll(-scrolls.get(0).scroll_widget.getHeight());
        return false;
    }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
    @Override public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
    @Override public boolean scrolled(int amount) {
        return false;
    }
}