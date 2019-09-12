package com.wradchuk.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wradchuk.game.MainMenu;
import com.wradchuk.game.object.MyImage;
import com.wradchuk.game.object.MySprite;
import com.wradchuk.utils.Debug;

public class Screen {
    public int WIDTH           =     -1; // Ширина экрана
    public int HEIGHT          =     -1; // Высота экрана
    public int SET_SCREEN      =     -1;
    public int fx, fy                  ; // финальная позиция экрана
    public int cx              =      0; // Глобальная координата X для объектов (текущая)
    public int cy              =      0; // Глобальная координата Y для объектов (текущая)
    public SpriteBatch batch           ; // Холст для отрисовки объектов
    public MyImage background          ; // Фоновое изображение
    public MyComponent component       ; // Дизайн интерфейса


    /***
     * Коструктор для загрузки фона
     * @param _background - путь к фоновому изображению
     * @param _x - координата по X
     * @param _y - координата по Y
     */
    public Screen(String _background, int _x, int _y) {

        batch      = new SpriteBatch();

        component = new MyComponent("gdx/uiskin.json", "gdx/font.ttf", 44);

        WIDTH      = Gdx.graphics.getWidth();
        HEIGHT     = Gdx.graphics.getHeight();

        background = new MySprite(_background, _x, _y);
        background.setBatch(batch);
        setPos(_x,_y);
        fx = _x; fy = _y;
    }
    /***
     * Освобождает все ресурсы этого объекта.
     */
    public void dispose() {
        Debug.dispose(batch);
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

}
