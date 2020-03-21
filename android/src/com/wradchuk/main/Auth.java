package com.wradchuk.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.wradchuk.utils.AsyncResultPasser;
import com.wradchuk.utils.CreateFileConfig;
import com.wradchuk.utils.LogOut;
import com.wradchuk.utils.Utils;
import com.wradchuk.utils.Command;

import org.json.JSONException;
import org.json.JSONObject;

public class Auth implements Screen, InputProcessor, AsyncResultPasser {
    final Core game;

    public Sprite background;
    public Sprite cloud_1;
    public Sprite cloud_2;
    public Sprite shar;
    public ImageButton login_icon;
    public ImageButton stripes_login;
    public ImageButton password_icon;
    public ImageButton stripes_password;

    public Label error_label;
    public Label login_label;
    public Label pass_label;
    public String login_str = "";
    public String pass_str = "";

    public boolean login_or_pass = false;
    public int key_kode = -1;
    public boolean isAuth = false;



    public Auth(final Core _game) {
        game = _game;

        background = new Sprite(new Texture("screen/auth/background.png"));
        cloud_1 = new Sprite(new Texture("screen/auth/cloud_1.png"));
        cloud_2 = new Sprite(new Texture("screen/auth/cloud_2.png"));
        shar = new Sprite(new Texture("screen/auth/shar.png"));
        login_icon = Utils.createImageButton("screen/auth/login_icon.png","screen/auth/login_icon.png" );
        stripes_login =  Utils.createImageButton("screen/auth/stripes.png","screen/auth/stripes.png");
        password_icon =  Utils.createImageButton("screen/auth/password_icon.png", "screen/auth/password_icon.png");
        stripes_password =  Utils.createImageButton("screen/auth/stripes.png", "screen/auth/stripes.png");

        error_label = new Label("Регистрация", game.skin, "comfo", new Color(1,0,0,1));
        login_label = new Label(" ", game.skin, "comfo", new Color(1,0,0,1));
        pass_label  = new Label(" ", game.skin, "comfo", new Color(1,0,0,1));


        error_label.setPosition(20, game.virtualHeight/2+login_icon.getHeight()*2);

        login_icon.setPosition(200, game.virtualHeight/2);
        stripes_login.setPosition(200+login_icon.getWidth()/2, game.virtualHeight/2-10);
        login_label.setPosition(200+login_icon.getWidth(), game.virtualHeight/2);


        password_icon.setPosition(200, game.virtualHeight/2-90);
        stripes_password.setPosition(200+password_icon.getWidth()/2, game.virtualHeight/2-100);
        pass_label.setPosition(200+password_icon.getWidth(), game.virtualHeight/2-90);




        login_icon.addListener(new InputListener() {
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                login_or_pass=true;
                Gdx.input.setOnscreenKeyboardVisible(true);
                error_label.setText("Вводим логин");
            }
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
        });
        stripes_login.addListener(new InputListener() {
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                login_or_pass=true;
                Gdx.input.setOnscreenKeyboardVisible(true);
                error_label.setText("Вводим логин");
            }
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
        });
        password_icon.addListener(new InputListener() {
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                login_or_pass=false;
                Gdx.input.setOnscreenKeyboardVisible(true);
                error_label.setText("Вводим пароль");
            }
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
        });
        stripes_password.addListener(new InputListener() {
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                login_or_pass=false;
                Gdx.input.setOnscreenKeyboardVisible(true);
                error_label.setText("Вводим пароль");
            }
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
        });


        game.stage.addActor(login_icon);
        game.stage.addActor(stripes_login);
        game.stage.addActor(password_icon);
        game.stage.addActor(stripes_password);
        game.stage.addActor(error_label);
        game.stage.addActor(login_label);
        game.stage.addActor(pass_label);
        game.multiplexer.addProcessor(this);
        game.multiplexer.addProcessor(game.stage);

        JSONObject auth = null;
        if(Gdx.files.local("auth.txt").exists()) {
            auth = Utils.read_json("auth.txt", Utils.DISCR.LOCAL);
            Command.auth(auth.optString("login"), auth.optString("pass"),this);
        }

    }

    @Override public void show() {}
    @Override public void render(float delta) {

        game.update();
        game.batch.begin();

        game.batch.draw(background, 0, 0, game.virtualWidth, game.virtualHeight);

        game.batch.draw(cloud_1, 180, game.virtualHeight-400);
        game.batch.draw(cloud_2, (game.virtualWidth-cloud_1.getWidth())-30, game.virtualHeight-(shar.getHeight()+150));

        game.batch.draw(shar, (game.virtualWidth-shar.getWidth())-250, game.virtualHeight-(shar.getHeight()+200));

        game.batch.end();

        game.stage.act();
        game.stage.draw();

        if(isAuth) game.setScreen(new SplashScreen(game));

    }

    @Override public void resize(int width, int height) {
        game.resize(width, height);
    }
    @Override public void pause() {

    }
    @Override public void resume() {}
    @Override public void hide() {
        login_icon.setVisible(false);
        login_label.setVisible(false);
        stripes_login.setVisible(false);
        password_icon.setVisible(false);
        pass_label.setVisible(false);
        stripes_password.setVisible(false);
        error_label.setVisible(false);
    }
    @Override public void dispose() {
        background.getTexture().dispose();
        cloud_1.getTexture().dispose();
        cloud_2.getTexture().dispose();
        shar.getTexture().dispose();
    }

    @Override public boolean keyDown(int keycode) {
        LogOut.log(""+keycode);
        key_kode = keycode;
        if(keycode==67) {
            if (login_or_pass) {
                if (login_str.length() < 1) {
                    login_str = "";
                } else {
                    login_str = login_str.substring(0, login_str.length() - 1);
                }
            } else {
                if (pass_str.length() < 1) {
                    pass_str = "";
                } else {
                    pass_str = pass_str.substring(0, pass_str.length() - 1);

                }
            }
        }



        if(keycode==66) {
            if (login_or_pass) login_or_pass = false;
            else {
                LogOut.log("Кнопка отправить!");
                Gdx.input.setOnscreenKeyboardVisible(false);
                Command.reg(login_str, pass_str, this);
            }

            if(login_or_pass) error_label.setText("Вводим логин");
            else error_label.setText("Вводим пароль");
        }

        return false;
    }
    @Override public boolean keyUp(int keycode) {
        LogOut.log(""+keycode);

        key_kode = keycode;

        if(keycode==67) {
            if (login_or_pass) login_label.setText(login_str);
            else pass_label.setText(pass_str);
        }
        return false;
    }
    @Override public boolean keyTyped(char character) {
        LogOut.log(""+character);

        String cha = new String(""+character);
        if(key_kode!=67 && key_kode!=66 && key_kode!=62 && !cha.equals(null))
        {
            if(login_or_pass) {
                if(login_str.length()<20) {
                    login_str = login_str+""+character;
                    login_label.setText(""+login_str);
                }

            }
            else {
                if(pass_str.length()<20) {
                    pass_str = pass_str + "" + character;
                    pass_label.setText("" + pass_str);
                }
            }
        }

        key_kode=-1;

        return false;
    }
    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
    @Override public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
    @Override public boolean scrolled(int amount) {
        return false;
    }

    @Override public void message(String msg) {
        LogOut.log(msg);
        JSONObject response = null;

        try { response = new JSONObject(msg); } catch(JSONException e) {LogOut.logEx(e.getMessage());}


        if(response.optString("com").equals("server_error")) {
            error_label.setText("Сервер игры не доступен!");
            error_label.setPosition(game.virtualWidth/2-(error_label.getWidth()/2), game.virtualHeight/2+login_icon.getHeight()*2);
        }


        if(response.optString("com").equals("reg")) {
            if(response.optBoolean("status")) Command.auth(login_str, pass_str,this);
            else {
                error_label.setText(response.optString("msg"));
            }
        }

        if(response.optString("com").equals("auth")) {
            if(response.optBoolean("status")) {
                JSONObject auth = null;
                try { auth = new JSONObject().put("login", login_str).put("pass", pass_str);} catch(JSONException e) {LogOut.logEx(e.getMessage());}
                if(Gdx.files.local("auth.txt").exists()) isAuth = true;
                else Utils.create_file(auth.toString(), "auth.txt", false, Utils.DISCW.LOCAL, this);
            } else {
                error_label.setText(response.optString("msg"));
            }

        }
    }
    @Override public void create_file(CreateFileConfig _config) {
        if(Gdx.files.local(_config.file).exists()) isAuth = true;


    }

}