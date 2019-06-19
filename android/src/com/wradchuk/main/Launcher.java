package com.wradchuk.main;

import android.os.Bundle;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.wradchuk.utils.PatchedAndroidApplication;

public class Launcher extends PatchedAndroidApplication implements ApplicationListener {

	public static int  WIDTH = -1;
	public static int HEIGHT = -1;



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


	}
	@Override public    void  render() {
		Gdx.gl20.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

	}

	@Override public    void dispose() {}
	@Override public    void  resize(int width, int height) {
		WIDTH = width; HEIGHT = height;
		Gdx.gl20.glViewport(0,0, WIDTH, HEIGHT);
	}
	@Override public    void   pause() {}
	@Override public    void  resume() {}
}