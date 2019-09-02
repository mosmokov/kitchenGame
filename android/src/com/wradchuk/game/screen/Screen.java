package com.wradchuk.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.wradchuk.game.MainMenu;
import com.wradchuk.utils.Debug;

public class Screen {
    public int WIDTH           =     -1; // Ширина экрана
    public int HEIGHT          =     -1; // Высота экрана
    public int SET_SCREEN      =     -1;
    public int fx, fy                  ; // финальная позиция экрана
    public int cx              =      0; // Глобальная координата X для объектов (текущая)
    public int cy              =      0; // Глобальная координата Y для объектов (текущая)
    public SpriteBatch batch           ; // Холст для отрисовки объектов
    public Texture background          ; // Фоновое изображение

    public BitmapFont font;
    public Skin skin;
    public Stage stage;
    public InputMultiplexer multiplexer;


    /***
     * Коструктор для загрузки фона
     * @param _background - путь к фоновому изображению
     * @param _x - координата по X
     * @param _y - координата по Y
     */
    public Screen(String _background, int _x, int _y, InputMultiplexer _multiplexer) {
        batch      = new SpriteBatch();
        WIDTH      = Gdx.graphics.getWidth();
        HEIGHT     = Gdx.graphics.getHeight();
        background = setBackground(_background, WIDTH, HEIGHT);

        stage      = new Stage();

        skin       = new Skin(Gdx.files.internal("gdx/uiskin.json"));
        font = Debug.createFonts("gdx/font.ttf", 44);
        skin.add("my-font", font, BitmapFont.class);
        setPos(_x,_y);
        fx = _x; fy = _y;
        multiplexer = _multiplexer;
        multiplexer.addProcessor(stage);
    }
    /***
     * Освобождает все ресурсы этого объекта.
     */
    public void dispose() {
        Debug.dispose(batch);
        Debug.dispose(background);
        Debug.dispose(stage);
    }

    boolean l_g_move = false; // детектор движения

    public int moveTo(int a, int b) {
        double res;
        if(a==b) res = b;
        else {
            l_g_move = true;
            double h = (((double)b-(double)a)/(double)66);
            if(Math.abs(h)<1) {
                if(h>0) h = 1;
                else h = -1;
            }
            res = a+h;
        }
        return (int) res;
    }

    /***
     * Рисует заданный вами фон
     */
    public void drawBackground() {

        l_g_move = false; // Проверяем есть ли движения

        if(MainMenu.SET_SCREEN==0) { // ВИТРИНА
            MainMenu.screens[1].cx= // Убераем меню ФОРТУНА
                    moveTo(MainMenu.screens[1].cx, MainMenu.screens[1].fx);

            MainMenu.screens[2].cx= // Убераем меню СТАРТ_ИГРЫ
                    moveTo(MainMenu.screens[2].cx, MainMenu.screens[2].fx);

            MainMenu.screens[3].cy= // Убераем меню РЕЦЕПТЫ
                    moveTo(MainMenu.screens[3].cy, MainMenu.screens[3].fy);

        }
        if(MainMenu.SET_SCREEN==1) { // ВИТРИНА

            MainMenu.screens[1].cx= // Открываем меню ФОРТУНА
                    moveTo(MainMenu.screens[1].cx,
                            MainMenu.screens[1].fx+WIDTH);

            MainMenu.screens[2].cx= // Убераем меню СТАРТ_ИГРЫ
                    moveTo(MainMenu.screens[2].cx, MainMenu.screens[2].fx);

            MainMenu.screens[3].cy= // Убераем меню РЕЦЕПТЫ
                    moveTo(MainMenu.screens[3].cy, MainMenu.screens[3].fy);

        }
        if(MainMenu.SET_SCREEN==2) { // ВИТРИНА

            MainMenu.screens[1].cx= // Убираем меню ФОРТУНА
                    moveTo(MainMenu.screens[1].cx,
                            MainMenu.screens[1].fx);

            MainMenu.screens[2].cx= // Открываем меню СТАРТ_ИГРЫ
                    moveTo(MainMenu.screens[2].cx, MainMenu.screens[2].fx-WIDTH);

            MainMenu.screens[3].cy= // Убераем меню РЕЦЕПТЫ
                    moveTo(MainMenu.screens[3].cy, MainMenu.screens[3].fy);

        }
        if(MainMenu.SET_SCREEN==3) { // ВИТРИНА

            MainMenu.screens[1].cx= // Убираем меню ФОРТУНА
                    moveTo(MainMenu.screens[1].cx,
                            MainMenu.screens[1].fx);

            MainMenu.screens[2].cx= // Убираем меню СТАРТ_ИГРЫ
                    moveTo(MainMenu.screens[2].cx, MainMenu.screens[2].fx);

            MainMenu.screens[3].cy= // Открываем меню РЕЦЕПТЫ
                    moveTo(MainMenu.screens[3].cy, MainMenu.screens[3].fy+HEIGHT);

        }

        if(!l_g_move) MainMenu.G_MOVE = false; // если нет движений включаем кнопки
    }
    /***
     * Установить начальные координаты экрана
     * @param _x Глобальная координата X для объектов (текущая)
     * @param _y Глобальная координата X для объектов (текущая)
     */
    public void setPos(int _x, int _y) {
        cx = _x; cy = _y;
    }
    /***
     *  Растягивает фоновое изображение
     *  по ширене и высоте экрана
     * @param _img путь к изображению
     * @param _width ширина экрана
     * @param _height высота экрана
     * @return вернёт текстуру для фона
     */
    private Texture setBackground(String _img, int _width, int _height) {
        return Debug.setBackground(_img, _width, _height);
    }

    public TextureRegionDrawable createTexture(String _file) {
        Texture myTexture = new Texture(_file);
        TextureRegion myTextureRegion = new TextureRegion(myTexture);
        return new TextureRegionDrawable(myTextureRegion);
    }
    public ImageButton createImageButton(String _file) {
        TextureRegionDrawable texture = createTexture(_file);
        ImageButton result = new ImageButton(texture);
        result.setSize(texture.getRegion().getRegionWidth(), texture.getRegion().getRegionHeight());
        return result;
    }
    public ImageButton createImageButton(String _up, String _down) {
        TextureRegionDrawable up = createTexture(_up);
        TextureRegionDrawable down = createTexture(_down);
        ImageButton result = new ImageButton(up);
        result.setSize(up.getRegion().getRegionWidth(), up.getRegion().getRegionHeight());
        result.getStyle().imageUp = up;
        result.getStyle().imageDown = down;
        return result;
    }
    public void buttonSetPos(ImageButton _button, float _x, float _y) {
        _button.setPosition(cx+_x, cy+_y);
    }
    public void labelSetPos(Label _label, float _x, float _y) {
        _label.setPosition(cx+_x, cy+_y);
    }
    public void listenerButton(ImageButton _button, final int _screen_id) {

        _button.addListener(new InputListener() {
            @Override public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                Debug.debug(MainMenu.SET_SCREEN +" a "+_screen_id);

            if(!MainMenu.G_MOVE) {
                MainMenu.G_MOVE=true;
                 if (MainMenu.SET_SCREEN == 1 && _screen_id == 2) MainMenu.SET_SCREEN = 0;
            else if (MainMenu.SET_SCREEN == 1 && _screen_id == 3) MainMenu.SET_SCREEN = 1;
            else if (MainMenu.SET_SCREEN == 2 && _screen_id == 1) MainMenu.SET_SCREEN = 0;
            else if (MainMenu.SET_SCREEN == 2 && _screen_id == 3) MainMenu.SET_SCREEN = 2;
            else if (MainMenu.SET_SCREEN == 3 && _screen_id == 1) MainMenu.SET_SCREEN = 3;
            else if (MainMenu.SET_SCREEN == 3 && _screen_id == 2) MainMenu.SET_SCREEN = 3;
            else if (MainMenu.SET_SCREEN == 1 && _screen_id == 1) MainMenu.SET_SCREEN = 0;
            else if (MainMenu.SET_SCREEN == 2 && _screen_id == 2) MainMenu.SET_SCREEN = 0;
            else if (MainMenu.SET_SCREEN == 3 && _screen_id == 3) MainMenu.SET_SCREEN = 0;
            else MainMenu.SET_SCREEN = _screen_id;
            }

        Debug.debug(MainMenu.SET_SCREEN +" b "+_screen_id);

            }
            @Override public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true; }
        });
    }

}
