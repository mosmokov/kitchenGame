package com.wradchuk.main;

import android.os.Bundle;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wradchuk.utils.Debug;
import com.wradchuk.utils.MyVideoPlayerAndroid;
import com.wradchuk.utils.PatchedAndroidApplication;
import java.io.FileNotFoundException;

public class Launcher extends PatchedAndroidApplication implements ApplicationListener {

	public static int  WIDTH = -1;
	public static int HEIGHT = -1;
	public Viewport viewport;
	public Camera cam;
	public Mesh mesh;
	public MyVideoPlayerAndroid player;



	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(this, config);

	}
	@Override protected void onPause() {
		super.onPause();
		Gdx.graphics.requestRendering();
	}
	@Override public    void  create() {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		Gdx.gl20.glViewport(0, 0, WIDTH, HEIGHT);
		viewport = new FitViewport(WIDTH, HEIGHT);
		cam = viewport.getCamera();

		mesh = new Mesh(true, 4, 6, VertexAttribute.Position(), VertexAttribute.TexCoords(0));
		//@formatter:off
		mesh.setVertices(new float[] {0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0});
		//@formatter:on
		mesh.setIndices(new short[] {0, 1, 2, 2, 3, 0});

		player = new MyVideoPlayerAndroid(cam, mesh, GL20.GL_TRIANGLES);
		FileHandle handle = Gdx.files.internal("1.mp4");


		try {
			Debug.debug("Player: "+ player.play(handle));
		} catch (FileNotFoundException e) {
			Debug.debug(e.getMessage());
		}

	}
	@Override public    void  render() {
		Gdx.gl20.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		player.render();
	}

	@Override public    void dispose() {player.dispose();}
	@Override public    void  resize(int width, int height) {
		WIDTH = width; HEIGHT = height;
		Gdx.gl20.glViewport(0,0, WIDTH, HEIGHT);
		player.resize(width, height);
	}
	@Override public    void   pause() {player.pause();}
	@Override public    void  resume() {player.resume();}
}