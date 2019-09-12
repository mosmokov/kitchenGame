package com.wradchuk.game.screen;


import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Recipes extends Screen {

    public Label label;
    ImageButton go_showcase;

    /***
     * Коструктор для загрузки фона
     * @param _background - путь к фоновому изображению
     */
    public Recipes(String _background, int _x, int _y) {
        super(_background, _x,  _y);
        SET_SCREEN = 3;

        label = component.createLabel("РЕЦЕПТЫ",  new Color(1,0,0,1));
        label.setPosition(100, 100);

        //go_showcase = createImageButton("img/bt/off.png", "img/bt/on.png");
        //buttonSetPos(go_showcase, (WIDTH/2)-(go_showcase.getWidth()/2), 0);
        //listenerButton(go_showcase, 0);

        component.addActor(label);

    }

    @Override public void drawBackground() {
        super.drawBackground();
        background.draw();
        label.setPosition(100, 100);
        //buttonSetPos(go_showcase, (WIDTH/2)-(go_showcase.getWidth()/2), HEIGHT-go_showcase.getHeight());
        component.getStage().draw();
    }

}