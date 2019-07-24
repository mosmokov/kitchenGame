package com.wradchuk.game;

import android.os.Bundle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wradchuk.utils.Debug;
import com.wradchuk.utils.PatchedAndroidApplication;
import com.wradchuk.utils.TextureAnimation;

public class TestRoom extends PatchedAndroidApplication implements ApplicationListener {

    public SpriteBatch wBatch; // холст
    public TextureAnimation wTextureAnimation;
    public TextureAnimation wTextureAnimation1;

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
        Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        wBatch = new SpriteBatch();
        //wBatch.maxSpritesInBatch = 1000;

        wTextureAnimation = new TextureAnimation("img/1.jpg", 3, 4, 0.016f);
        wTextureAnimation1 = new TextureAnimation("img/1.jpg", 3, 4, 0.016f);
    }
    @Override public void resize(int width, int height) {
        Gdx.gl20.glViewport(0,0, width, height);
    }
    @Override public void render() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen

        // Get current frame of animation for the current stateTime
        for(int i = 0; i < 6; i++)
        {
            wTextureAnimation.draw(wBatch, wTextureAnimation.currentFrame.getRegionWidth()*i, 10);
            wTextureAnimation1.draw(wBatch, wTextureAnimation.currentFrame.getRegionWidth()*i,
                    10+wTextureAnimation.currentFrame.getRegionHeight());
        }


    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {
        wBatch.dispose();
        wTextureAnimation.walkSheet.dispose();
    }



}