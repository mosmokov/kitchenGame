package com.wradchuk.widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.wradchuk.main.Core;
import com.wradchuk.utils.Utils;

public class WidgetToolBar {
    public Core core;

    private Table table;

    private Texture toolbar_bg;
    private ImageButton toolbar_menu;

    private ImageButton toolbar_relit;
    private Label label_relit;

    private ImageButton toolbar_selit;
    private Label label_selit;

    private ImageButton toolbar_cap;
    private Label label_cap;

    private ImageButton toolbar_energy;
    private Label label_energy;

    private ImageButton toolbar_close;

    public WidgetToolBar(float _x, float _y) {
        table = new Table();
        table.setPosition(_x, _y);
    }

    public ImageButton createIB(String _file) {
        return Utils.createImageButton(_file, _file);
    }

    public void init(Stage _stage, Core _core) {

        toolbar_bg     = new Texture("widget/toolbar/toolbar_bg.png");
        toolbar_menu   = createIB("widget/toolbar/toolbar_menu.png");

        toolbar_relit  = createIB("widget/toolbar/toolbar_relit.png");
        label_relit = new Label("1/10", _core.skin);

        toolbar_selit  = createIB("widget/toolbar/toolbar_selit.png");
        label_selit = new Label("1/10", _core.skin);

        toolbar_cap    = createIB("widget/toolbar/toolbar_cap.png");
        label_cap = new Label("1/10", _core.skin);

        toolbar_energy = createIB("widget/toolbar/toolbar_energy.png");
        label_energy = new Label("1/10", _core.skin);

        toolbar_close  = createIB("widget/toolbar/toolbar_close.png");

        table.setBackground(new TextureRegionDrawable(new TextureRegion(toolbar_bg)));

        table.add(toolbar_menu)  .padLeft(20).padRight(15).padTop(10).padBottom(10);
        table.add(toolbar_relit) .padLeft(15).padRight(10) .padTop(10).padBottom(10);
        table.add(label_relit)   .padLeft(10) .padRight(15).padTop(10).padBottom(10);
        table.add(toolbar_selit) .padLeft(15).padRight(10) .padTop(10).padBottom(10);
        table.add(label_selit)   .padLeft(10) .padRight(15).padTop(10).padBottom(10);
        table.add(toolbar_cap)   .padLeft(15).padRight(10) .padTop(10).padBottom(10);
        table.add(label_cap)     .padLeft(10) .padRight(15).padTop(10).padBottom(10);
        table.add(toolbar_energy).padLeft(15).padRight(10) .padTop(10).padBottom(10);
        table.add(label_energy)  .padLeft(10) .padRight(15).padTop(10).padBottom(10);
        table.add(toolbar_close) .padLeft(10).padRight(20).padTop(10).padBottom(10);
        table.pack();
        _stage.addActor(table);
    }
    public void render(Stage _stage) {
        _stage.act();
        _stage.draw();
    }
    public void dispose() {
        Utils.dispose(toolbar_bg);
    }
}
