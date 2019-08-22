package com.wradchuk.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Pixmap;
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
    public int nx              =      0; // Глобальная координата X для объектов (будущая)
    public int ny              =      0; // Глобальная координата Y для объектов (будущая)
    public SpriteBatch batch           ; // Холст для отрисовки объектов
    public Texture background          ; // Фоновое изображение

    public BitmapFont font;
    public Skin skin;
    public Stage stage;
    public InputMultiplexer multiplexer;
    public boolean moved = false;


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
    }
    /***
     * Освобождает все ресурсы этого объекта.
     */
    public void dispose() {
        Debug.dispose(batch);
        Debug.dispose(background);
        Debug.dispose(stage);
    }
    /***
     * Рисует заданный вами фон
     */
    public void drawBackground(int _id) {
        move(_id);
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
     * Узнать глобальную координату экрана по X
     * @return Глобальная координата X для объектов (текущая)
     */
    public int getX() {
        return cx;
    }
    /***
     * Узнать глобальную координату экрана по Y
     * @return Глобальная координата Y для объектов (текущая)
     */
    public int getY() {
        return cy;
    }
    /***
     * Сдвинуть экран по координате X
     * @param _step на сколько сдвинуть
     */
    public void moveX(int _step) {
        nx = getX() + _step;
        setPos(nx, ny);
    }
    /***
     * Сдвинуть экран по координате Y
     * @param _step на сколько сдвинуть
     */
    public void moveY(int _step) {
        ny = getY() + _step;
        setPos(nx, ny);
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
        Pixmap pixmap = new Pixmap(Gdx.files.internal(_img));
        Pixmap full = new Pixmap(_width, _height, pixmap.getFormat());
        full.drawPixmap(pixmap, 0, 0, pixmap.getWidth(), pixmap.getHeight(),
                0, 0, full.getWidth(), full.getHeight() );
        Texture result = new Texture(full);
        Debug.dispose(pixmap);
        Debug.dispose(full);
        return result;
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


    public boolean isListen(int _id) {
        if(_id==SET_SCREEN) {
          // multiplexer.addProcessor(stage);
            return true;
        } else {
           // multiplexer.removeProcessor(stage);
            return false;
        }
    }
    public void move(int _id) {
        isListen(_id);
    }

    public void listenerButton(final int id, ImageButton _button, final int _screen_id) {

        _button.addListener(new InputListener() {
            @Override public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                MainMenu.SET_SCREEN = _screen_id;
                MainMenu.screens[id].moved = true;
            }
            @Override public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                MainMenu.PREV_SCREEN = MainMenu.SET_SCREEN;
                return true; }
        });
    }
    public void stop() {}

    public void addProcessor() {
        multiplexer.addProcessor(stage);
    }
    public void removeProcessor() {
        multiplexer.removeProcessor(stage);
    }

}
