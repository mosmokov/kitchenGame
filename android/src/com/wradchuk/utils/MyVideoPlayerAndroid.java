package com.wradchuk.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLES32;
import android.util.Log;
import android.view.Surface;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.video.VideoPlayer;

/**
 * Android implementation of the VideoPlayer class.
 *
 * @author Rob Bogie <rob.bogie@codepoke.net>
 */
public class MyVideoPlayerAndroid implements VideoPlayer, OnFrameAvailableListener {

    public static final String ATTRIBUTE_TEXCOORDINATE = ShaderProgram.TEXCOORD_ATTRIBUTE + "0";
    public static final String VARYING_TEXCOORDINATE = "varTexCoordinate";
    public static final String UNIFORM_TEXTURE = "texture";
    public static final String UNIFORM_CAMERATRANSFORM = "camTransform";//camTransform
    public SpriteBatch batch;

    //@formatter:off
    public String vertexShaderCode = "attribute vec4 a_position;    \n" +
            "attribute mediump vec2 " + ATTRIBUTE_TEXCOORDINATE + ";" +
            "uniform mediump mat4 " + UNIFORM_CAMERATRANSFORM + ";" +
            "varying mediump vec2 " + VARYING_TEXCOORDINATE + ";" +
            "void main()                   \n" +
            "{                             \n" +
            "   gl_Position = " + UNIFORM_CAMERATRANSFORM + " * a_position ;  \n" +
            "   varTexCoordinate = " + ATTRIBUTE_TEXCOORDINATE + " ;\n" +
            "}                             \n";

    public String fragmentShaderCode = "#extension GL_OES_EGL_image_external : require\n" +
            "uniform samplerExternalOES " + UNIFORM_TEXTURE + ";" +
            "varying mediump vec2 " + VARYING_TEXCOORDINATE + ";" +
            "void main()                 \n" +
            "{                           \n" +
            "  gl_FragColor = texture2D(" + UNIFORM_TEXTURE + ", " + VARYING_TEXCOORDINATE + ");	\n" +
            "}";
    //@formatter:on

    public ShaderProgram shader;
    public int[] textures = new int[1];
    public SurfaceTexture videoTexture;

    public MediaPlayer player;
    public boolean prepared = false;
    public boolean frameAvailable = false;
    public boolean done = false;

    public Viewport viewport;
    public Camera cam;
    public Mesh mesh;

    public boolean customMesh = false;

    VideoSizeListener sizeListener;
    CompletionListener completionListener;
    public int primitiveType = GL20.GL_TRIANGLES;


    public MyVideoPlayerAndroid () {
        shader = new ShaderProgram(vertexShaderCode, fragmentShaderCode);
        //viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        customMesh = true;
        setupRenderTexture();
        cam = viewport.getCamera();
        mesh = new Mesh(true, 4, 6, VertexAttribute.Position(), VertexAttribute.TexCoords(0));
        //@formatter:off
        mesh.setVertices(new float[] {
                0, 0, 0, 0, 1,
                0, 0, 0, 1, 1,
                0, 0, 0, 1, 0,
                0, 0, 0, 0, 0
        });
        //@formatter:on
        mesh.setIndices(new short[] {0, 1, 2, 2, 3, 0});
    }

    @Override public boolean play (final FileHandle file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException("Could not find file: " + file.path());
        }

        player = new MediaPlayer();

        player.setOnPreparedListener(new OnPreparedListener() {
            @Override public void onPrepared (MediaPlayer mp) {
                float x = -mp.getVideoWidth() / 2;
                float y = -mp.getVideoHeight() / 2;
                float width = mp.getVideoWidth();
                float height = mp.getVideoHeight();

                //@formatter:off
                mesh.setVertices(
                        new float[] {x, y, 0, 0, 1, x + width, y, 0, 1, 1, x + width, y + height, 0, 1, 0, x, y + height, 0, 0, 0});
                //@formatter:on

                prepared = true;
                if (sizeListener != null) {
                    sizeListener.onVideoSize(width, height);
                }
                mp.start();
            }
        });
        player.setOnErrorListener(new OnErrorListener() {
            @Override public boolean onError (MediaPlayer mp, int what, int extra) {
                done = true;
                Log.e("VideoPlayer", String.format("Error occured: %d, %d\n", what, extra));
                return false;
            }
        });

        player.setOnCompletionListener(new OnCompletionListener() {
            @Override public void onCompletion (MediaPlayer mp) {
                done = true;
                if (completionListener != null) {
                    completionListener.onCompletionListener(file);
                }
            }
        });

        try {
            if (file.type() == FileType.Classpath || (file.type() == FileType.Internal && !file.file().exists())) {
                AssetManager assets = ((AndroidApplication)Gdx.app).getAssets();
                AssetFileDescriptor descriptor = assets.openFd(file.name());
                player.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            } else {
                player.setDataSource(file.file().getAbsolutePath());
            }
            player.setSurface(new Surface(videoTexture));
            player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override public void resize (int width, int height) {
        if (!customMesh) {

            viewport.update(width, height);
        }
    }

    @Override public boolean render () {
        if (done) {
            return false;
        }
        if (!prepared) {
            return true;
        }
        synchronized (this) {
            if (frameAvailable) {
                videoTexture.updateTexImage();
                frameAvailable = false;
            }
        }

        // Draw texture
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textures[0]);

        //cam.near=-10;
        //cam.far=+10;
        //cam.position = new Vector3(0,0,0);
        //cam.translate(0,0,100);
        //cam.rotate(1,0,0,0.1f);
        //cam.update();
        shader.begin();
        shader.setUniformMatrix(UNIFORM_CAMERATRANSFORM, cam.combined);
        mesh.render(shader, primitiveType);
        shader.end();
        //GLES20.glEnable(GLES20.GL_TEXTURE);







        return !done;
    }

    /**
     * For android, this will return whether the prepareAsync method of the android MediaPlayer is done with preparing.
     *
     * @return whether the buffer is filled.
     */
    @Override public boolean isBuffered () {
        return prepared;
    }

    @Override public void stop () {
        if (player != null && player.isPlaying()) {
            player.stop();
        }
        prepared = false;
        done = true;
    }

    public void setupRenderTexture () {
        // Generate the actual texture
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glGenTextures(1, textures, 0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textures[0]);

        videoTexture = new SurfaceTexture(textures[0]);
        videoTexture.setOnFrameAvailableListener(this);

    }

    @Override public void onFrameAvailable (SurfaceTexture surfaceTexture) {
        synchronized (this) {
            frameAvailable = true;
        }
    }

    @Override public void pause () {
        // If it is running
        if (prepared) {
            player.pause();
        }
    }

    @Override public void resume () {
        // If it is running
        if (prepared) {
            player.start();
        }
    }

    @Override public void dispose () {
        stop();

        videoTexture.detachFromGLContext();

        GLES20.glDeleteTextures(1, textures, 0);

        if (shader != null) {
            shader.dispose();
        }

        if (!customMesh && mesh != null) {
            mesh.dispose();
        }
    }

    @Override public void setOnVideoSizeListener (VideoSizeListener listener) {
        sizeListener = listener;
    }

    @Override public void setOnCompletionListener (CompletionListener listener) {
        completionListener = listener;
    }

    @Override public int getVideoWidth () {
        if (!prepared) {
            throw new IllegalStateException("Can't get width when video is not yet buffered!");
        }
        return player.getVideoWidth();
    }

    @Override public int getVideoHeight () {
        if (!prepared) {
            throw new IllegalStateException("Can't get height when video is not yet buffered!");
        }
        return player.getVideoHeight();
    }

    @Override public boolean isPlaying () {
        return player.isPlaying();
    }

}