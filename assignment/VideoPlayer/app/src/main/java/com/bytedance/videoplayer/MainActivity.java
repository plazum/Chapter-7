package com.bytedance.videoplayer;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private MyVideoView videoView;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ImageView imageView = findViewById(R.id.imageView);
//        String url = "https://s3.pstatp.com/toutiao/static/img/logo.271e845.png";
//        Glide.with(this).load(url).into(imageView);
        videoView = findViewById(R.id.video_view);
        mediaController = new MediaController(this);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);

        Intent intent = getIntent();
        Uri realUri;
        Uri data = intent.getData();

        if(data != null) {
            realUri = data;
        } else {
            realUri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + R.raw.sample);
        }

        videoView.setVideoURI(realUri);
        videoView.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            Objects.requireNonNull(getSupportActionBar()).hide();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            Objects.requireNonNull(getSupportActionBar()).show();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
