package com.wradchuk.game;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.wradchuk.main.Launcher;

public class Menu  {

    public boolean mComplete = false;
    private SpriteBatch mSpriteBatchGraphic;
    private Texture mTextureBackground;
    private Sprite mSpriteBackground;

    public Menu() {}
    public void addGraphics() {
        mSpriteBatchGraphic = new SpriteBatch();
    }
    public void setTextureBackground(String path) {
        mTextureBackground = new Texture(path);
    }
    public void setSpriteBackground () {
        mSpriteBackground = new Sprite(mTextureBackground);
    }
    public Texture getTextureBackground() {
        return mTextureBackground;
    }
    public Sprite getSpriteBackground() {
        return mSpriteBackground;
    }
    public void render() {
        if(mComplete) {
            mSpriteBatchGraphic.begin();
            mSpriteBatchGraphic.draw(mSpriteBackground, 300,200,mSpriteBackground.getWidth(), mSpriteBackground.getHeight());
            mSpriteBatchGraphic.end();


        }

    }
    public void dispose(){
        if(mComplete) {
            mTextureBackground.dispose();
            mSpriteBatchGraphic.dispose();
        }
    }
}