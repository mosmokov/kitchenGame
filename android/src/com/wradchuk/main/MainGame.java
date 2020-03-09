package com.wradchuk.main;

import android.content.Intent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.wradchuk.maingame.CircularMotion;


public class MainGame implements Screen {
    private final Core game;
    private Sprite back;
    private CircularMotion motion;
    //MyRetrofit retrofit;




    public MainGame(final Core _game) {
        game = _game;

        back = new Sprite(new Texture("screen/splash/background_stars.png"));
        motion = new CircularMotion(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


       //final MyRetrofit service = new MyRetrofit();
       //service.auth("login", "password", "sid").enqueue(new Callback<AuthResult>() {
       //    @Override public void onResponse(Call<AuthResult> call, Response<AuthResult> response) {
       //        final AuthResult authResult = response.body();
       //
       //        LogOut.log("Response: " + response.raw().toString() );
       //    }
       //
       //    @Override public void onFailure(Call<AuthResult> call, Throwable throwable) {
       //        throwable.printStackTrace();
       //    }});

    }


    @Override public void show() {}
    @Override public void render(float delta) {
        game.update();

        if (Gdx.input.isTouched()) {
            //int x = game.viewport.getScreenX();
            //x++;
            //game.viewport.setScreenX(x);
        }

        game.batch.begin();
        game.batch.draw(back, 0, 0 , game.virtualWidth, game.virtualHeight);
        game.batch.end();

        motion.drawCircle(game.box, 5);
        motion.draw(game.batch, game.box);

    }
    @Override public void resize(int width, int height) {
       game.resize(width, height);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        back.getTexture().dispose();
        motion.dispose();
    }
}