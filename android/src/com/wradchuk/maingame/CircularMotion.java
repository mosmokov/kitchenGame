package com.wradchuk.maingame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.wradchuk.utils.gui.Box;

/***
 * Класс для описания кругового движения
 */
@SuppressWarnings("FieldCanBeLocal")
public class CircularMotion {

    private final float screenWidth ;
    private final float screenHeight;
    private int radius;
    private int inorPix = 10;
    private Vector2 circleCenter;


    private final int ALL;
    private MyObject[] object;
    public float[]   p;


    public CircularMotion(float _screenWidth, float _screenHeight) {
        this.screenWidth = _screenWidth;
        this.screenHeight = _screenHeight;

        ALL = (int)screenWidth/64;

        object = new MyObject[ALL];
        p = new float[ALL];

        radius = (int) screenWidth;

        int pos_y = (int) (screenHeight/100) * 15;
        circleCenter = new Vector2(screenWidth / 2, 0-(radius-pos_y));

        for(int i = 0; i < ALL; i++) {
            object[i] = new MyObject("res/fish/silver_carp_1.png");
            object[i].setCircle(circleCenter, radius);
            object[i].setCord(screenWidth / 2, screenHeight / 2);
        }

        p[0] = -(radius-inorPix);
        for(int i = 1; i < ALL; i++) p[i] = p[i-1]+128;



    }

    public void draw(SpriteBatch _batch, Box _box) {
        drawCircle(_box, 5);

        for(int i = 0; i < ALL; i++) {
            p[i]=object[i].move(p[i], inorPix);
            object[i].drawObject(_batch);
        }

    }
    public void drawCircle(Box _box, int _lineWidth) {
        _box.setLineWidth(_lineWidth);
        _box.setColor(1,0,0,1);
        _box.drawCircle(circleCenter.x, circleCenter.y, radius);
    }

    public void dispose() {
        for(int i = 0; i < ALL; i++) object[i].dispose();
    }

}