package com.wradchuk.game;

import android.os.Bundle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wradchuk.game1.RecipesData;
import com.wradchuk.game.object.MyAnimSprite;
import com.wradchuk.game.object.MyImage;
import com.wradchuk.game.object.MySprite;
import com.wradchuk.utils.Debug;
import com.wradchuk.utils.PatchedAndroidApplication;


public class MainGame extends PatchedAndroidApplication implements ApplicationListener {

    public static int         WIDTH       =    -1;
    public static int         HEIGHT      =    -1;
    public RecipesData recipesData;

    public MyImage[] images = new MyImage[4];
    private SpriteBatch batch;


    public InputMultiplexer multiplexer; // Слушатель событий
    public Texture box;



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
        multiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);
       // multiplexer.addProcessor(this);
        Gdx.gl20.glViewport(0, 0, WIDTH, HEIGHT);

        batch = new SpriteBatch();


        images[0] = new MySprite("img/game/background.png", 0, 0);
        images[0].setBatch(batch);

        images[1] = new MyAnimSprite("img/game/povar.png");
        images[1].setBatch(batch);
        images[1].addMap(5, 2, 0.07f).position(0, 75);

        images[2] = new MySprite("img/game/stol.png", 0, 0);
        images[2].setBatch(batch);

        images[3] = new MyAnimSprite("img/game/pojar.png");
        images[3].setBatch(batch);
        images[3].addMap(8, 4, 0.2f).position(-120, 130);

        box = new Texture("img/game/box.png");

        recipesData = new RecipesData("russian", 0);
    }

    @Override public void resize(int width, int height) {
       // WIDTH = width; HEIGHT = height;
       // Gdx.gl20.glViewport(0, 0, width, height);
    }

    @Override public void render() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen

        for (int i = 0; i < 4; i++) {
            images[i].draw();
            images[i].setMouse(Gdx.input.getX(), Gdx.input.getY());
            if (i!=0) images[i].box(box);
        }

    }
    @Override public void pause() {

    }
    @Override public void resume() {

    }
    @Override public void dispose() {
        Debug.dispose(box);
        Debug.dispose(batch);
    }

}
