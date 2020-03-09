package com.wradchuk.utils.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;


public class Box {
    public SpriteBatch batch;
    public int[] leftTop = new int[2];
    public int[] rightTop = new int[2];
    public int[] leftDown = new int[2];
    public int[] rightDown = new int[2];
    public int[] center = new int[2];
    private Color box_color;
    private Color set_bg_color;
    private Color set_color;
    public ShapeRenderer renderer;
    int width;
    int height;

    public Box(int width, int height, SpriteBatch _batch) {
        this.width = width;
        this.height = height;
        leftTop  [0]  =     0; leftTop  [1] = this.height;
        rightTop [0]  = this.width; rightTop [1] = this.height;
        batch = _batch;

        leftDown [0]  =     0; leftDown [1] =     0;
        rightDown[0]  = this.width; rightDown[1] =     0;
        center[0] = this.width/2; center[1] = this.height/2;

        this.renderer      = new ShapeRenderer();
        box_color = new Color(0,0,0,1);
        set_bg_color  = new Color(0,0,0,1);
        set_color     = new Color(1,1,1,1);


    }
    public void resize(int width, int height) {
        leftTop  [0]  =  0  ; leftTop  [1] = height;
        rightTop [0]  = width; rightTop [1] = height;
        leftDown [0]  =    0; leftDown [1] =     0;
        rightDown[0]  = width; rightDown[1] =     0;
        center[0] = width/2; center[1] = height/2;
    }


    public void boxColor(float r, float g, float b, float a) {
        box_color = new Color(r, g, b, a);
        renderer.setColor(box_color);
    }
    public void boxColor(Color rgba) {
        box_color = new Color(rgba.r,rgba.g,rgba.b,rgba.a);
        renderer.setColor(box_color);

    }
    public void fonClear(float r, float g, float b, float a) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(r, g, b, a);
    }
    public void fonClear(Color rgba) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(rgba.r,rgba.g,rgba.b,rgba.a);
    }
    public void setColor(float r, float g, float b, float a) {
        set_color = new Color(r, g, b, a);
        renderer.setColor(set_color);
    }
    public void setColor(Color rgba) {
        set_color = new Color(rgba.r,rgba.g,rgba.b,rgba.a);
        renderer.setColor(set_color);
    }
    public void setLineWidth(int width) {
        Gdx.gl.glLineWidth(width);
    }

    public void drawPoint(Vector2 vector2) {
        batch.begin();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.line(vector2.x-5, vector2.y, vector2.x+5, vector2.y);
        renderer.end();
        batch.end();
    }
    public void drawPoint(float x, float y) {
        batch.begin();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.line(x-5, y, x+5, y);
        renderer.end();
        batch.end();
    }

    public void drawLine(Vector2 start, Vector2 end) {
        batch.begin();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.line(start, end);
        renderer.end();
        batch.end();
    }
    public void drawLine(float x, float y, float x1, float y1) {
        batch.begin();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.line(x, y, x1, y1);
        renderer.end();
        batch.end();
    }
    public void drawCircle(float x, float y, float radius) {
        batch.begin();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.circle(x, y, radius);
        renderer.end();
        batch.end();
    }
    public void boxDrawPoint(int pointSize) {
        setLineWidth(pointSize);
        drawPoint(leftTop[0], leftTop[1]);
        drawPoint(rightTop[0], rightTop[1]);
        drawPoint(rightDown[0], rightDown[1]);
        drawPoint(leftDown[0], leftDown[1]);

    }
    public void boxDrawLine(int lineWidth) {
        setLineWidth(lineWidth);
        drawLine(leftTop[0], leftTop[1], rightTop[0], rightTop[1]);
        drawLine(rightTop[0], rightTop[1], rightDown[0], rightDown[1]);
        drawLine(rightDown[0], rightDown[1], leftDown[0], leftDown[1]);
        drawLine(leftDown[0], leftDown[1], leftTop[0], leftTop[1]);
    }

    public void dispose() {
        renderer.dispose();
    }


}