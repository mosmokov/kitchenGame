package com.wradchuk.main;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.wradchuk.utils.sys.LogOut;

public class MainGame implements Screen {
    private final Core game;

    private float planet_x;
    private float planet_y;
    private float center_planet_x;
    private float center_planet_y;
    private float radius_planet;


    private Sprite back;


    private Sprite object;
    private float b = 0.0f;
    private float point_x;
    Vector2 temp_cord;


    public MainGame(final Core _game) {
        game = _game;
    }


    @Override public void show() {
        back = new Sprite(new Texture("screen/splash/background_stars.png"));

        float planet_w=800;
        float planet_h=800;
        planet_x = (game.virtualWidth / 2) - (planet_w / 2);
        planet_y = (game.virtualHeight / 2) - (planet_h / 2);
        center_planet_x = planet_x + (planet_w / 2);
        center_planet_y = planet_y + (planet_h / 2);
        radius_planet = planet_w/2;

        object = new Sprite(new Texture("test.png"));
        object.setSize(object.getWidth(), object.getHeight());
        object.setOrigin(object.getWidth() / 2, object.getHeight() / 2);
        point_x =  (float) game.virtualWidth / 2;


    }
    @Override public void render(float delta) {
        game.update();

        game.batch.begin();
        game.batch.draw(back, 0, 0 , game.virtualWidth, game.virtualHeight);
        game.batch.end();




        point_x+=0.1f;
        if(point_x > point_x+radius_planet) point_x = (float) game.virtualWidth / 2;
        Vector2 cord = game.getPointInCircle(point_x, new Vector2(center_planet_x, center_planet_y), radius_planet);


        if(Float.isNaN(cord.x) || Float.isNaN(cord.y)) {
            while (true) {
                LogOut.log("cord " +temp_cord.x+" "+temp_cord.y);
                LogOut.log("point_x " +point_x);
            }
        }
        else {
            temp_cord = game.getPointInCircle((point_x-0.0001f), new Vector2(center_planet_x, center_planet_y), radius_planet);
        }

        object.setPosition(cord.x, cord.y);

        game.drawLine(cord.x, cord.y, center_planet_x, center_planet_y);


        game.setColor(1,0,0,1);
        game.setLineWidth(5);
        game.drawCircle(center_planet_x, center_planet_y, radius_planet);

        game.batch.begin();
        object.draw(game.batch);
        game.batch.end();


        game.boxColor(1,0,0,1);
        game.drawBoxLine(true, 5);
        game.drawBoxPoint(true, 10);
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
        back.getTexture().dispose();
        object.getTexture().dispose();

    }
}