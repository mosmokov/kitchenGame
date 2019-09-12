package com.wradchuk.game.screen;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Fortune extends  Screen {

    public Label label;
    ImageButton go_showcase;


    /***
     * Коструктор для загрузки фона
     * @param _background - путь к фоновому изображению
     */
    public Fortune(String _background, int _x, int _y) {
        super(_background, _x,  _y);
        SET_SCREEN = 1;

        label = component.createLabel("ФОРТУНА",  new Color(1,0,0,1));
        label.setPosition(100, 100);

        //go_showcase = createImageButton("img/bt/off.png", "img/bt/on.png");
        //buttonSetPos(go_showcase, WIDTH-go_showcase.getWidth(), (HEIGHT/2)-(go_showcase.getHeight()/2));
        //listenerButton(go_showcase, 0);

        component.addActor(label);
    }

    @Override public void drawBackground() {
        super.drawBackground();
        background.draw();

        label.setPosition(100, 100);
        //buttonSetPos(go_showcase, WIDTH-go_showcase.getWidth(), (HEIGHT/2)-(go_showcase.getHeight()/2));
        component.getStage().draw();
    }


}