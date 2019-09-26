package com.wradchuk.game.object;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class MySprite extends MyImage {

    public Sprite sprite;
    public MySprite(String _file, int x, int y) {
        sclFactor();
        sprite = loadSprite(_file);
        setWidth(sprite.getWidth());
        setHeight(sprite.getHeight());
        setPosX(x);
        setPosY(y);
    }

    @Override
    public MySprite position(int x, int y) {
        setPosX(x);
        setPosY(y);
        return this;
    }
    @Override public void draw() {
        super.draw();
        sclFactor();
        draw(sprite);
    }
}
