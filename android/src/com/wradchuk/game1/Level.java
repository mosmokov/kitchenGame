package com.wradchuk.game1;

import android.os.Bundle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wradchuk.game.object.MySprite;
import com.wradchuk.utils.Debug;
import com.wradchuk.utils.PatchedAndroidApplication;

public class Level  extends PatchedAndroidApplication implements ApplicationListener {

    public static int         WIDTH       =    -1;
    public static int         HEIGHT      =    -1;
    private SpriteBatch batch;
    public RecipesData recipe;
    public IngreData ingredients;
    LogicGame logic;
    public MySprite[] background = new MySprite[2];
    public MySprite stol;
    public MySprite setter;
    public MySprite[] basket = new MySprite[3]; // Куда перекинуть рецепт
    public MySprite dish; // Изображение блюда



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

        batch = new SpriteBatch();

        recipe = new RecipesData("russian", 0);
        ingredients = new IngreData(recipe);

        for(int i = 0; i < ingredients.pool.length; i++) {
            ingredients.pool[i].setBatch(batch);
            ingredients.pool[i].position(0,0);
        }

        logic = new LogicGame(batch, ingredients.pool, ingredients.state);
        ingredients = null;


        for(int i = 0; i < 3; i++) {
            basket[i] = new MySprite("date/img/basket.png", 0, 0);
            basket[i].setBatch(batch);
        }

        background[0] = new MySprite("date/img/background1.png", 0, 0);
        background[0].setBatch(batch);
        background[1] = new MySprite("date/img/background.png", 0, 0);
        background[1].setBatch(batch);

        stol = new MySprite("date/img/stol.png",0, -100);
        stol.setBatch(batch);

        setter = new MySprite("date/img/setter.png", 0, -500);
        setter.setBatch(batch);

        dish = new MySprite("date/img/"+recipe.model, 0, 0);
        dish.setBatch(batch);

        basket[0].position(-110, -90);
        basket[1].position(0, 110);
        basket[2].position(110, -90);
        dish.position(0, 480);
    }
    @Override public void render() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen

        background[0].draw();
        background[1].draw();
        stol.draw();

        for(int i = 0; i < 3; i++) basket[i].draw();
        logic.draw();
        dish.draw();
        setter.draw();
    }
    @Override public void dispose() {
        Debug.dispose(batch);
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}



}
