package com.wradchuk.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.wradchuk.utils.Debug;

public class FileManager {

    boolean isExtAvailable = Gdx.files.isExternalStorageAvailable();
    boolean isLocAvailable = Gdx.files.isLocalStorageAvailable();

    String extRoot = Gdx.files.getExternalStoragePath();
    String locRoot = Gdx.files.getLocalStoragePath();

    FileHandle handle = Gdx.files.local("test.png");

    boolean exists = Gdx.files.local("test.png").exists();
    boolean isDirectory = Gdx.files.local("/").isDirectory();

    FileHandle[] files = Gdx.files.local("/").list();

    FileHandle parent = Gdx.files.local("test.png").parent();
    FileHandle child = Gdx.files.local("/").child("test.png");



    public FileManager() {

        Debug.debug("Отладка ");

        Debug.debug("Наличие флешки: " +isExtAvailable);
        Debug.debug("Наличие локал: " +isLocAvailable);

        Debug.debug("Путь в корень флешки: " +extRoot);
        Debug.debug("Путь в корень локал: " +locRoot);

        Debug.debug("Путь к файлу: " +handle.path());

        Debug.debug("Этот файл существует: " +exists);
        Debug.debug("Это директория: " +isDirectory);


        for(FileHandle file: files) {
           Debug.debug("Директория на флешке: "+file.path());
        }

        Debug.debug("Родительская директория: " +parent);
        Debug.debug("Потомок: " +child);



        Debug.debug("Заключение ");


    }
}
