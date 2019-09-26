package com.wradchuk.main;

import android.os.Bundle;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wradchuk.game.MainGame;
import com.wradchuk.game.MainMenu;
import com.wradchuk.game1.Level;
import com.wradchuk.manager.PlatformManager;
import com.wradchuk.utils.Debug;
import com.wradchuk.utils.PatchedAndroidApplication;

/***
 * Наш лаунчер, запускает приложение
 */
public class Launcher extends PatchedAndroidApplication implements ApplicationListener {

	public static PatchedAndroidApplication context;	// Контекст приложения
	private PlatformManager sPlatform;					// Проверка разрешений
	public static int WIDTH  = -1;						// Ширина дисплея
	public static int HEIGHT = -1;						// Высота дисплея
	private SpriteBatch sBatch;							// Холст для рисования
	private Texture sPerm;								// Изображение "Нет разрешений"


	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(this, config);
		context = this;
		sPlatform = new PlatformManager(context);
	}
	@Override protected void onPause() {super.onPause();}
	@Override public    void  create() {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT= Gdx.graphics.getHeight();
		Gdx.gl20.glViewport(0, 0, WIDTH, HEIGHT);
		sBatch = new SpriteBatch();
		sPerm = new Texture("img/no-internet-perm.jpg");
	}
	@Override public    void  render() {
		Gdx.gl20.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sPlatform.check();
		if(!sPlatform.isCheak) {
			sBatch.begin();
			sBatch.draw(sPerm,0,0, WIDTH, HEIGHT);
			sBatch.end();
		}
		else login();
	}
	@Override public    void dispose() {
		Debug.dispose(sBatch);
		Debug.dispose(sPerm);
	}
	@Override public    void  resize(int width, int height) {}
	@Override public    void   pause() {}



	@Override public    void  resume() {}
	private void login() {
		context.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				//Debug.Intent(new Login(), context, true);
				//Debug.Intent(new MainMenu(), context, true);
				//Debug.Intent(new MainGame(), context, true);
				Debug.Intent(new Level(), context, true);
			}
		});
	}
}