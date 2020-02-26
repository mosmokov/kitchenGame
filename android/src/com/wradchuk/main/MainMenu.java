package com.wradchuk.main;

import com.badlogic.gdx.Screen;

public class MainMenu implements Screen {
    final Core game;


    public MainMenu(final Core gam) { game = gam; }

    @Override public void show() {

    }
    @Override public void render(float delta) {

        game.update();



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
}
