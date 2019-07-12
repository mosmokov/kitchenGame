package com.wradchuk.task;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wradchuk.utils.Debug;
import com.wradchuk.utils.DownloadFileFromURL;
import com.wradchuk.utils.PatchedAndroidApplication;

public class GDXLoadingBar {

    public static PatchedAndroidApplication context;

    public SpriteBatch batch;

    public DownloadFileFromURL loader;

    public AssetManager assetManager;
    public AssetManager localAssetManager;

    private Texture no_internet;
    private Texture loadingBackground;
    private Texture loadingBarBackground;
    private Texture loadingBarProgress;

    public Sprite lbb;
    public Sprite lbp;

    public boolean isPreload = false;
    public boolean isLoaded = false;
    public boolean isLoad = false;
    public boolean isInit = false;
    public boolean net_or_local = false;

    public Music music;
    public Texture test;



    private void loadAssets() {
        assetManager.load("img/no-internet-connection.jpg"   , Texture.class);
        assetManager.load("img/pBackground.png"   , Texture.class);
        assetManager.load("img/pBarBack.png"   , Texture.class);
        assetManager.load("img/pBarLoading.png", Texture.class);
        assetManager.finishLoading();

        no_internet = assetManager.get("img/no-internet-connection.jpg", Texture.class);
        loadingBackground = assetManager.get("img/pBackground.png", Texture.class);
        loadingBarBackground = assetManager.get("img/pBarBack.png", Texture.class);
        loadingBarProgress = assetManager.get("img/pBarLoading.png",Texture.class);

        lbb = new Sprite(loadingBarBackground, Gdx.graphics.getWidth(),loadingBarBackground.getHeight());
        lbp = new Sprite(loadingBarProgress, Gdx.graphics.getWidth(),loadingBarProgress.getHeight());
    }
    private void loadAssets1() {
        localAssetManager.load("1.mp3", Music.class);
        localAssetManager.load("test.png", Texture.class);
    }
    private void download() {
        if(!isLoad) loader.execute(Debug.HOST);
    }

    public GDXLoadingBar(PatchedAndroidApplication _context) {

        this.context = _context;

        batch = new SpriteBatch();

        assetManager = new AssetManager();
        this.loadAssets();

        loader = new DownloadFileFromURL();


        LocalFileHandleResolver resolver = new LocalFileHandleResolver();
        localAssetManager = new AssetManager(resolver);
        this.loadAssets1();
    }

    public void init() {
        music = localAssetManager.get("1.mp3", Music.class);
        test = localAssetManager.get("test.png", Texture.class);
    }
    public void loading() {

        if (assetManager.update() && localAssetManager.update()) isLoaded = true;
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
        net_or_local = loader.isLoaded;
        if(Debug.isOnline()) {
            batch.begin();

            batch.draw(loadingBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.draw(lbb, 0, 0, lbb.getWidth(), lbb.getHeight());


            if(!net_or_local) {
                if(!isPreload)
                {
                    loader.preloaded();
                    isPreload = true;
                }
                else {
                    download();
                    isLoad = true;
                }


                batch.draw(lbp, 0, 0, loader.progress, lbp.getHeight());
            }
            else {
                loading();
                batch.draw(lbp, 0, 0, (assetManager.getProgress()*lbp.getWidth()), lbp.getHeight());
            }

            batch.end();

            if(isInit) {
                batch.begin();
                // batch.draw(test, 0, 0);
                batch.end();
            }
        }
        else
        {
            batch.begin();
            batch.draw(no_internet, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();
        }


    }
    public void dispose () {
        Debug.dispose(batch);
        Debug.dispose(assetManager);
        Debug.dispose(no_internet);
        Debug.dispose(localAssetManager);
        Debug.dispose(loadingBarBackground);
        Debug.dispose(loadingBarProgress);

        Debug.dispose(music);
        Debug.dispose(test);
    }


}