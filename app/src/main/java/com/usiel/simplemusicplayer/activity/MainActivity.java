package com.usiel.simplemusicplayer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.usiel.simplemusicplayer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String title="SimpleMusicPlayer";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_local_music)
    Button btnLocalMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

    }

    @OnClick(R.id.btn_local_music)
    void onClickBtnLocalMusic(){

    }
}
