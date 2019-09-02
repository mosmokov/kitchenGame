package com.wradchuk.game;

import android.os.Bundle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wradchuk.utils.Debug;
import com.wradchuk.utils.PatchedAndroidApplication;
import com.wradchuk.utils.TextureAnimation;

public class MainGame extends PatchedAndroidApplication implements ApplicationListener {

    private int         WIDTH       =    -1;
    private int         HEIGHT      =    -1;
    private SpriteBatch batch;
    public Texture background;
    public Texture povar;
    public TextureAnimation povar1;
    public TextureAnimation pojar;
    public Texture stol;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(this, config);
    }
    @Override protected void onPause() {
        super.onPause();
        Gdx.graphics.requestRendering();
    }

    @Override public void create() {
        WIDTH  = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        Gdx.gl20.glViewport(0, 0, WIDTH, HEIGHT);

        batch = new SpriteBatch();

        background = Debug.setBackground("img/game/background.png", WIDTH, HEIGHT);
        povar = new Texture("img/game/povar.png");
        povar1 = new TextureAnimation("img/game/povar1.png", 5, 2, 0.07f);
        pojar = new TextureAnimation("img/game/pojar.png", 8, 4, 0.2f);
        stol = new Texture("img/game/stol.png");


    }

    @Override public void resize(int width, int height) {

    }

    @Override public void render() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen

        batch.begin();
        batch.draw(background,0,0);
        batch.end();
        povar1.draw(batch,(WIDTH/2)-povar1.getWidth()/2,(HEIGHT/2)-60);
        //batch.draw(povar,(WIDTH/2)-povar.getWidth()/2,(HEIGHT/2)-60);
        batch.begin();
        batch.draw(stol,(WIDTH/2)-stol.getWidth()/2,(HEIGHT/2)-stol.getHeight()/2);
        batch.end();
        pojar.draw(batch,(WIDTH/2)-(pojar.getWidth()/2)-120, (HEIGHT/2)+90);
    }

    @Override public void pause() {

    }

    @Override public void resume() {

    }

    @Override public void dispose() {
        Debug.dispose(background);
        Debug.dispose(stol);
        Debug.dispose(povar);
        Debug.dispose(batch);

    }
}
