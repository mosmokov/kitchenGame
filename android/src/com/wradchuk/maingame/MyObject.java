package com.wradchuk.maingame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MyObject {
    private int     circleRadius;
    private Vector2 circleCenter;

    private Sprite sprite;
    private Vector2 cord;
    private double  angle;



    public MyObject(String _image) {
        sprite = new Sprite(new Texture(_image));
        sprite.setSize(sprite.getWidth(), sprite.getHeight());
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    }

    public void setCircle(Vector2 _circleCenter, int _radius) {
        circleCenter = _circleCenter;
        circleRadius = _radius;
    }

    public float getPointInCircleY(float x) {
        x=Math.abs(x);
        return (float)Math.sqrt(circleRadius*circleRadius-x*x);
    }
    //Начало нужных методов
    public double getAngle(double x){
        return Math.toDegrees((float)Math.PI-Math.acos(-x/circleRadius));
    }
    public Vector2  getPoint(float x){
        return new Vector2((float)(circleCenter.x+x),(float)(circleCenter.y+getPointInCircleY(x)));
    }


    public void setCord(float _x, float _y) {
        cord = new Vector2(_x-sprite.getWidth()/2, _y-sprite.getHeight()/2);
        sprite.setPosition(_x-sprite.getWidth()/2, _y-sprite.getHeight()/2);
    }
    public Vector2 getCord() {return cord;}

    public void setAngle(double _angle) {
        angle = -90+_angle;
    }


    public float getP(float _p, int _ignorPix) {
        float res = _p;
        if(res>circleRadius-_ignorPix) res = -(circleRadius-_ignorPix);
        else res++;

        return res;
    }

    public float move(float _hor, int _ignorPix) {
        _hor=getP(_hor, _ignorPix);

        Vector2 point = getPoint(_hor);
        setCord(point.x, point.y);
        double ang = getAngle(_hor);
        setAngle(ang);

        return _hor;
    }

    public void drawObject(SpriteBatch _batch) {
        sprite.setRotation((float)angle);
        _batch.begin();
        sprite.draw(_batch);
        _batch.end();
    }
    public void dispose() {
        sprite.getTexture().dispose();
    }
}