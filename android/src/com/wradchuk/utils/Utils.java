package com.wradchuk.utils;

import android.content.Context;
import android.content.Intent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;

import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
    public static enum DISCR {DEFAULT, LOCAL, INTERNAL, EXTERNAL, ABSOLUTE} // Диски чтения.
    public static enum DISCW {DEFAULT, LOCAL, EXTERNAL, ABSOLUTE}           // Диски записи.


    /***
     *  Выгружаю из памяти объекты не доступные для GC.
     * @param _disposable - Выгружаемый объект
     */
    public static void dispose(Disposable _disposable) {
        if(_disposable!=null) _disposable.dispose();
    }
    /***
     *  Выгружаю из памяти объекты не доступные для GC.
     * @param _disposable - Выгружаемый объект
     */
    public static void dispose(Sprite _disposable) {
        if(_disposable!=null) _disposable.getTexture().dispose();
    }
    /***
     * Смена активности в одну строку. Вызовите finish() ниже
     * если нужно завершить активность при её смене.
     * @param packageContext - контекст активности вызова
     * @param cls - Объект открываемой активности
     */
    public static void intent(Context packageContext, Class<?> cls) {
        Intent intent = new Intent(packageContext, cls);
        packageContext.startActivity(intent);
    }
    /***
     * Чтение файла в указанном диске
     * @param _file - Путь к файлу, например: "dir/f_name.type"
     * @param _discr - Диск расположения файла
     * @return Файл ввиде String
     */
    public static String  read_file (String _file, DISCR _discr) {
        FileHandle file = null;
        switch (_discr) {
            case LOCAL   : file = Gdx.files.local   (_file); break;
            case INTERNAL: file = Gdx.files.internal(_file); break;
            case EXTERNAL: file = Gdx.files.external(_file); break;
            case ABSOLUTE: file = Gdx.files.absolute(_file); break;
            default:;
        }

        boolean exists = file.exists();

        if(exists) return file.readString();
        else return "{}";
    }
    /***
     * Запись в файл в указанном диске (диск INTERNAL не подлежит записи)
     * @param _write - Что записать в файл
     * @param _flag - Перезаписать содержимое? ( true - нет / false - да )
     * @param _file - Путь к файлу, например: "dir/f_name.type"
     * @param _discw - Диск расположения файла
     */
    public static void write_file(String _write, boolean _flag, String _file, DISCW _discw) {

        FileHandle file = null;
        switch (_discw) {
            case ABSOLUTE: file = Gdx.files.absolute(_file); break;
            case LOCAL   : file = Gdx.files.local   (_file); break;
            case EXTERNAL: file = Gdx.files.external(_file); break;
            default:;
        }

        file.writeString(_write, _flag);

    }
    /***
     * Получить данные йз файла в формате JSON.
     * Чтобы сразу читать в объект JSONObject и обработать try-catch
     * @param _file - Путь к файлу, например: "dir/f_name.type"
     * @param _discr - Диск расположения файла
     * @return Вернёт полученные данные ввиде JSONObject
     */
    public static JSONObject read_json(String _file, DISCR _discr) {
        JSONObject res = null;
        try {
            res = new JSONObject(read_file(_file, _discr));
        } catch(JSONException ex) { LogOut.logEx(ex.getMessage()); }

        return res;
    }
    /***
     * Проверка на пустотность JSON
     * @param _json - Проверяемый JSONObject
     * @return Вернёт статус операции ( true - путой / false - есть данные )
     */
    public static boolean isEmptyJSON(JSONObject _json) {
        if(_json.toString().equals("{}")) return true;
        else return false;
    }
    /***
     * Создаём файл безопасным путём
     * @param _write - Что записать в файл
     * @param _file - Путь к файлу, например: "dir/f_name.type"
     * @param _flag - Перезаписать содержимое? ( true - нет / false - да )
     * @param _discw - Диск расположения файла
     * @param _passer - Интерфейс для безопасной работы
     */
    public static void create_file(String _write, String _file, boolean _flag, Utils.DISCW _discw, AsyncResultPasser _passer) {
        CreateFileConfig config = new CreateFileConfig(_write, _file, _flag, _discw);
        new CreateFile(_passer).execute(config);
    }


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