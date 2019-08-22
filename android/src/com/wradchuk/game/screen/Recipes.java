package com.wradchuk.game.screen;


import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.wradchuk.utils.Debug;

public class Recipes extends Screen {

    Label label;
    ImageButton go_showcase;

    /***
     * Коструктор для загрузки фона
     * @param _background - путь к фоновому изображению
     */
    public Recipes(String _background, int _x, int _y, InputMultiplexer __multiplexer) {
        super(_background, _x,  _y, __multiplexer);
        SET_SCREEN = 3;

        label = new Label("РЕЦЕПТЫ", skin, "my-font", new Color(1,0,0,1));
        labelSetPos(label, 100, 100);

        go_showcase = createImageButton("img/bt/off.png", "img/bt/on.png");
        buttonSetPos(go_showcase, (WIDTH/2)-(go_showcase.getWidth()/2), 0);
        listenerButton(0, go_showcase, 0);

        stage.addActor(label);
        stage.addActor(go_showcase);
        moved = false;
    }

    @Override public void drawBackground(int _id) {
        super.drawBackground(_id);
        batch.begin();
        batch.draw(background, 0, cy);
        batch.end();
        labelSetPos(label, 100, 100);
        buttonSetPos(go_showcase, (WIDTH/2)-(go_showcase.getWidth()/2), HEIGHT-go_showcase.getHeight());
        stage.draw();
        go_showcase.clearActions();
    }


    @Override public void move(int _id) {
        super.move(_id);
        if (isListen(_id)) {
            if (cy<0) moveY(8);

        } else {
            if (cy > fy) moveY(-8);
        }
    }

    @Override public void stop() {
        super.stop();
        Debug.debug(cy + " " + fy);
        if(cy == fy || cy == fy + HEIGHT) moved = false;
        else moved = true;
    }

    @Override public void addProcessor() {super.addProcessor(); }
    @Override public void removeProcessor() { super.removeProcessor();}
}