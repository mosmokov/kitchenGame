package com.wradchuk.widget;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.wradchuk.main.Core;
import com.wradchuk.utils.Utils;

public class WidgetToolBar {

    public float x;
    public float y;
    public Core core;
    public Stage stage;
    public SpriteBatch batch;

    private Sprite toolbar_bg;
    private ImageButton toolbar_menu;
    private ImageButton toolbar_relit;
    private ImageButton toolbar_selit;
    private ImageButton toolbar_cap;
    private ImageButton toolbar_energy;
    private ImageButton toolbar_close;

    public WidgetToolBar(Core _core) {
        this.core = _core;
        stage = core.stage;
        batch = core.batch;
    }

    public void setPosition(float _x, float _y) {
        this.x = _x;
        this.y = _y;
    }
    public ImageButton createIB(String _file, float _x, float _y) {
        return Utils.createImageButton(_file, _file, x+_x, y+_y);
    }


    public void init() {

        toolbar_bg     = Utils.createSprite("widget/toolbar/toolbar_bg.png", x, y);

        toolbar_menu   = createIB("widget/toolbar/toolbar_menu.png",  10, 15);
        toolbar_relit  = createIB("widget/toolbar/toolbar_relit.png", 87, 15);
        toolbar_selit  = createIB("widget/toolbar/toolbar_selit.png", 212, 15);
        toolbar_cap    = createIB("widget/toolbar/toolbar_cap.png", 353, 15);
        toolbar_energy = createIB("widget/toolbar/toolbar_energy.png", 483, 15);
        toolbar_close  = createIB("widget/toolbar/toolbar_close.png", 660, 15);

        stage.addActor(toolbar_menu);
        stage.addActor(toolbar_relit);
        stage.addActor(toolbar_selit);
        stage.addActor(toolbar_cap);
        stage.addActor(toolbar_energy);
        stage.addActor(toolbar_close);

        core.multiplexer.addProcessor(stage);
    }
    public void render() {
        batch.begin();
        toolbar_bg.draw(batch);
        batch.end();

        stage.draw();
    }
    public void dispose() {
        Utils.dispose(toolbar_bg);
        Utils.dispose(stage);
        Utils.dispose(batch);
    }
}
