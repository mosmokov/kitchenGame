package com.wradchuk.task;

import android.os.AsyncTask;

import com.wradchuk.game.Menu;
import com.wradchuk.main.Launcher;
import com.wradchuk.utils.Debug;

/***
 * Отвечает за загрузку ресурсов меню
 */
public class ResourceMenuLoader extends AsyncTask<Void, Integer, Void> {

    private Menu mMenu; // Ссылка на объект меню
    private LoaderListener mLoaderListener; // Интерфейс для прослушки загрузки ресурсов

    /***
     * Конструктор загрузчика по умолчанию
     */
    public ResourceMenuLoader() {}

    /***
     * Конструктор загрузчика меню, переопределённый
     * @param menu ссылка на класс отвечающий за меню
     * @param loaderListener слушатель состояния загрузки
     */
    public ResourceMenuLoader(Menu menu, LoaderListener loaderListener) {
        mMenu = menu;
        mLoaderListener = loaderListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Launcher.setProgressLoader(values[0]);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        loaderResource();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mLoaderListener.onComplete();
    }

    private void loaderResource() {
        Debug.treadSleep(2000);
        publishProgress(100);
        Debug.treadSleep(2000);
        mMenu.setTextureBackground("img/fon/bg_1.png");
        Debug.treadSleep(2000);
        publishProgress(500);
        mMenu.setSpriteBackground();
        Debug.treadSleep(2000);
        publishProgress(Launcher.WIDTH);
    }
}