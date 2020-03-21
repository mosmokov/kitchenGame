package com.wradchuk.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wradchuk.test.TestScreens;
import com.wradchuk.utils.LogOut;
import com.wradchuk.utils.PatchedAndroidApplication;
import com.wradchuk.utils.Utils;
import com.wradchuk.view.Recipe;

import javax.microedition.khronos.opengles.GL;


public class Core extends Game {
    public InputMultiplexer multiplexer;
    public static PatchedAndroidApplication context                    ; // Контекст приложения
    public float virtualHeight;
    public float virtualWidth;
    private int setWX = -1;
    private int setWY = -1;
    private ShapeRenderer renderer;
    public          SpriteBatch               batch                      ; //
    public          Viewport                  viewport                   ; //
    public          Stage                     stage                      ; //
    public          Skin                      skin                       ; //
    public          BitmapFont                comfortaaRegular           ; //
    public          BitmapFont                montserratAlternatesRegular; //
    public          BitmapFont                pattayaRegular             ; //
    public          BitmapFont                podkovaRegular             ; //
    public float keyboardHeight;
    public float a1 = 0;


    public Core() {}
    public Core(PatchedAndroidApplication _context) {
        context = _context;

    }


    public void create() {
        this.virtualWidth  = 720;
        this.virtualHeight = 1280;

        this.viewport      = new FitViewport(virtualWidth, virtualHeight);
        this.batch         = new SpriteBatch();
        this.renderer      = new ShapeRenderer();



        skin  = new Skin(Gdx.files.internal("gdx/uiskin.json"));

        this.comfortaaRegular            = Utils.setFont("ttf/Comfortaa-Regular.ttf"           , 30);
        this.montserratAlternatesRegular = Utils.setFont("ttf/MontserratAlternates-Regular.ttf", 22);
        this.pattayaRegular              = Utils.setFont("ttf/Pattaya-Regular.ttf"             , 22);
        this.podkovaRegular              = Utils.setFont("ttf/Podkova-Regular.ttf"             , 22);

        skin.add("comfo", comfortaaRegular,             BitmapFont.class);
        skin.add("monts", montserratAlternatesRegular,  BitmapFont.class);
        skin.add("patta", pattayaRegular,               BitmapFont.class);
        skin.add("podko", podkovaRegular,               BitmapFont.class);



        stage = new Stage(viewport);
        multiplexer  = new InputMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);

        this.setScreen(new Recipe(this));


    }

    @Override public void render() {
        super.render();
    }

    @Override public void dispose() {
        Utils.dispose(batch);
        Utils.dispose(renderer);
        Utils.dispose(stage);
        Utils.dispose(comfortaaRegular);
        Utils.dispose(montserratAlternatesRegular);
        Utils.dispose(pattayaRegular);
        Utils.dispose(podkovaRegular);
        //skin.dispose();
    }
    @Override public void resize(int width, int height) {
        viewport.update(width, height, true);
        setWX = (int) viewport.getWorldWidth();
        setWY = (int) viewport.getWorldHeight();
    }
    @Override public void pause() {}
    @Override public void resume() {}

    public void rotateSprite(Sprite s, float _speed) {
        float rotation=s.getRotation();
        rotation-=_speed;
        if(rotation>360) rotation-=360;
        s.setRotation(rotation);
    }
    public void update() {
        viewport.getCamera().update();
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 1, 1);
    }

}