package com.wradchuk.main.quest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wradchuk.main.Core;
import com.wradchuk.utils.sys.Utils;

public class TestScreen implements Screen {
    final Core game;

    private SpriteBatch batch;
    private Viewport viewport;

    float WIDTH  = -1;
    float HEIGHT = -1;


    Vector2 perScreen;


    private Sprite background;


    private int offset = 10;
    private final int ALL = 5;
    private Sprite[] xz = new Sprite[ALL];



    public TestScreen(final Core _game) {
        game = _game;
        batch = game.batch;

        WIDTH = 100;
        HEIGHT= 100;

        viewport = new FitViewport(WIDTH, HEIGHT);
        perScreen = perScreen(WIDTH, HEIGHT, offset);



        background = new Sprite(new Texture("quest/test/background.png"));

        for(int i = 0; i < ALL; i ++) {
            xz[i] = new Sprite(new Texture("quest/test/1.png"));
        }


        posPerSprite(5, xz, 0, 50.0f);

    }

    @Override public void show() {}
    @Override public void render(float delta) {
        update();

        batch.begin();
        batch.draw(background, 0, 0);
        for(int i = 0; i < ALL; i ++) xz[i].draw(batch);
        batch.end();



    }
    @Override public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
    @Override public void pause() {

    }
    @Override public void resume() {

    }
    @Override public void hide() {

    }
    @Override public void dispose() {
        Utils.dispose(batch);
        Utils.dispose(background.getTexture());

        for(int i = 0; i < ALL; i ++) Utils.dispose(xz[i].getTexture());
    }


    public void update() {
        viewport.getCamera().update();
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0,0,0,1);
    }



    public Vector2 perScreen(float _width, float _heght, int _offsetW) {
        return  new Vector2((WIDTH-(_offsetW*2))/100, HEIGHT/100);
    }
    public Vector2 perSprite(Sprite _sprite) {
        return new Vector2(_sprite.getWidth()/100, _sprite.getHeight()/100);
    }

    public void posPerSprite(int _coll, Sprite[] _sprite, float _perX, float _perY) {
        float step = (perScreen.x*100)/_coll;
        for(int i = 0; i < _coll; i++) {
            if(i==0) {
                _sprite[i].setPosition(offset, perScreen.y*_perY-(_sprite[i].getHeight()/2));
            }
            if(i!= 0 && i!=_coll-1) {
                _sprite[i].setPosition(offset+(step*i)-(_sprite[i].getWidth()/2), perScreen.y*_perY-(_sprite[i].getHeight()/2));
            }
            if(i==_coll-1) {
                _sprite[i].setPosition(offset+(perScreen.x*100)-(_sprite[i].getWidth()), perScreen.y*_perY-(_sprite[i].getHeight()/2));
            }

        }


    }
}