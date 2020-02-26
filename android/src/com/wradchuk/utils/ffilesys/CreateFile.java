package com.wradchuk.utils.ffilesys;

import android.os.AsyncTask;

import com.wradchuk.utils.sys.Utils;
import com.wradchuk.utils.net.AsyncResultPasser;


public class CreateFile extends AsyncTask<CreateFileConfig, Void, CreateFileConfig> {
    public AsyncResultPasser delegate = null;
    public boolean state = false;


    public CreateFile(AsyncResultPasser resultPasser) { delegate = resultPasser; }

    @Override protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override protected CreateFileConfig doInBackground(CreateFileConfig... configs) {
        CreateFileConfig config = configs[0];
        Utils.write_file(config.write, config.flag, config.file, config.discw);
        return config;
    }
    @Override protected void onPostExecute(CreateFileConfig res) {
        super.onPostExecute(res);
        delegate.create_file(res);

    }
}