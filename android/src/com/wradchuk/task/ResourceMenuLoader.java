package com.wradchuk.task;

import android.os.AsyncTask;
import com.wradchuk.game.Menu;
import com.wradchuk.main.Launcher;
import com.wradchuk.utils.Debug;

/***
 * Отвечает за загрузку ресурсов меню
 */
public class ResourceMenuLoader extends AsyncTask<Void, Integer, Void> {
    public Menu mMenu; // Ссылка на объект меню
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
       // Launcher.setProgressLoader(values[0]);
    }
    @Override
    protected Void doInBackground(Void... voids) {
        loaderResource();
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Debug.debug(mMenu.getTextureBackground().getTextureObjectHandle());
        mLoaderListener.onComplete();
    }

    private void loaderResource() {
        mMenu.setTextureBackground("img/1.png");
        mMenu.setSpriteBackground();
        loading_test();
    }
    private void loading_test() {
        for(int i = 0; i < 1; i+=1)
        {
            publishProgress(i);
           Debug.treadSleep(100);

        }
        publishProgress(1);
    }
}