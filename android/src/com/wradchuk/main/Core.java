package com.wradchuk.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wradchuk.utils.gui.MyComponent;
import com.wradchuk.utils.gui.MyResize2;
import com.wradchuk.utils.keyboard.ApplicationBundle;
import com.wradchuk.utils.keyboard.SizeChangeListener;
import com.wradchuk.utils.keyboard.View;
import com.wradchuk.main.shop.ShopRecipes;
import com.wradchuk.utils.gui.Box;
import com.wradchuk.utils.sys.LogOut;
import com.wradchuk.utils.sys.PatchedAndroidApplication;
import com.wradchuk.utils.sys.Utils;


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
    private Box box;
    public          SpriteBatch               batch                      ; //
    public          Viewport                  viewport                   ; //
    public MyResize2 resizeble;
    public int[] screenSize = new int[2];
    public          Stage                     stage                      ; //
    public          Skin                      skin                       ; //
    public          BitmapFont                comfortaaRegular           ; //
    public          BitmapFont                montserratAlternatesRegular; //
    public          BitmapFont                pattayaRegular             ; //
    public          BitmapFont                podkovaRegular             ; //
    public float keyboardHeight;


    public Core() {}
    public Core(ApplicationBundle _bundle, PatchedAndroidApplication _context, int[] _screenSize) {
        this.screenSize = _screenSize;
        this.resizeble = new MyResize2(screenSize[0], screenSize[1]);
        view =  _bundle.getView();
        context = _context;



    }


    public void create() {
        this.virtualWidth  = 1080;
        this.virtualHeight = 1920;

        this.viewport      = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.batch         = new SpriteBatch();
        this.renderer      = new ShapeRenderer();

        this.set_bg_color  = new Color(0,0,0,1);
        this.set_color     = new Color(1,1,1,1);

        box                = new Box((int)virtualWidth, (int) virtualHeight);

        skin  = new Skin(Gdx.files.internal("gdx/uiskin.json"));

        LogOut.log("test");

        this.comfortaaRegular            = MyComponent.setFont("ttf/Comfortaa-Regular.ttf"           , 30);
        this.montserratAlternatesRegular = MyComponent.setFont("ttf/MontserratAlternates-Regular.ttf", 22);
        this.pattayaRegular              = MyComponent.setFont("ttf/Pattaya-Regular.ttf"             , 22);
        this.podkovaRegular              = MyComponent.setFont("ttf/Podkova-Regular.ttf"             , 22);

        skin.add("comfo", comfortaaRegular,             BitmapFont.class);
        skin.add("monts", montserratAlternatesRegular,  BitmapFont.class);
        skin.add("patta", pattayaRegular,               BitmapFont.class);
        skin.add("podko", podkovaRegular,               BitmapFont.class);



        LogOut.log(Gdx.graphics.getWidth()+"x"+Gdx.graphics.getHeight());



        stage = new Stage(viewport);
        multiplexer  = new InputMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);

        this.setScreen(new SplashScreen(this));


    }

    @Override public void render() {super.render();}

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





    public void fonClear(float r, float g, float b, float a) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(r, g, b, a);
    }
    public void fonClear(Color rgba) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(rgba.r,rgba.g,rgba.b,rgba.a);
    }
    public void setColor(float r, float g, float b, float a) {
        set_color = new Color(r, g, b, a);
        renderer.setColor(set_color);
    }
    public void setColor(Color rgba) {
        set_color = new Color(rgba.r,rgba.g,rgba.b,rgba.a);
        renderer.setColor(set_color);
    }
    public void setLineWidth(int width) {
        Gdx.gl.glLineWidth(width);
    }
    public void drawPoint(Vector2 vector2) {
        batch.begin();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.line(vector2.x-5, vector2.y, vector2.x+5, vector2.y);
        renderer.end();
        batch.end();
    }
    public void drawPoint(float x, float y) {
        batch.begin();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.line(x-5, y, x+5, y);
        renderer.end();
        batch.end();
    }
    public void drawLine(Vector2 start, Vector2 end) {
        batch.begin();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.line(start, end);
        renderer.end();
        batch.end();
    }
    public void drawLine(float x, float y, float x1, float y1) {
        batch.begin();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.line(x, y, x1, y1);
        renderer.end();
        batch.end();
    }
    public void drawCircle(float x, float y, float radius) {
        batch.begin();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.circle(x, y, radius);
        renderer.end();
        batch.end();
    }
    public void boxColor(float r, float g, float b, float a) {
        box.boxColor(r,g,b,a);
    }
    public void boxColor(Color rgba) {
        box.boxColor(rgba.r,rgba.g,rgba.b,rgba.a);

    }
    public void drawBoxPoint(boolean _flag, int pointSize) {
        if(_flag) box.boxDrawPoint(this, pointSize);
    }
    public void drawBoxLine(boolean _flag, int lineWidth) {
        if(_flag) box.boxDrawLine(this, lineWidth);
    }

}