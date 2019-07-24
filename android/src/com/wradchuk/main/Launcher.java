package com.wradchuk.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.wradchuk.game.MyGoogle;
import com.wradchuk.game.TestRoom;
import com.wradchuk.utils.PatchedAndroidApplication;


public class Launcher extends PatchedAndroidApplication implements ApplicationListener {
	public static PatchedAndroidApplication context;
	public static int WIDTH = -1;
	public static int HEIGHT= -1;
	//public PlatformManager platform;
	public boolean complete = false;
	public boolean createLoading = false;
	//public GDXLoadingBar loadingBar;



	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(this, config);
		context = this;
		//platform = new PlatformManager(context);
	}
	@Override protected void onPause() {
		super.onPause();
	}
	@Override public    void  create() {
		Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		WIDTH = Gdx.graphics.getWidth();
		HEIGHT= Gdx.graphics.getHeight();

		context.runOnUiThread(new Runnable() {
			public void run() {
				//MyVideoPlayer myVideoPlayer = new MyVideoPlayer();
				//context.startActivity(new Intent(context, myVideoPlayer.getClass()));
				//context.finish();

				TestRoom testRoom = new TestRoom();
				context.startActivity(new Intent(context, testRoom.getClass()));
				context.finish();

				//MyGoogle myGoogle = new MyGoogle();
				//context.startActivity(new Intent(context, myGoogle.getClass()));
				//context.finish();
			}
		});



	}

	//public void createLoad() {
	//	if(complete && loadingBar==null) {
	//		loadingBar = new GDXLoadingBar(this);
	//		createLoading = true;
	//	}
	//}

	@RequiresApi(api = Build.VERSION_CODES.M)
	@Override public    void  render() {
		//platform.create();
		//complete = platform.complete;
		//if(!createLoading) createLoad();
		Gdx.gl20.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		//if(complete) loadingBar.render();
	}

	@Override public    void dispose() {
		//if(complete) loadingBar.dispose();
	}
	@Override public    void  resize(int width, int height) {}
	@Override public    void   pause() {}
	@Override public    void  resume() {}
}