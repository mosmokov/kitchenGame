package com.wradchuk.game;

import android.os.Bundle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.wradchuk.game.screen.Fortune;
import com.wradchuk.game.screen.Recipes;
import com.wradchuk.game.screen.Screen;
import com.wradchuk.game.screen.Showcase;
import com.wradchuk.game.screen.StartGame;
import com.wradchuk.utils.PatchedAndroidApplication;

public class MainMenu extends PatchedAndroidApplication implements ApplicationListener {

    public static int         WIDTH       =    -1;
    public static int         HEIGHT      =    -1;
    public static final int ALL_SCREEN = 4;
    public static Screen[] screens = new Screen[ALL_SCREEN];
    public static int SET_SCREEN = 0; // 0 1 2 3
    public static boolean G_MOVE = false;


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

        screens[0] = new Showcase ("img/mb/pBGShowcase.png" ,     0,    0);
        screens[1] = new Fortune  ("img/mb/pBGFortune.png"  ,   -WIDTH,    0);
        screens[2] = new StartGame("img/mb/pBGStartGame.png",    WIDTH,    0);
        screens[3] = new Recipes  ("img/mb/pBGRecipes.png"  ,     0, -HEIGHT);
    }
    @Override public void resize(int width, int height) {}
    @Override public void render() {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen

        for(int i = 0; i < ALL_SCREEN; i++) screens[0].drawBackground();

    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {
        for(int i = 0; i < ALL_SCREEN; i++) screens[i].dispose();
    }
}
