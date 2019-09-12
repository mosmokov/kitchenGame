package com.wradchuk.game.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wradchuk.utils.Debug;

public class MyAnimSprite extends MyImage {

    public int                      FRAME_COLS  ;
    public int                      FRAME_ROWS  ;
    public float                    frameDur    ;
    public Animation<TextureRegion> animation   ;
    public Texture                  texture     ;
    public TextureRegion            currentFrame;
    public float                    stateTime   ;


    public MyAnimSprite(String _file) {
        sclFactor();
        texture = loadTexture(_file);
    }

    public MyAnimSprite addMap(int cols, int rows, float frameDur) {
        FRAME_COLS = cols;
        FRAME_ROWS = rows;
        this.frameDur = frameDur;


        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / FRAME_COLS,
                texture.getHeight() / FRAME_ROWS);

        TextureRegion[] regions = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                regions[index++] = tmp[i][j];
            }
        }

        animation = new Animation<TextureRegion>(frameDur, regions);
        stateTime = 0f;
        currentFrame = animation.getKeyFrame(stateTime, true);

        setWidth(currentFrame.getRegionWidth());
        setHeight(currentFrame.getRegionHeight());
        return this;
    }
    public MyAnimSprite position(int x, int y) {
        setPosX(x);
        setPosY(y);
        return this;
    }

    @Override public void draw() {
        super.draw();
        sclFactor();
        stateTime += Debug.getTime();
        currentFrame = animation.getKeyFrame(stateTime, true);
        draw(currentFrame);
    }
}
