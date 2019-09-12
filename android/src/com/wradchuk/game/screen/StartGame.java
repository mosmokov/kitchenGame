package com.wradchuk.game.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class StartGame extends Screen {

    public Label label;
    ImageButton go_showcase;

    /***
     * Коструктор для загрузки фона
     * @param _background - путь к фоновому изображению
     */
    public StartGame(String _background, int _x, int _y) {
        super(_background, _x,  _y);
        SET_SCREEN = 2;

        label = component.createLabel("СТАРТ", new Color(1,0,0,1));
        label.setPosition(100, 100);



        component.addActor(label);

    }

    @Override public void drawBackground() {
        super.drawBackground();
        background.draw();

       label.setPosition(100, 100);
       // buttonSetPos(go_showcase, 0, (HEIGHT/2)-(go_showcase.getHeight()/2));
        component.getStage().draw();
    }

}