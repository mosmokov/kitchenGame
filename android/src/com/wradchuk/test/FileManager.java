package com.wradchuk.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

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
    }
}