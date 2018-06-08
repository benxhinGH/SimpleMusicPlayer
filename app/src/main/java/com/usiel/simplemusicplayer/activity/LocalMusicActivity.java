package com.usiel.simplemusicplayer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.usiel.simplemusicplayer.R;

import butterknife.BindView;

public class LocalMusicActivity extends AppCompatActivity {

    private final String title="localmusic";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_local_music)
    RecyclerView rvLocalMusic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_music);
    }
}
