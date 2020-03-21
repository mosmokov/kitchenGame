package com.wradchuk.shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.wradchuk.main.Core;
import com.wradchuk.main.SplashScreen;
import com.wradchuk.utils.AsyncResultPasser;
import com.wradchuk.utils.CreateFileConfig;
import com.wradchuk.utils.Utils;


public class ShopCooks implements Screen, AsyncResultPasser {

    final Core game;

    private Stage stage;

    private Sprite background;
    private Sprite sun;
    private Sprite signboard;
    private ImageButton exit_button;
    private ImageButton buy_button;
    private Sprite second_back;
    private Sprite second_back_fix;
    private Sprite added_text;

    private ImageButton cook_tab;
    private ImageButton sale_tab;
    private ImageButton recipe_tab;
    private ImageButton resources_tab;

    private Sprite switch_1;
    private Sprite switch_2;
    private Sprite switch_3;
    private Sprite switch_4;

    private Sprite scroll;
    private Sprite master_chief_2;
    private Sprite text_2;
    private Sprite text_1;

    private Sprite europe_tab;



    public ShopCooks(Core _game) {
        game = _game;

        stage = game.stage;

        background      = new Sprite(new Texture("screen/shop/all_screen/background.png"));
        sun             = new Sprite(new Texture("screen/shop/all_screen/sun.png"));
        signboard       = new Sprite(new Texture("screen/shop/all_screen/signboard.png"));
        second_back     = new Sprite(new Texture("screen/shop/all_screen/second_back.png"));
        second_back_fix = new Sprite(new Texture("screen/shop/all_screen/second_back_fix.png"));
        added_text      = new Sprite(new Texture("screen/shop/all_screen/added_text.png"));


        exit_button   = Utils.createImageButton("screen/shop/all_screen/exit_button.png", "screen/shop/all_screen/exit_button.png");
        buy_button    = Utils.createImageButton("screen/shop/all_screen/buy_button.png", "screen/shop/all_screen/buy_button.png");
        cook_tab      = Utils.createImageButton("screen/shop/all_screen/cook_tab_on.png", "screen/shop/all_screen/cook_tab_on.png");
        sale_tab      = Utils.createImageButton("screen/shop/all_screen/sale_tab_off.png", "screen/shop/all_screen/sale_tab_on.png");
        recipe_tab    = Utils.createImageButton("screen/shop/all_screen/recipe_tab_off.png", "screen/shop/all_screen/recipe_tab_on.png");
        resources_tab = Utils.createImageButton("screen/shop/all_screen/resources_tab_off.png", "screen/shop/all_screen/resources_tab_on.png");


        switch_1      = new Sprite(new Texture("screen/shop/cooks/switch_1.png"));
        scroll      = new Sprite(new Texture("screen/shop/cooks/scroll.png"));
        master_chief_2      = new Sprite(new Texture("screen/shop/cooks/master_chief_2.png"));
        text_2      = new Sprite(new Texture("screen/shop/cooks/text_2.png"));
        text_1      = new Sprite(new Texture("screen/shop/cooks/text_1.png"));
        europe_tab      = new Sprite(new Texture("screen/shop/cooks/europe_tab.png"));


        sun.setSize(sun.getWidth(),sun.getHeight());
        sun.setOrigin(sun.getWidth()/2,sun.getHeight()/2);
        sun.setPosition(game.virtualWidth/2-(sun.getWidth()/2), game.virtualHeight/2-(sun.getHeight()/2.15f));

        sun.setSize(sun.getWidth(),sun.getHeight());
        sun.setOrigin(sun.getWidth()/2,sun.getHeight()/2);
        sun.setPosition(game.virtualWidth/2-(sun.getWidth()/2), game.virtualHeight/2-(sun.getHeight()/2.15f));

        signboard.setPosition(game.virtualWidth/2-(signboard.getWidth()/2), game.virtualHeight-signboard.getHeight());


        cook_tab.setPosition(0,  game.virtualHeight-400);
        recipe_tab.setPosition(cook_tab.getWidth(),  game.virtualHeight-400);
        sale_tab.setPosition((game.virtualWidth-recipe_tab.getWidth()*2)+20,  game.virtualHeight-400);
        resources_tab.setPosition(game.virtualWidth-(recipe_tab.getWidth()-20),  game.virtualHeight-400);

        buy_button.setPosition(game.virtualWidth-(buy_button.getWidth()+10), 20);
        exit_button.setPosition(game.virtualWidth-exit_button.getWidth(), game.virtualHeight-exit_button.getHeight());


        cook_tab.addListener(new InputListener() {
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
        });
        recipe_tab.addListener(new InputListener() {
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ShopRecipes(game));
            }
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
        });
        sale_tab.addListener(new InputListener() {
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
        });
        recipe_tab.addListener(new InputListener() {
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
        });

        exit_button.addListener(new InputListener() {
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new SplashScreen(game));
            }
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
        });
        buy_button.addListener(new InputListener() {
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
        });


    }

    @Override public void show() {
        stage.addActor(cook_tab);
        stage.addActor(recipe_tab);
        stage.addActor(sale_tab);
        stage.addActor(resources_tab);
        stage.addActor(buy_button);
        stage.addActor(exit_button);
        Gdx.input.setInputProcessor(stage);

    }
    @Override public void render(float delta) {
        game.update();

        game.update();

        game.batch.begin();
        game.batch.draw(background, 0 ,0, game.virtualWidth, game.virtualHeight);
        game.rotateSprite(sun, 0.3f);
        sun.draw(game.batch);
        game.batch.draw(second_back, 0, 120, game.virtualWidth, game.virtualHeight-500);


        game.batch.draw(added_text, game.virtualWidth-(added_text.getWidth()+buy_button.getWidth())-20, 35);
        signboard.draw(game.batch);



        float y = 120;
        game.batch.draw(switch_1, game.virtualWidth/2-(switch_1.getWidth()/2), y);
        y=y+switch_1.getHeight();
        game.batch.draw(scroll, game.virtualWidth/2-(scroll.getWidth()/2), y);
        y=y+scroll.getHeight();
        game.batch.draw(master_chief_2, game.virtualWidth/2-(master_chief_2.getWidth()/2), y);
        y=y+master_chief_2.getHeight();
        game.batch.draw(text_2, game.virtualWidth/2-(text_2.getWidth()/2), y);
        y=y+text_2.getHeight();
        game.batch.draw(text_1, game.virtualWidth/2-(text_1.getWidth()/2), y);
        y=y+text_1.getHeight();
        game.batch.draw(europe_tab, game.virtualWidth/2-(europe_tab.getWidth()/2), y);


        game.batch.end();


        stage.draw();

        game.batch.begin();
        game.batch.draw(second_back_fix,  0, game.virtualHeight-388);
        game.batch.end();
    }
    @Override public void resize(int width, int height) {
        game.resize(width, height);
    }
    @Override public void pause() {

    }
    @Override public void resume() {

    }
    @Override public void hide() {
        exit_button.setVisible(false);
        buy_button.setVisible(false);
        cook_tab.setVisible(false);
        sale_tab.setVisible(false);
        recipe_tab.setVisible(false);
        resources_tab.setVisible(false);
    }
    @Override public void dispose() {
        background.getTexture().dispose();
        sun.getTexture().dispose();
        signboard.getTexture().dispose();

        second_back.getTexture().dispose();
        second_back_fix.getTexture().dispose();
        added_text.getTexture().dispose();

        switch_1.getTexture().dispose();
        scroll.getTexture().dispose();
        master_chief_2.getTexture().dispose();
        text_2.getTexture().dispose();
        text_1.getTexture().dispose();
        europe_tab.getTexture().dispose();
        stage.dispose();
    }

    @Override public void message(String msg) {

    }
    @Override public void create_file(CreateFileConfig _config) {

    }
}