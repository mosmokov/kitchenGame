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
    public Fortune(String _background, int _x, int _y, InputMultiplexer __multiplexer) {
        super(_background, _x,  _y, __multiplexer);
        SET_SCREEN = 1;

        label = new Label("ФОРТУНА", skin, "my-font", new Color(1,0,0,1));
        labelSetPos(label, 100, 100);

        go_showcase = createImageButton("img/bt/off.png", "img/bt/on.png");
        buttonSetPos(go_showcase, WIDTH-go_showcase.getWidth(), (HEIGHT/2)-(go_showcase.getHeight()/2));
        listenerButton(go_showcase, 0);

        stage.addActor(label);
        stage.addActor(go_showcase);
    }

    @Override public void drawBackground() {
        super.drawBackground();
        batch.begin();
        batch.draw(background, cx, 0);
        batch.end();

        labelSetPos(label, 100, 100);
        buttonSetPos(go_showcase, WIDTH-go_showcase.getWidth(), (HEIGHT/2)-(go_showcase.getHeight()/2));
        stage.draw();
        go_showcase.clearActions();
    }


}