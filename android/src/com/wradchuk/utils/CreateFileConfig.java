package com.wradchuk.utils;

public class CreateFileConfig {

    public String write;
    public String file;
    public boolean flag;
    public Utils.DISCW discw;

    public CreateFileConfig(String _write, String _file, boolean _flag, Utils.DISCW _discw) {
        this.write = _write;
        this.file = _file;
        this.flag = _flag;
        this.discw = _discw;
    }
}
