package com.wradchuk.utils.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MyComponent {

    public static ImageButton.ImageButtonStyle style(String background, String off, String on, String checked) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        //style.up           = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(background))));
        //style.down         = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(background))));
        //style.checked      = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(background))));
        style.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(off))));
        style.imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(on))));
        //style.imageChecked = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(checked))));
        //style.unpressedOffsetY = -20; // to "not" center the icon
        //style.unpressedOffsetX = -30; // to "not" center the icon
        return style;
    }
    public static ImageButton createImageButton(String off, String on) {
        return new ImageButton(style(off, off, on, off));
    }
    public static BitmapFont setFont(String _font, int _size) {
        BitmapFont res;
        String FONT_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя abcdefghijklmnopqrstuvwxyz АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ ABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890.:,;'\"(!?)+-*/=";
        FileHandle fontFile = Gdx.files.internal(_font);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FONT_CHARACTERS;
        parameter.size = _size;
        //parameter.color.add(Color.BLACK);
        res = generator.generateFont(parameter);
        generator.dispose();
        return res;
    }
}
