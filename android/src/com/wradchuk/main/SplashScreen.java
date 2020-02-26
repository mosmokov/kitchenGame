package com.wradchuk.main;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.wradchuk.main.shop.ShopCooks;
import com.wradchuk.main.shop.ShopRecipes;


public class SplashScreen implements Screen {
    private final Core game;
    public Sprite background;
    public Sprite sun;
    public Sprite cloud_1;
    public Sprite cloud_2;
    public Sprite cloud_niz;
    public Sprite planet;
    public Sprite shar;


    float a = 1.0f, b = 0.0f;
    Vector2 point;

    float planet_x = 0;
    float planet_y = 0;

    float center_planet_x = 0;
    float center_planet_y = 0;
    float radius_planet   = 0;





    public SplashScreen(final Core gam) {
        game = gam;

        background = new Sprite(new Texture("screen/splash/background_stars.png"));


        cloud_1 = new Sprite(new Texture("screen/splash/cloud_1.png"));
        cloud_2 = new Sprite(new Texture("screen/splash/cloud_2.png"));
        cloud_niz = new Sprite(new Texture("screen/splash/Cloud_niz.png"));
        planet = new Sprite(new Texture("screen/splash/planet.png"));


        planet_x = game.virtualWidth/2-(planet.getWidth()/2);
        planet_y = -((game.virtualWidth)/1.40f)-planet.getHeight()/4;

        center_planet_x = planet_x + (planet.getWidth()  / 2);
        center_planet_y = planet_y + (planet.getHeight() / 2);
        radius_planet = (planet.getWidth()/2);

        planet.setSize(planet.getWidth(),planet.getHeight());
        planet.setOrigin(planet.getWidth()/2,planet.getHeight()/2);
        planet.setPosition(planet_x, planet_y);

        sun = new Sprite(new Texture("screen/splash/sun_done.png"));
        sun.setSize(sun.getWidth(),sun.getHeight());
        sun.setOrigin(sun.getWidth()/2,sun.getHeight()/2);

        shar = new Sprite(new Texture("screen/splash/shar.png"));

    }

    @Override public void show() {




    }
    @Override public void render(float delta) {

        game.update();
        game.batch.begin();

        game.batch.draw(background, 0, 0, game.virtualWidth, game.virtualHeight);


            if (b >= radius_planet + (sun.getWidth() / 2)) b = 0.0f;
            else b +=0.9f;

            point = pointInOrbit(b);
            sun.setPosition(point.x - (sun.getWidth() / 2), point.y - (sun.getHeight() / 2));

            sun.draw(game.batch);

            if(b>radius_planet/2) { game.setScreen(new ShopRecipes(game));

            }

            game.batch.draw(cloud_1, (game.virtualWidth/2)-460, game.virtualHeight/2);
            game.batch.draw(cloud_2, (game.virtualWidth/2)+300, game.virtualHeight/2);
            game.batch.draw(cloud_niz, (game.virtualWidth/2)+200, (game.virtualHeight/2)-300);

            planet.draw(game.batch);
            game.batch.draw(shar, game.virtualWidth/2-(shar.getWidth()/2), game.virtualHeight/2-(shar.getHeight()/4));

            game.batch.end();

            game.rotateSprite(sun, 0.5f);
            game.rotateSprite(planet, -0.5f);
    }



    public Vector2 pointInOrbit(float horz) {
        double x = (horz - center_planet_x) / radius_planet;
        double y = Math.sin(Math.acos(x));
        double cx = center_planet_x + (radius_planet * x);
        double cy = center_planet_y + (radius_planet * y);
        return new Vector2((float) cx, (float)cy);
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
        background.getTexture().dispose();
        sun.getTexture().dispose();
        cloud_1.getTexture().dispose();
        cloud_2.getTexture().dispose();
        cloud_niz.getTexture().dispose();
        planet.getTexture().dispose();
        shar.getTexture().dispose();
    }


}
