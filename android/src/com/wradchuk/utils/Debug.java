package com.wradchuk.utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import org.jetbrains.annotations.NotNull;

public class Debug {
    private static final String TEG = "kitchen: ";
    public static void  debug(String _output) { Gdx.app.log(TEG, _output);}
    public static void  debug(boolean _output) {Gdx.app.log(TEG, ""+_output);}
    public static void  debug(int _output) {Gdx.app.log(TEG, ""+_output);}
    public static void  debug(float _output) {Gdx.app.log(TEG, ""+_output);}
    public static void  debug(@NotNull Vector2 _output) {Gdx.app.log(TEG, "{"+_output.x+"|"+_output.y+"}");}
    public static void  debug(int[] ints) {
        Gdx.app.log(TEG, "{"+ints[0]+"|"+ints[1]+"}");
    }
    public static int   getFPS(){return Gdx.graphics.getFramesPerSecond();}
    public static float getTime() { return Gdx.graphics.getDeltaTime();}
    public static BitmapFont createFonts(String _font) {
        BitmapFont font;
        String FONT_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя abcdefghijklmnopqrstuvwxyz АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ ABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890.:,;'\"(!?)+-*/=";
        FileHandle fontFile = Gdx.files.internal(_font);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FONT_CHARACTERS;
        parameter.size = 22;
        parameter.color.add(Color.BLACK);

        font = generator.generateFont(parameter);

        generator.dispose();
        return font;
    }
    public static void treadSleep(int _ms)  {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Debug.debug(e.getMessage());
        }
    }
}