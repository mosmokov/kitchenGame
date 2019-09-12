package com.wradchuk.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.wradchuk.utils.Debug;

public class MyComponent {

    private double      SCL    ; // Коэффициент масштаба
    private Stage       stage  ;
    private BitmapFont  font   ;
    private Skin        skin   ;

    public MyComponent(String _skin, String _font, int _font_size) {
        sclFactor();
        stage = new Stage();
        skin       = new Skin(Gdx.files.internal(_skin));
        setFont(_font, _font_size);
        skin.add("my-font", font, BitmapFont.class);
    }


    public void setFont(String _font, int _size) {
         double font_s = (double)_size*SCL;
        String FONT_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя abcdefghijklmnopqrstuvwxyz АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ ABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890.:,;'\"(!?)+-*/=";
        FileHandle fontFile = Gdx.files.internal(_font);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FONT_CHARACTERS;
        parameter.size = (int)font_s;
        parameter.color.add(Color.BLACK);
        font = generator.generateFont(parameter);
        dispose(generator);
    }
    public void dispose(Disposable _disposable) {
        if(_disposable!=null) _disposable.dispose();

    }
    /**
     * Установить коэффициент масштаба
     * Формула расчета в глобальном классе Debug
     */
    public void sclFactor() { SCL = Debug.KOF(); }

    public Skin getSkin() {return skin;}
    public Stage getStage() {return stage;}


    public void addActor(Actor _actor) {
        stage.addActor(_actor);
    }

    public Label createLabel(String _text, Color _color) {
        return new Label(_text, skin, "my-font", _color);
    }


}
