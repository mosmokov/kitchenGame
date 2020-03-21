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
import com.wradchuk.utils.Utils;

public class ShopRecipes implements Screen {

    final Core game;
    private Stage stage;

    private Sprite background;
    private Sprite sun;
    private Sprite signboard;
    private ImageButton exit_button;
    private ImageButton buy_button;

    private Sprite shadow;
    private Sprite basket_empty;
    private Sprite basket_1_scroll;
    private Sprite basket_2_scroll;
    private Sprite basket_3_scroll;
    private Sprite second_back;
    private Sprite second_back_fix;
    private ImageButton recipe_asia_1;
    private ImageButton recipe_asia_2;
    private ImageButton recipe_asia_3;
    private ImageButton recipe_europe_1;
    private ImageButton recipe_europe_2;
    private ImageButton recipe_europe_3;
    private ImageButton up_arrow, down_arrow;
    private Sprite added_text;
    private ImageButton cook_tab;
    private ImageButton sale_tab;
    private ImageButton recipe_tab;
    private ImageButton resources_tab;


    public ShopRecipes(final Core _game) {
        game = _game;

        stage = game.stage;

        background      = new Sprite(new Texture("screen/shop/all_screen/background.png"));
        sun             = new Sprite(new Texture("screen/shop/all_screen/sun.png"));
        signboard       = new Sprite(new Texture("screen/shop/all_screen/signboard.png"));
        second_back     = new Sprite(new Texture("screen/shop/all_screen/second_back.png"));
        second_back_fix = new Sprite(new Texture("screen/shop/all_screen/second_back_fix.png"));
        added_text      = new Sprite(new Texture("screen/shop/all_screen/added_text.png"));


        exit_button  =  Utils.createImageButton("screen/shop/all_screen/exit_button.png", "screen/shop/all_screen/exit_button.png");
        buy_button   =  Utils.createImageButton("screen/shop/all_screen/buy_button.png", "screen/shop/all_screen/buy_button.png");
        cook_tab     =  Utils.createImageButton("screen/shop/all_screen/cook_tab_off.png", "screen/shop/all_screen/cook_tab_on.png");
        sale_tab     =  Utils.createImageButton("screen/shop/all_screen/sale_tab_off.png", "screen/shop/all_screen/sale_tab_on.png");
        recipe_tab   =  Utils.createImageButton("screen/shop/all_screen/recipe_tab_on.png", "screen/shop/all_screen/recipe_tab_on.png");
        resources_tab = Utils.createImageButton("screen/shop/all_screen/resources_tab_off.png", "screen/shop/all_screen/resources_tab_on.png");

        up_arrow = Utils.createImageButton("screen/shop/all_screen/down_arrow.png", "screen/shop/all_screen/down_arrow.png");
        up_arrow.setRotation(180);
        down_arrow = Utils.createImageButton("screen/shop/all_screen/down_arrow.png", "screen/shop/all_screen/down_arrow.png");


        shadow = new Sprite(new Texture("screen/shop/recipes/shadow.png"));
        basket_empty = new Sprite(new Texture("screen/shop/recipes/basket_empty.png"));
        basket_1_scroll = new Sprite(new Texture("screen/shop/recipes/basket_1scroll.png"));
        basket_2_scroll = new Sprite(new Texture("screen/shop/recipes/basket_2scroll.png"));
        basket_3_scroll = new Sprite(new Texture("screen/shop/recipes/basket_3scroll.png"));

        recipe_asia_1   = Utils.createImageButton("screen/shop/recipes/recipe_asia_1.png", "screen/shop/recipes/recipe_asia_1.png");
        recipe_asia_2   = Utils.createImageButton("screen/shop/recipes/recipe_asia_2.png", "screen/shop/recipes/recipe_asia_2.png");
        recipe_asia_3   = Utils.createImageButton("screen/shop/recipes/recipe_asia_3.png", "screen/shop/recipes/recipe_asia_3.png");
        recipe_europe_1 = Utils.createImageButton("screen/shop/recipes/recipe_europe_1.png", "screen/shop/recipes/recipe_europe_1.png");
        recipe_europe_2 = Utils.createImageButton("screen/shop/recipes/recipe_europe_2.png", "screen/shop/recipes/recipe_europe_2.png");
        recipe_europe_3 = Utils.createImageButton("screen/shop/recipes/recipe_europe_3.png", "screen/shop/recipes/recipe_europe_3.png");




        sun.setSize(sun.getWidth(),sun.getHeight());
        sun.setOrigin(sun.getWidth()/2,sun.getHeight()/2);
        sun.setPosition(game.virtualWidth/2-(sun.getWidth()/2), game.virtualHeight/2-(sun.getHeight()/2.15f));

        signboard.setPosition(game.virtualWidth/2-(signboard.getWidth()/2), game.virtualHeight-signboard.getHeight());


        cook_tab.setPosition(0,  game.virtualHeight-400);
        recipe_tab.setPosition(cook_tab.getWidth(),  game.virtualHeight-400);
        sale_tab.setPosition((game.virtualWidth-recipe_tab.getWidth()*2)+20,  game.virtualHeight-400);
        resources_tab.setPosition(game.virtualWidth-(recipe_tab.getWidth()-20),  game.virtualHeight-400);

        down_arrow.setPosition(game.virtualWidth/2-(down_arrow.getWidth()),game.virtualHeight-(800+recipe_europe_3.getHeight()+(down_arrow.getHeight()*2)));
        buy_button.setPosition(game.virtualWidth-(buy_button.getWidth()+10), 20);
        exit_button.setPosition(game.virtualWidth-exit_button.getWidth(), game.virtualHeight-exit_button.getHeight());



        recipe_europe_1.setPosition( 0, game.virtualHeight-800);
        recipe_europe_2.setPosition( game.virtualWidth/2-(recipe_europe_2.getWidth()/2)-30, game.virtualHeight-800);
        recipe_europe_3.setPosition( game.virtualWidth-recipe_europe_3.getWidth()-20, game.virtualHeight-800);
        recipe_asia_1  .setPosition( 0, game.virtualHeight-(800+recipe_europe_1.getHeight()));
        recipe_asia_2  .setPosition( game.virtualWidth/2-(recipe_asia_2.getWidth()/2)-30, game.virtualHeight-(800+recipe_europe_2.getHeight()));
        recipe_asia_3  .setPosition( game.virtualWidth-recipe_asia_3.getWidth()-20, game.virtualHeight-(800+recipe_europe_3.getHeight()));

        cook_tab.addListener(new InputListener() {
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ShopCooks(game));
            }
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
        });
        recipe_tab.addListener(new InputListener() {
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
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

        recipe_asia_1.addListener(new InputListener() {
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
        });
        recipe_asia_2.addListener(new InputListener() {
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
        });
        recipe_asia_3.addListener(new InputListener() {
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
        });

        recipe_europe_1.addListener(new InputListener() {
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
        });
        recipe_europe_2.addListener(new InputListener() {
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
        });
        recipe_europe_3.addListener(new InputListener() {
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
        });


        down_arrow.addListener(new InputListener() {
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
        });



    }

    @Override public void show() {

        stage.addActor(cook_tab);
        stage.addActor(recipe_tab);
        stage.addActor(sale_tab);
        stage.addActor(resources_tab);
        stage.addActor(down_arrow);
        stage.addActor(buy_button);
        stage.addActor(exit_button);

        stage.addActor(recipe_asia_1);
        stage.addActor(recipe_asia_2);
        stage.addActor(recipe_asia_3);
        stage.addActor(recipe_europe_1);
        stage.addActor(recipe_europe_2);
        stage.addActor(recipe_europe_3);




        Gdx.input.setInputProcessor(stage);
    }
    @Override public void render(float delta) {
        game.update();

        game.batch.begin();
        game.batch.draw(background, 0 ,0, game.virtualWidth, game.virtualHeight);
        game.rotateSprite(sun, 0.3f);
        sun.draw(game.batch);
        game.batch.draw(second_back, 0, 120, game.virtualWidth, game.virtualHeight-500);


        game.batch.draw(shadow, game.virtualWidth/2-(shadow.getWidth()/2), 120);
        game.batch.draw(basket_empty, game.virtualWidth/2-(basket_empty.getWidth()/2), 120);

        game.batch.draw(added_text, game.virtualWidth-(added_text.getWidth()+buy_button.getWidth())-20, 35);
        signboard.draw(game.batch);
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
        down_arrow.setVisible(false);
        cook_tab.setVisible(false);
        sale_tab.setVisible(false);
        recipe_tab.setVisible(false);
        resources_tab.setVisible(false);

        recipe_asia_1.setVisible(false);
        recipe_asia_2.setVisible(false);
        recipe_asia_3.setVisible(false);
        recipe_europe_1.setVisible(false);
        recipe_europe_2.setVisible(false);
        recipe_europe_3.setVisible(false);

    }
    @Override public void dispose() {
        background.getTexture().dispose();
        sun.getTexture().dispose();
        signboard.getTexture().dispose();

        shadow.getTexture().dispose();
        basket_empty.getTexture().dispose();
        second_back.getTexture().dispose();
        second_back_fix.getTexture().dispose();
        added_text.getTexture().dispose();

        stage.dispose();
    }
}