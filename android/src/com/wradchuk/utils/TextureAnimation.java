package com.wradchuk.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureAnimation {
    // Constant rows and columns of the sprite sheet
    public static int FRAME_COLS = 6, FRAME_ROWS = 5;
    float frameDur = 0.0f;

    // Objects used
    public Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)
    public Texture walkSheet;
    public TextureRegion currentFrame;

    // A variable for tracking elapsed time for the animation
    public float stateTime;
    public TextureAnimation(String _texture, int x, int y, float frameDur) {
        // Load the sprite sheet as a Texture
                walkSheet = new Texture(_texture);
                FRAME_COLS = x;
                FRAME_ROWS = y;
        this.frameDur = frameDur;

        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / FRAME_COLS,
                walkSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        // Initialize the Animation with the frame interval and array of frames
        walkAnimation = new Animation<TextureRegion>(frameDur, walkFrames);
        // Instantiate a SpriteBatch for drawing and reset the elapsed animation
        // time to 0
        stateTime = 0f;
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);
    }


    public void draw(SpriteBatch _batch, float _x, float _y) {
        stateTime += Debug.getTime(); // Accumulate elapsed animation time
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        _batch.begin();
        _batch.draw(currentFrame, _x, _y); // Draw current frame at (50, 50)
        _batch.end();
    }

}
