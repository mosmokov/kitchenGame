package com.wradchuk.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.wradchuk.main.Core;
import com.wradchuk.object.Scroll;
import com.wradchuk.widget.WidgetRecipe;
import com.wradchuk.widget.WidgetToolBar;
import com.wradchuk.utils.LogOut;
import com.wradchuk.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Recipe implements Screen, InputProcessor {
    final Core game;
    private final int ALL_CONT = 6;
    private int SET_CONT = 0;

    private int mouse_sx=0, mouse_sy=0            ;   // Текушие координаты миши
    private int mouse_px=0, mouse_py=0            ;   // Предыдущие координаты мыши

    // Bg
    private Sprite recipe_bg;
    private Sprite up_hide_pan;
    private Sprite down_hide_pan;

    private WidgetToolBar toolBar;
    private WidgetRecipe[] widgetRecipes = new WidgetRecipe[6];

    private Sprite namebar_cont;
    private Sprite arrow_left;
    private Sprite arrow_right;
    private Sprite find;


    public Recipe(final Core _game) {
        game = _game;

        recipe_bg      = Utils.createSprite("view/recipe/recipe_bg.png"       , 0, 0);
        up_hide_pan    = Utils.createSprite("view/recipe/up_hide_pan.png"     , 0, 1110);
        down_hide_pan  = Utils.createSprite("view/recipe/down_hide_pan.png"   , 0, 0);

        toolBar = new WidgetToolBar(game);
        toolBar.setPosition(0, 1200);
        toolBar.init();

        namebar_cont   = Utils.createSprite("view/recipe/namebar_cont.png"    , 10, 1110);
        arrow_left     = Utils.createSprite("view/recipe/arrow.png"      , 20, 1125);
        arrow_right    = Utils.createSprite("view/recipe/arrow.png"      , 675, 1125);

        find           =  Utils.createSprite("view/recipe/find.png"           , 610, 1120);


        int id = 0;
        widgetRecipes[id] = new WidgetRecipe(game,"Cont"+id, (720*id), 1090);

        game.multiplexer.addProcessor(this);
    }

    @Override public void show() {}
    @Override public void render(float delta) {
        game.update();

        game.batch.begin();
        recipe_bg.draw(game.batch);
        game.batch.end();

        //game.stage.draw();

        widgetRecipes[0].render();

        game.batch.begin();
            up_hide_pan.draw(game.batch);
        game.batch.end();

        toolBar.render();

        game.batch.begin();
            down_hide_pan.draw(game.batch);
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
        widgetRecipes[0].dispose();

        Utils.dispose(namebar_cont);
        Utils.dispose(arrow_left);
        Utils.dispose(arrow_right);
        Utils.dispose(find);
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
        widgetRecipes[0].moveScroll(mouse_py, mouse_sy);
        LogOut.log("py " + mouse_py + " sy " + mouse_sy);
        mouse_py=mouse_sy;
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