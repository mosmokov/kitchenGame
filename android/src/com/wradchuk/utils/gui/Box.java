package com.wradchuk.utils.gui;

import com.badlogic.gdx.graphics.Color;
import com.wradchuk.main.Core;


public class Box {
    public int[] leftTop = new int[2];
    public int[] rightTop = new int[2];
    public int[] leftDown = new int[2];
    public int[] rightDown = new int[2];
    public int[] center = new int[2];
    private Color box_color;

    public Box(int width, int height) {
        leftTop  [0]  =     0; leftTop  [1] = height;
        rightTop [0]  = width; rightTop [1] = height;
        leftDown [0]  =     0; leftDown [1] =     0;
        rightDown[0]  = width; rightDown[1] =     0;
        center[0] = width/2; center[1] = height/2;
        box_color = new Color(0,0,0,1);
    }
    public void resize(int width, int height) {
        leftTop  [0]  =     0; leftTop  [1] = height;
        rightTop [0]  = width; rightTop [1] = height;
        leftDown [0]  =     0; leftDown [1] =     0;
        rightDown[0]  = width; rightDown[1] =     0;
        center[0] = width/2; center[1] = height/2;
    }


    public void boxColor(float r, float g, float b, float a) {
        box_color = new Color(r, g, b, a);
    }
    public void boxColor(Color rgba) {
        box_color = new Color(rgba.r,rgba.g,rgba.b,rgba.a);

    }
    public void boxDrawPoint(Core paint, int pointSize) {
        paint.setLineWidth(pointSize);
        paint.setColor(box_color);
        paint.drawPoint(leftTop[0], leftTop[1]);
        paint.drawPoint(rightTop[0], rightTop[1]);
        paint.drawPoint(rightDown[0], rightDown[1]);
        paint.drawPoint(leftDown[0], leftDown[1]);

    }
    public void boxDrawLine(Core paint, int lineWidth) {
        paint.setLineWidth(lineWidth);
        paint.setColor(box_color);
        paint.drawLine(leftTop[0], leftTop[1], rightTop[0], rightTop[1]);
        paint.drawLine(rightTop[0], rightTop[1], rightDown[0], rightDown[1]);
        paint.drawLine(rightDown[0], rightDown[1], leftDown[0], leftDown[1]);
        paint.drawLine(leftDown[0], leftDown[1], leftTop[0], leftTop[1]);
    }


}