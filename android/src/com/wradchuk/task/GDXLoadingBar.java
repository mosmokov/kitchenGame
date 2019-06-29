package com.wradchuk.task;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wradchuk.main.Launcher;

public class GDXLoadingBar  {
    SpriteBatch batch;
    Texture img;
    Texture img1[] = new Texture[11];

    AssetManager assetManager;
    Texture loadingBarBackground;
    Texture loadingBarProgress;

    TextureRegion loadingBarProgressStart;
    TextureRegion loadingBarProgressBody;
    TextureRegion loadingBarProgressEnd;

    private void loadAssets(){

        // load synchronously
        assetManager.load("img/pBarBack.png", Texture.class);
        assetManager.load("img/pBarLoading.png", Texture.class);
        assetManager.load("img/pBackground.png", Texture.class);
        assetManager.finishLoading();

        // load asynchronously
        for(int i = 0; i <img1.length; i++) {
            assetManager.load("img/"+(i+1)+".png", Texture.class);

            //Debug.treadSleep(100);
        }




    }


    public void create () {
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        this.loadAssets();
        img = assetManager.get("img/pBackground.png", Texture.class);
        loadingBarBackground = assetManager.get("img/pBarBack.png", Texture.class);
        loadingBarProgress = assetManager.get("img/pBarLoading.png",Texture.class);

        // bar width 489px = Start 20px Body 449px End 20px
        loadingBarProgressStart = new TextureRegion(loadingBarProgress, 0, 0, 20, loadingBarProgress.getHeight());
        loadingBarProgressBody = new TextureRegion(loadingBarProgress, 20, 0, 449,loadingBarProgress.getHeight());
        loadingBarProgressEnd = new TextureRegion(loadingBarProgress, 20+449, 0, 20,loadingBarProgress.getHeight());


        assetManager.finishLoading();

        synchronized (assetManager) {
                for (int i = 0; i < img1.length; i++) {

                   img1[i] = assetManager.get("img/" + (i + 1) + ".png", Texture.class);
                }
        }



    }


    public void render () {
        batch.begin();

        if (assetManager.update()) {
            batch.draw(img,100, 100);
            batch.draw(img1[0],200, 100);
        }


        int initialPosX = 40;
        int initialPosY =19;

        batch.draw(loadingBarBackground,
                initialPosX,
                initialPosY);

        batch.draw(loadingBarProgressStart,
                initialPosX,
                initialPosY);
        batch.draw(loadingBarProgressBody,
                initialPosX+loadingBarProgressStart.getRegionWidth(),
                initialPosY,
                loadingBarProgressBody.getRegionWidth()*assetManager.getProgress(),
                loadingBarProgressBody.getRegionHeight());
        batch.draw(loadingBarProgressEnd,
                initialPosX+loadingBarProgressStart.getRegionWidth()+loadingBarProgressBody.getRegionWidth()*assetManager.getProgress(),
                initialPosY);
        batch.end();
    }

    public void dispose () {
        batch.dispose();
        img.dispose();
        assetManager.dispose();
    }
}