package com.wradchuk;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.sokolov.androidsizes.ISize;
import com.sokolov.androidsizes.SizeFromDisplay;
import com.wradchuk.main.Core;
import com.wradchuk.utils.PatchedAndroidApplication;

public class AndroidLauncher extends PatchedAndroidApplication {
	public static PatchedAndroidApplication context                  ; // Контекст приложения

	private View rootView;
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




		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//config.useImmersiveMode = false;
		useImeMode=true;
		rootView = this.getWindow().getDecorView().getRootView();
		Rect rect = new Rect();
		rootView.getWindowVisibleDisplayFrame(rect);
		width = rect.width();
		height = rect.height();


		initialize(new Core(context), config);
	}


	public int[] getScreenSize() {
		ISize size = new SizeFromDisplay(getWindowManager().getDefaultDisplay());
		return new int[] {size.width(), size.height()};
	}



}