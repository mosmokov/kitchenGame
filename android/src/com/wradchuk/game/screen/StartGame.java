package com.wradchuk.game.screen;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class StartGame extends Screen {

    Label label;
    ImageButton go_showcase;

    /***
     * Коструктор для загрузки фона
     * @param _background - путь к фоновому изображению
     */
    public StartGame(String _background, int _x, int _y, InputMultiplexer __multiplexer) {
        super(_background, _x,  _y, __multiplexer);
        SET_SCREEN = 2;

        label = new Label("СТАРТ", skin, "my-font", new Color(1,0,0,1));
        labelSetPos(label, 100, 100);

        go_showcase = createImageButton("img/bt/off.png", "img/bt/on.png");
        buttonSetPos(go_showcase, 0, (HEIGHT/2)-(go_showcase.getHeight()/2));
        listenerButton(0, go_showcase, 0);



        stage.addActor(label);
        stage.addActor(go_showcase);
        moved = false;
    }

    @Override public void drawBackground(int _id) {
        super.drawBackground(_id);
        batch.begin();
        batch.draw(background, cx, 0);
        batch.end();
        labelSetPos(label, 100, 100);
        buttonSetPos(go_showcase, 0, (HEIGHT/2)-(go_showcase.getHeight()/2));
        stage.draw();
        go_showcase.clearActions();
    }


    @Override public void move(int _id) {
        super.move(_id);
        if (isListen(_id)) {
            if (cx > 0) moveX(-10);
        } else {
            if (cx < fx) moveX(10);
        }
    }

    @Override public void stop() {
        super.stop();
        if(cx == fx || cx == fx-WIDTH) moved = false;
        else moved = true;
    }

    @Override public void addProcessor() {super.addProcessor(); }
    @Override public void removeProcessor() { super.removeProcessor();}
}