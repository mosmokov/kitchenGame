package com.wradchuk;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.wradchuk.utils.keyboard.AndroidView;
import com.wradchuk.utils.keyboard.ApplicationBundle;
import com.wradchuk.main.Core;
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

		initialize(new Core(new ApplicationBundle(androidView), context), config);
	}
}