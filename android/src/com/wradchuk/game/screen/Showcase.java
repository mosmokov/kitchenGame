package com.wradchuk.game.screen;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Showcase extends Screen {

    Label label;
    ImageButton go_fortune, go_start_game, go_recipes;


    /***
     * Коструктор для загрузки фона
     * @param _background - путь к фоновому изображению
     */
    public Showcase(String _background, int _x, int _y, InputMultiplexer __multiplexer) {
        super(_background, _x,  _y, __multiplexer);
        SET_SCREEN = 0;

        label = new Label("ВИТРИНА", skin, "my-font", new Color(1,0,0,1));
        labelSetPos(label, 100, 100);

        go_fortune = createImageButton("img/bt/off.png", "img/bt/on.png");
        buttonSetPos(go_fortune, 0, (HEIGHT/2)-(go_fortune.getHeight()/2));
        listenerButton(1, go_fortune, 1);

        go_start_game = createImageButton("img/bt/off.png", "img/bt/on.png");
        buttonSetPos(go_start_game, WIDTH-go_start_game.getWidth(), (HEIGHT/2)-(go_start_game.getHeight()/2));
        listenerButton(2, go_start_game, 2);

        go_recipes = createImageButton("img/bt/off.png", "img/bt/on.png");
        buttonSetPos(go_recipes, (WIDTH/2)-(go_recipes.getWidth()/2), 0);
        listenerButton(3, go_recipes, 3);

        stage.addActor(label);
        stage.addActor(go_fortune);
        stage.addActor(go_start_game);
        stage.addActor(go_recipes);
        moved = false;

    }


    @Override public void drawBackground(int _id) {
        super.drawBackground(_id);
        batch.begin();
        batch.draw(background, cx, 0);
        batch.end();
        stage.draw();

        go_fortune.clearActions();
        go_start_game.clearActions();
        go_recipes.clearActions();
    }

    @Override
    public void dispose() {
        super.dispose();
    }


    @Override public void move(int _id) {
        super.move(_id);
    }

    @Override public void stop() {
        super.stop();
        moved = false;
    }

    @Override public void addProcessor() {super.addProcessor(); }
    @Override public void removeProcessor() { super.removeProcessor();}
}