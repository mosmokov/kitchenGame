package com.wradchuk.task;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GDXLoadingBar  {
    public SpriteBatch batch;
    public AssetManager assetManager;
    private Texture loadingBarBackground;
    private Texture loadingBarProgress;
    public Sprite lbb;
    public Sprite lbp;

    public boolean isLoaded = false;
    public boolean isInit = false;
    public final int MAX = 6;
    public Texture img1[] = new Texture[MAX];
    public Music music;

    private void loadAssets() {
        assetManager.load("img/pBarBack.png"   , Texture.class);
        assetManager.load("img/pBarLoading.png", Texture.class);
        assetManager.finishLoading();
    }
    private void loadAssets1() {
        assetManager.load("img/0.png", Texture.class);
        assetManager.load("img/1.png", Texture.class);
        assetManager.load("img/2.png", Texture.class);
        assetManager.load("img/3.png", Texture.class);
        assetManager.load("img/4.png", Texture.class);
        assetManager.load("img/5.png", Texture.class);
        assetManager.load("muz/1.mp3", Music.class  );
    }


    public GDXLoadingBar () {
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        this.loadAssets();
        loadingBarBackground = assetManager.get("img/pBarBack.png", Texture.class);
        loadingBarProgress = assetManager.get("img/pBarLoading.png",Texture.class);

        lbb = new Sprite(loadingBarBackground, Gdx.graphics.getWidth(),loadingBarBackground.getHeight());
        lbp = new Sprite(loadingBarProgress, Gdx.graphics.getWidth(),loadingBarProgress.getHeight());


        // Загрузка в фоне
        this.loadAssets1();
    }

    public void init() {
        for(int i = 0; i < MAX; i++) img1[i] = assetManager.get("img/"+i+".png", Texture.class);
        music = assetManager.get("muz/1.mp3", Music.class);
    }
    public void loading() {
        if (assetManager.update()) isLoaded = true;
        else isLoaded = false;

        if(isLoaded) {
            init();
            music.play();
            music.setVolume(0.3f);
            isLoaded = false;
            isInit = true;
        }
    }

    public void render () {
        loading();
        if(isInit) {
            batch.begin();
            for (int i = 0; i < MAX; i++) batch.draw(img1[i],i*100,0);
            batch.end();
        }

        batch.begin();
        batch.draw(lbb, 0, 0,
                lbb.getWidth(), lbb.getHeight());
        batch.draw(lbp, 0, 0,
                (assetManager.getProgress()*lbp.getWidth()), lbp.getHeight());
        batch.end();
    }
    public void dispose () {
        batch.dispose();
        loadingBarBackground.dispose();
        loadingBarProgress.dispose();
        for (int i = 0; i < MAX; i++) img1[i].dispose();
        music.dispose();
        assetManager.dispose();
    }
}