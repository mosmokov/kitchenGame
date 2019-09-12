package com.wradchuk.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wradchuk.utils.Debug;


/**
 * Класс описывает базовое изобрпжение.
 */
public class MyImage {

    private SpriteBatch batch  ; // Холст для отрисовки
    private float       width  ; // Ширина изображения
    private float       height ; // Высота изображения
    private double      SCL    ; // Коэффициент масштаба
    private int         realX  ; // Реальная координата X
    private int         realY  ; // Реальная координата Y
    private float       sclX   ; // Координата X с учетом маштаба
    private float       sclY   ; // Координата Y с учетом маштаба
    private double      screenX; // Позиция мышки по X
    private double      screenY; // Позиция мышки по Y


    /**
     * Конструктор по умолчанию
     */
    public MyImage() {}
    /**
     *
     * @param _img путь к файлу
     * @return объект Текстура
     */
    public Texture loadTexture(String _img) { return new Texture(_img); }
    /**
     *
     * @param _img путь к файлу
     * @return объект Спрайт
     */
    public Sprite  loadSprite (String _img) { return new Sprite(loadTexture(_img)); }
    /**
     * Установить коэффициент масштаба
     * Формула расчета в глобальном классе Debug
     */
    public void sclFactor() { SCL = Debug.KOF(); }
    /**
     * Указывает на чем рисовать
     * @param _batch холст для рисования
     */
    public void setBatch(SpriteBatch _batch) { batch = _batch; }
    /**
     * Устанавливаем ширину изображения
     * с коэффициентом масштаба
     * @param _width ширина
     */
    public void setWidth(float _width) { width = (_width*(float) SCL); }
    /**
     * Устанавливаем высоту изображения
     * с коэффициентом масштаба
     * @param _height высота
     */
    public void setHeight(float _height) { height = (_height*(float) SCL); }
    /**
     * Перезаписать координаты мышки
     * @param _screenX мышка по X
     * @param _screenY мышка по Y
     */
    public void setMouse(double _screenX, double _screenY) {
        screenX = _screenX;
        screenY = (double)Gdx.graphics.getHeight()-_screenY;
    }
    /**
     * Вернёт ширину изображения
     * @return ширина
     */
    public float getWidth() { return width;}
    /**
     * Вернёт высоту изображения
     * @return высота
     */
    public float getHeight() { return height;}
    /**
     * Расположить изображение по X
     * с учетом коэффициента масштаба
     * @param x реальная координата X
     */
    public void setPosX(int x) {
        realX = x;
        sclX = ((float) Gdx.graphics.getWidth()/2.0f)  + ((float)x*(float) SCL) - (width/2);
    }
    /**
     ** Расположить изображение по Y
     * с учетом коэффициента масштаба
     * @param y реальная координата Y
     */
    public void setPosY(int y) {
        realY = y;
        sclY = ((float)Gdx.graphics.getHeight()/2.0f) + ((float)y*(float) SCL) - (height/2);
    }
    /**
     * Проверка на вхождение мышки
     * внутрь изображения
     * @return вернёт правду, если входит
     */
    public boolean collision() {
        if(screenX >= sclX &&
           screenX <= sclX+getWidth() &&
           screenY >= sclY &&
           screenY <= sclY+getHeight()) return true;
        else return false;
    }
    /**
     * Создаёт карту анимации
     * @param cols количество кадров width/cols
     * @param rows количество кадров height/rows
     * @param frameDur длительность кадра
     * @return текущий объект
     */
    public MyImage addMap(int cols, int rows, float frameDur) {return this;}
    /**
     * Позиция кадра анимации на экране
     * @param x реальная координата X
     * @param y реальная координата Y
     * @return  текущий объект
     */
    public MyImage position(int x, int y) {return this;}
    /**
     * Функция для отрисовки
     * потомков
     */
    public void draw(){};
    /**
     * Рисует Спрайт
     * @param _sprite какой Спрайт нарисовать
     */
    public void draw(Sprite _sprite) {
        batch.begin();
        batch.draw(_sprite, sclX, sclY, getWidth(), getHeight());
        batch.end();
    }
    /**
     * Рисут кадр анимации
     * @param _textureRegion Какой кадр рисовать
     */
    public void draw(TextureRegion _textureRegion) {
        batch.begin();
        batch.draw(_textureRegion, sclX, sclY, getWidth(), getHeight());
        batch.end();
    }
    /**
     * Позиция объекта с учотом маштаба
     * @return координата X
     */
    public float getPosX () { return sclX ;}
    /**
     * Позиция объекта с учотом маштаба
     * @return координата Y
     */
    public float getPosY () { return sclY ;}

    public void box(Texture texture) {
        batch.begin();
        if(collision()) batch.draw(texture, getPosX(), getPosY(), getWidth(), getHeight());
        batch.end();
    }


    // Сомнительный блок кода, думаю потом уберу ///////
    //////                                   ///////////
    public int   getRealX() { return realX;} ///////////
    public int   getRealY() { return realY;} ///////////
    ////////////////////////////////////////////////////

}
