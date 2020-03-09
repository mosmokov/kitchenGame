package com.wradchuk;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.sokolov.androidsizes.ISize;
import com.sokolov.androidsizes.SizeFromDisplay;
import com.wradchuk.utils.keyboard.AndroidView;
import com.wradchuk.utils.keyboard.ApplicationBundle;
import com.wradchuk.main.Core;
import com.wradchuk.utils.sys.LogOut;
import com.wradchuk.utils.sys.PatchedAndroidApplication;

public class AndroidLauncher extends PatchedAndroidApplication {
	public static PatchedAndroidApplication context                  ; // Контекст приложения

	private View rootView;
	private AndroidView androidView;
	private int width, height;

	/***
	 * Стартует приложение
	 * @param savedInstanceState
	 */
	@Override protected void onCreate (Bundle savedInstanceState) {
		context = this;
		super.onCreate(savedInstanceState);



		// узнаем размеры экрана из класса Display
		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics metricsB = new DisplayMetrics();
		display.getMetrics(metricsB);
		int a = metricsB.widthPixels;
		int a1 = metricsB.heightPixels;
		System.out.println("KITCHEN " + a);
		System.out.println("KITCHEN " + a1);

		DisplayMetrics displaymetrics = getResources().getDisplayMetrics();

		System.out.println("KITCHEN " + displaymetrics.widthPixels);
		System.out.println("KITCHEN " + displaymetrics.heightPixels);


		hideSystemUI(this);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		rootView = this.getWindow().getDecorView().getRootView();
		Rect rect = new Rect();
		rootView.getWindowVisibleDisplayFrame(rect);
		width = rect.width();
		height = rect.height();
		androidView = new AndroidView(width, height);
		rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

			@Override public void onLayoutChange(View v, int left, int top, int right,
				int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

				Rect rect = new Rect();
				rootView.getWindowVisibleDisplayFrame(rect);

				if(!(width == rect.width() && height == rect.height())) {
					width = rect.width();
					height = rect.height();
					androidView.onSizeChange(width, height);
				}
			}
		});

		initialize(new Core(new ApplicationBundle(androidView), context, getScreenSize(), a1), config);
	}


	public int[] getScreenSize() {
		ISize size = new SizeFromDisplay(getWindowManager().getDefaultDisplay());
		return new int[] {size.width(), size.height()};
	}


	public static void hideSystemUI(PatchedAndroidApplication _application) {
		// Enables regular immersive mode.
		// For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
		// Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
		View decorView = _application.getWindow().getDecorView();
		decorView.setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_IMMERSIVE
						// Set the content to appear under the system bars so that the
						// content doesn't resize when the system bars hide and show.
						| View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						// Hide the nav bar and status bar
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_FULLSCREEN);
	}
	// Shows the system bars by removing all the flags
	// except for the ones that make the content appear under the system bars.
	public static void showSystemUI(PatchedAndroidApplication _application) {
		View decorView = _application.getWindow().getDecorView();
		decorView.setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
	}
}