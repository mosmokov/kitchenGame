package com.wradchuk.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wradchuk.utils.Utils;

public class ToolBar {

    private float x, y;

    private Sprite toolbar_bg;
    private Sprite toolbar_menu;
    private Sprite toolbar_relit;
    private Sprite toolbar_selit;
    private Sprite toolbar_cap;
    private Sprite toolbar_energy;
    private Sprite toolbar_close;


    public ToolBar() {
        toolbar_bg     = Utils.createSprite("widget/toolbar/toolbar_bg.png", 0, 1200);
        toolbar_menu   = Utils.createSprite("widget/toolbar/toolbar_menu.png", 10, 1215);
        toolbar_relit  = Utils.createSprite("widget/toolbar/toolbar_relit.png", 87, 1215);
        toolbar_selit  = Utils.createSprite("widget/toolbar/toolbar_selit.png", 212, 1215);
        toolbar_cap    = Utils.createSprite("widget/toolbar/toolbar_cap.png", 353, 1215);
        toolbar_energy = Utils.createSprite("widget/toolbar/toolbar_energy.png", 483, 1215);
        toolbar_close  = Utils.createSprite("widget/toolbar/toolbar_close.png", 660, 1215);
    }


    public void render(SpriteBatch _batch) {

        _batch.begin();
        toolbar_bg.draw(_batch);
        toolbar_menu.draw(_batch);
        toolbar_relit.draw(_batch);
        toolbar_selit.draw(_batch);
        toolbar_cap.draw(_batch);
        toolbar_energy.draw(_batch);
        toolbar_close.draw(_batch);
        _batch.end();
    }

    public void dispose() {
        Utils.dispose(toolbar_bg);
        Utils.dispose(toolbar_menu);
        Utils.dispose(toolbar_relit);
        Utils.dispose(toolbar_selit);
        Utils.dispose(toolbar_cap);
        Utils.dispose(toolbar_energy);
        Utils.dispose(toolbar_close);
    }
}
