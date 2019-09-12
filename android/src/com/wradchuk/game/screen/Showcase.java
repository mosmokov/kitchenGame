package com.wradchuk.game.screen;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Showcase extends Screen {

    public Label label;
    ImageButton go_fortune, go_start_game, go_recipes;


    /***
     * Коструктор для загрузки фона
     * @param _background - путь к фоновому изображению
     */
    public Showcase(String _background, int _x, int _y) {
        super(_background, _x,  _y);
        SET_SCREEN = 0;

        label = component.createLabel("ВИТРИНА",  new Color(1,0,0,1));


        //go_fortune = createImageButton("img/bt/off.png", "img/bt/on.png");
        //buttonSetPos(go_fortune, 0, (HEIGHT/2)-(go_fortune.getHeight()/2));
        //listenerButton(go_fortune, 1);
//
        //go_start_game = createImageButton("img/bt/off.png", "img/bt/on.png");
        //buttonSetPos(go_start_game, WIDTH-go_start_game.getWidth(), (HEIGHT/2)-(go_start_game.getHeight()/2));
        //listenerButton(go_start_game, 2);
//
        //go_recipes = createImageButton("img/bt/off.png", "img/bt/on.png");
        //buttonSetPos(go_recipes, (WIDTH/2)-(go_recipes.getWidth()/2), 0);
        //listenerButton(go_recipes, 3);
//
        component.addActor(label);

    }


    @Override public void drawBackground() {
        super.drawBackground();
        background.draw();
        label.setPosition(100, 100);
        component.getStage().draw();

    }

    @Override
    public void dispose() {
        super.dispose();
    }


}