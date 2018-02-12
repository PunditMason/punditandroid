package com.softuvo.ipundit.activities;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.softuvo.ipundit.R;

import java.io.IOException;

public class DummyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        WebView wv=findViewById(R.id.wv_img);
        String sHtmlTemplate = "<html><head></head><body><img src=\"file:///android_asset/img/broadcast.svg\"></body></html>";
        wv.loadDataWithBaseURL(null, sHtmlTemplate, "text/html", "utf-8",null);
    }
}
