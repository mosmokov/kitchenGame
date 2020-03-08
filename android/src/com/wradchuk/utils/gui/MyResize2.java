package com.wradchuk.utils.gui;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class MyResize2 {

    public double set_w        = 1080.0; // Экран под который рисуем графику
    public double set_h        = 1920.0;

    public double user_w       =      0; // Размеры экрана пользователя
    public double user_h       =      0;

    public  double kofFHD              ; // Коффициэнт нашего Full HD
    public  double kofUser             ; // Коффициэнт юзера - экрана

    public double deltaPercent =      0; // Дельта будущего приращения

    public  double FHDKofUserW         ; // Коффициэнт нашего Full HD Weight к user Weight
    public  double FHDKofUserH         ; // Коффициэнт нашего Full HD Height к user Height

    public double kofPropScreen        ; // Коффициэнт пропорций сторон

    public  double onePerFHDKofUserW   ; // Один процент - Коффициэнт нашего Full HD Weight к user Weight
    public  double onePerFHDKofUserH   ; // Один процент - Коффициэнт нашего Full HD Height к user Height




    public double deltaPercent(double _kofFHD, double _kofUser) {
        return (_kofFHD/_kofUser%1);
    }



    public MyResize2(double _width, double _height) {
        this.user_w       = _width;
        this.user_h       = _height;

        kofFHD            =          set_w  / set_h;
        kofUser           = (double) user_w / (double)user_h;
        deltaPercent      = deltaPercent(kofFHD, kofUser);

        FHDKofUserW       = (double) user_w / set_w;
        FHDKofUserH       = (double) user_h / set_h;

        onePerFHDKofUserW =     FHDKofUserW / 100.0f;
        onePerFHDKofUserH =     FHDKofUserH / 100.0f;
        kofPropScreen     =     FHDKofUserW / FHDKofUserH;
    }

    public Sprite resizeSprite(Sprite _sprite) {
        Sprite res  = _sprite;
        double wPer = res.getWidth() / 100.0f;
        res.setSize((float) ( _sprite.getWidth() * onePerFHDKofUserW) * 100.0f, (float) (_sprite.getHeight() * onePerFHDKofUserH) * 100.0f );
        System.out.println( res.getWidth() + " x " +res.getHeight() );
        return res;
    }

}