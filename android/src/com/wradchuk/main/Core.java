package com.wradchuk.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wradchuk.main.quest.TestScreen;
import com.wradchuk.main.test.TestScreens;
import com.wradchuk.utils.gui.MyComponent;
import com.wradchuk.utils.gui.MyResize2;
import com.wradchuk.utils.keyboard.ApplicationBundle;
import com.wradchuk.utils.keyboard.SizeChangeListener;
import com.wradchuk.utils.keyboard.View;
import com.wradchuk.utils.gui.Box;
import com.wradchuk.utils.sys.LogOut;
import com.wradchuk.utils.sys.PatchedAndroidApplication;
import com.wradchuk.utils.sys.Utils;

import java.util.ArrayList;


public class Core extends Game {
    public InputMultiplexer multiplexer;
    public static PatchedAndroidApplication context                    ; // Контекст приложения
    public float virtualHeight;
    public float virtualWidth;
    private int setWX = -1;
    private int setWY = -1;
    private View view;
    private ShapeRenderer renderer;
    private Color set_bg_color;
    private Color set_color;
    public Box box;
    public          SpriteBatch               batch                      ; //
    public          Viewport                  viewport                   ; //
    public MyResize2 resizable;
    public int[] screenSize = new int[2];
    public          Stage                     stage                      ; //
    public          Skin                      skin                       ; //
    public          BitmapFont                comfortaaRegular           ; //
    public          BitmapFont                montserratAlternatesRegular; //
    public          BitmapFont                pattayaRegular             ; //
    public          BitmapFont                podkovaRegular             ; //
    public float keyboardHeight;
    public float a1 = 0;


    public Core() {}
    public Core(ApplicationBundle _bundle, PatchedAndroidApplication _context, int[] _screenSize, int _a1) {
        this.screenSize = _screenSize;
        a1 = _a1;
        //this.resizable = new MyResize2(screenSize[0], screenSize[1]);
        view =  _bundle.getView();
        context = _context;

    }


    public void create() {
        this.virtualWidth  = 720;
        this.virtualHeight = 1280;

        //LogOut.log("Others " + (int)(Gdx.graphics.getHeight()-a1));

        this.viewport      = new FitViewport(virtualWidth, virtualHeight);
        this.batch         = new SpriteBatch();
        this.renderer      = new ShapeRenderer();

        this.set_bg_color  = new Color(0,0,0,1);
        this.set_color     = new Color(1,1,1,1);

        box                = new Box((int) virtualWidth,  (int)virtualHeight, batch);

        skin  = new Skin(Gdx.files.internal("gdx/uiskin.json"));

        this.comfortaaRegular            = MyComponent.setFont("ttf/Comfortaa-Regular.ttf"           , 30);
        this.montserratAlternatesRegular = MyComponent.setFont("ttf/MontserratAlternates-Regular.ttf", 22);
        this.pattayaRegular              = MyComponent.setFont("ttf/Pattaya-Regular.ttf"             , 22);
        this.podkovaRegular              = MyComponent.setFont("ttf/Podkova-Regular.ttf"             , 22);

        skin.add("comfo", comfortaaRegular,             BitmapFont.class);
        skin.add("monts", montserratAlternatesRegular,  BitmapFont.class);
        skin.add("patta", pattayaRegular,               BitmapFont.class);
        skin.add("podko", podkovaRegular,               BitmapFont.class);



        //LogOut.log(Gdx.graphics.getWidth()+"x"+Gdx.graphics.getHeight());



        stage = new Stage(viewport);
        multiplexer  = new InputMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);

        this.setScreen(new TestScreens(this));


    }

    @Override public void render() {
        super.render();
    }

    @Override public void dispose() {
        Utils.dispose(batch);
        Utils.dispose(renderer);
        Utils.dispose(stage);
        Utils.dispose(comfortaaRegular);
        Utils.dispose(montserratAlternatesRegular);
        Utils.dispose(pattayaRegular);
        Utils.dispose(podkovaRegular);
        //skin.dispose();
    }
    @Override public void resize(int width, int height) {
        viewport.update(width, height, true);
        setWX = (int) viewport.getWorldWidth();
        setWY = (int) viewport.getWorldHeight();
        box.resize(setWX, setWY);
    }
    @Override public void pause() {}
    @Override public void resume() {}

    public void rotateSprite(Sprite s, float _speed) {
        float rotation=s.getRotation();
        rotation-=_speed;
        if(rotation>360) rotation-=360;
        s.setRotation(rotation);
    }
    public Vector2 getPointInCircle(float horz, Vector2 centerSphere, float radius) {
        double x = (horz - centerSphere.x) / radius;
        double y = Math.sin(Math.acos(x));
        double cx = centerSphere.x + (radius * x);
        double cy = centerSphere.y + (radius * y);
        return new Vector2((float) cx, (float)cy);
    }
    public void update() {
        viewport.getCamera().update();
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        box.fonClear(0,0,0,1);
    }


    private float getKeyboardHeight() {
        return (Gdx.graphics.getHeight() - view.getHeight());
    }
    public void listen() {
        view.addListener(new SizeChangeListener() {
            @Override public void onSizeChange(float width, float height) {
                LogOut.log("Visible area: " + width + "   " + height);
                LogOut.log( "Stage area: " + stage.getWidth() + "   " + stage.getHeight());
                keyboardHeight = getKeyboardHeight();
                LogOut.log("keyboardHeight " +keyboardHeight);
            }
        });
    }



    public void setPosArraySprite(Sprite[] _sprites, float _y) {
        float ScreeX = virtualWidth/_sprites.length;

        for(int i = 0; i < _sprites.length; i++) {
            if(_y>virtualHeight/2) _sprites[i].setPosition((i*ScreeX), _y-_sprites[i].getHeight());
            else _sprites[i].setPosition((i*ScreeX), _y);
            //LogOut.log(_sprites[i].getX()+_sprites[i].getWidth()/2 + " " + _sprites[i].getY()+_sprites[i].getHeight());
        }
    }



}