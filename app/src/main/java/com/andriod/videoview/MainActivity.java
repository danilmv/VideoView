package com.andriod.videoview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private static final String PLAYBACK_TIME = "playback_time";
    private static final Uri VIDEO_LINK = Uri.parse("https://d2xzmw6cctk25h.cloudfront.net/records/000/007/821/641f41183bb4b4cef8077faceeaee0c7ca9926e2.mp4");
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.video_view);
        videoView.setVideoURI(VIDEO_LINK);

        videoView.setOnPreparedListener(mp -> {
            MediaController mediaController = new MediaController(MainActivity.this);
//attach mediaController after video is prepared, and control size is measured
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
        });

        if (savedInstanceState != null) {
            restorePosition(savedInstanceState);
        } else {
            videoView.start();
        }

        videoView.requestFocus();
    }

    private void restorePosition(Bundle savedInstanceState) {
        int currentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        if (currentPosition > 0)
            videoView.seekTo(currentPosition);

        videoView.start();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(PLAYBACK_TIME, videoView.getCurrentPosition());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restorePosition(savedInstanceState);
    }
}