package com.wradchuk.game;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import com.wradchuk.R;

import android.util.DisplayMetrics;
import android.widget.MediaController;
import android.widget.VideoView;

public class MyVideoPlayer extends Activity {

    String vidAddress = "https://pointsales.buisness-app.ru/game/video/1.mp4";
    Uri vidUri = Uri.parse(vidAddress);
    VideoView vidView;

    public MyVideoPlayer() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_video_player);

        vidView = findViewById(R.id.myVideo);
        vidView.setVideoURI(vidUri);
        vidView.start();

        MediaController vidControl = new MediaController(this);
        vidControl.setAnchorView(vidView);
        vidView.setMediaController(vidControl);


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) vidView.getLayoutParams();
        params.width = metrics.widthPixels;
        params.height = metrics.heightPixels;
        params.leftMargin = 0;
        vidView.setLayoutParams(params);

        vidView.start();

        //Intent intent = new Intent(MyVideoPlayer.this, Launcher.class);
        //startActivity(intent);
        //finish();
    }

    @Override
    protected void onDestroy() {
     super.onDestroy();

    }
}
