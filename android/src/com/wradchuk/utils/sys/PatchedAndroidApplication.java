package com.wradchuk.utils.sys;

import com.badlogic.gdx.backends.android.AndroidApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/***
 * Был добавлен очень давно, не помню что исправляет, но
 * без него не работает.
 */
public class PatchedAndroidApplication extends AndroidApplication {

    private final ExecutorService exec = Executors.newSingleThreadExecutor();
    private final Runnable forcePause = new Runnable() {
        @Override public void run() {
            try {
                Thread.sleep(100);
            } catch(InterruptedException ex) { LogOut.logEx(ex.getMessage()); }
            graphics.onDrawFrame(null);
        }
    };
    @Override protected void onPause () {
        if(useImmersiveMode) { exec.submit(forcePause); }
        super.onPause();
    }
}