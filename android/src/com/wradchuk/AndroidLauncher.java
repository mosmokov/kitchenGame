package com.wradchuk;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.wradchuk.main.Core;
import com.wradchuk.utils.PatchedAndroidApplication;

public class AndroidLauncher extends PatchedAndroidApplication {
	public static PatchedAndroidApplication context                  ; // Контекст приложения

	private View rootView;

	/***
	 * Стартует приложение
	 * @param savedInstanceState
	 */
	@Override protected void onCreate (Bundle savedInstanceState) {
		context = this;
		super.onCreate(savedInstanceState);


		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//config.useImmersiveMode = false;
		useImeMode=true;
		rootView = this.getWindow().getDecorView().getRootView();
		Rect rect = new Rect();
		rootView.getWindowVisibleDisplayFrame(rect);

		initialize(new Core(context), config);
	}
}