package com.wradchuk.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wradchuk.utils.PatchedAndroidApplication;
import com.wradchuk.utils.Utils;
import com.wradchuk.view.Recipe;


public class Core extends Game {
    public InputMultiplexer multiplexer;
    public static PatchedAndroidApplication context                      ; // Контекст приложения
    public float virtualHeight = 1280                                    ; //
    public float virtualWidth  =  720                                    ; //

    public          SpriteBatch               batch                      ; //
    public          Viewport                  viewport                   ; //
    public          Stage                     stage                      ; //
    public          Skin                      skin                       ; //

    public          BitmapFont                comfortaaRegular           ; //
    public          BitmapFont                montserratAlternatesRegular; //
    public          BitmapFont                pattayaRegular             ; //
    public          BitmapFont                podkovaRegular             ; //


    public Core() {}
    public Core(PatchedAndroidApplication _context) {this.context = _context;}


    public void create() {
        this.viewport      = new FitViewport(virtualWidth, virtualHeight);

        this.batch         = new SpriteBatch();
        this.stage = new Stage(viewport);

        this.skin  = new Skin(Gdx.files.internal("gdx/uiskin.json"));

        this.comfortaaRegular            = Utils.setFont("ttf/Comfortaa-Regular.ttf"           , 30);
        this.montserratAlternatesRegular = Utils.setFont("ttf/MontserratAlternates-Regular.ttf", 22);
        this.pattayaRegular              = Utils.setFont("ttf/Pattaya-Regular.ttf"             , 22);
        this.podkovaRegular              = Utils.setFont("ttf/Podkova-Regular.ttf"             , 22);

        skin.add("comfo", comfortaaRegular,             BitmapFont.class);
        skin.add("monts", montserratAlternatesRegular,  BitmapFont.class);
        skin.add("patta", pattayaRegular,               BitmapFont.class);
        skin.add("podko", podkovaRegular,               BitmapFont.class);


        this.multiplexer  = new InputMultiplexer();

        Gdx.input.setInputProcessor(multiplexer);
        this.setScreen(new Recipe(this));
    }

    @Override public void render() {super.render();}
    @Override public void dispose() {
        Utils.dispose(batch);
        Utils.dispose(stage);
        Utils.dispose(comfortaaRegular);
        Utils.dispose(montserratAlternatesRegular);
        Utils.dispose(pattayaRegular);
        Utils.dispose(podkovaRegular);
    }
    @Override public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}


    public void rotateSprite(Sprite s, float _speed) {
        float rotation=s.getRotation();
        rotation-=_speed;
        if(rotation>360) rotation-=360;
        s.setRotation(rotation);
    }

    public void drawSprite(Sprite _sprite) {
        batch.begin();
        _sprite.draw(batch);
        batch.end();
    }
    public void update() {
        viewport.getCamera().update();
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 1, 1);
    }

    public SpriteBatch setProMatBatch(SpriteBatch _batch) {
        _batch = new SpriteBatch();
        _batch.setProjectionMatrix(viewport.getCamera().combined);
        return _batch;
    }

}