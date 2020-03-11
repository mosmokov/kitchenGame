package com.wradchuk.utils.sys;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import com.badlogic.gdx.backends.android.AndroidApplication;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/***
 * Был добавлен очень давно, не помню что исправляет, но
 * без него не работает.
 */
public class PatchedAndroidApplication extends AndroidApplication {

    private View decorView;
    public boolean useImeMode = true;

    private final ExecutorService exec = Executors.newSingleThreadExecutor();
    private final Runnable forcePause = new Runnable() {
        @Override public void run() {
            try {
                Thread.sleep(100);
            } catch(InterruptedException ex) { LogOut.logEx(ex.getMessage()); }
            graphics.onDrawFrame(null); }
    };

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility == 0) {
                    if(useImeMode) decorView.setSystemUiVisibility(hideSystemBars());
                }

            }
        });
    }

    @Override public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
            if(hasFocus) {
                if(useImeMode) decorView.setSystemUiVisibility(hideSystemBars());
            }

    }

    @Override protected void onResume() {
        super.onResume();
        if(useImeMode) decorView.setSystemUiVisibility(hideSystemBars());
    }

    @Override protected void onPause () {
        if(useImmersiveMode) { exec.submit(forcePause); }
        super.onPause();

    }



    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

}