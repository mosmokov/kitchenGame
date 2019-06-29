package com.wradchuk.game;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.wradchuk.R;
import com.wradchuk.main.Launcher;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MyVideoPlayer extends Activity {

    String vidAddress = "https://pointsales.buisness-app.ru/game/video/1.mp4";
    Uri vidUri = Uri.parse(vidAddress);
    private Button button;
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

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==button.getId()) {
                    Intent intent = new Intent(MyVideoPlayer.this, Launcher.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
     super.onDestroy();

    }
}
