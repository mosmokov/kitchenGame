package com.wradchuk.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.wradchuk.main.Core;
import com.wradchuk.widget.WidgetRecipe;
import com.wradchuk.widget.WidgetToolBar;
import com.wradchuk.utils.Utils;


public class Recipe implements Screen, InputProcessor {

    private final Core core;
    private SpriteBatch batch;
    private Stage stage;

    private float mouse_sy=0, mouse_py=0;



    // Bg
    private Sprite recipe_bg;
    private Sprite up_hide_pan;
    private Sprite down_hide_pan;

    private WidgetToolBar toolBar;
    private WidgetRecipe widgetRecipes;

    private Sprite namebar_cont;
    private Sprite arrow_left;
    private Sprite arrow_right;
    private Sprite find;


    public Recipe(Core _core) {
        this.core = _core;
        this.batch = core.setProMatBatch(batch);
        this.stage = new Stage(core.viewport);

        recipe_bg      = Utils.createSprite("view/recipe/recipe_bg.png"       , 0, 0);
        up_hide_pan    = Utils.createSprite("view/recipe/up_hide_pan.png"     , 0, 1110);
        down_hide_pan  = Utils.createSprite("view/recipe/down_hide_pan.png"   , 0, 0);

        toolBar = new WidgetToolBar();
        toolBar.setPosition(0, 1200);
        toolBar.init(core);

        namebar_cont   = Utils.createSprite("view/recipe/namebar_cont.png"    , 10, 1110);
        arrow_left     = Utils.createSprite("view/recipe/arrow.png"      , 20, 1125);
        arrow_right    = Utils.createSprite("view/recipe/arrow.png"      , 675, 1125);

        find           =  Utils.createSprite("view/recipe/find.png"           , 610, 1120);



        widgetRecipes = new WidgetRecipe(core, stage, "Cont0");

        core.multiplexer.addProcessor(this);
        core.multiplexer.addProcessor(stage);



    }


    @Override public void render(float delta) {
        core.update();

        core.drawSprite(recipe_bg);

        widgetRecipes.render(stage);

        core.drawSprite(up_hide_pan);

        toolBar.render(core);

        core.batch.begin();
            down_hide_pan.draw(core.batch);
            namebar_cont .draw(core.batch);
            arrow_left   .draw(core.batch);
            find         .draw(core.batch);
            arrow_right  .draw(core.batch);
        core.batch.end();

    }
    @Override public void resize(int width, int height) {
        core.resize(width, height);
    }
    @Override public void dispose() {
        Utils.dispose(batch);
        Utils.dispose(stage);

        Utils.dispose(recipe_bg);
        Utils.dispose(up_hide_pan);
        Utils.dispose(down_hide_pan);

        toolBar.dispose();

        Utils.dispose(namebar_cont);
        Utils.dispose(arrow_left);
        Utils.dispose(arrow_right);
        Utils.dispose(find);
    }

    @Override public void show() {}
    @Override public void hide() {

    }

    @Override public void pause() {

    }
    @Override public void resume() {

    }


    @Override public boolean keyDown(int keycode) {
        return false;
    }


    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //LogOut.log("px " + screenX + " py: " + screenY);
        mouse_py = Gdx.graphics.getHeight()-screenY;
        return false;
    }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //LogOut.log("sx " + screenX + " sy: " + screenY);
        mouse_sy = Gdx.graphics.getHeight()-screenY;

        if(mouse_py-mouse_sy==0) widgetRecipes.isOpen();

        mouse_py=mouse_sy;

        return false;
    }
    @Override public boolean keyUp(int keycode) {
        return false;
    }
    @Override public boolean keyTyped(char character) {
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