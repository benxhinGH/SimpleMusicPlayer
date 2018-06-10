package com.usiel.simplemusicplayer.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.usiel.simplemusicplayer.R;
import com.usiel.simplemusicplayer.engine.PlayControlCenter;
import com.usiel.simplemusicplayer.service.MusicPlayService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayActivity extends AppCompatActivity {

    private String title="play";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_play_or_pause)
    Button btnPlayOrPause;
    @BindView(R.id.btn_last_song)
    Button btnLastSong;
    @BindView(R.id.btn_next_song)
    Button btnNextSong;
    @BindView(R.id.seekbar)
    SeekBar seekBar;


    private MusicPlayService musicPlayService;

    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicPlayService=((MusicPlayService.MyBinder)service).getService();
            musicPlayService.switchPlayList(PlayControlCenter.getInstance().getPlayList());
            musicPlayService.init();
            seekBar.setMax(musicPlayService.getCurrentDuration());
            handler.post(seekRunnable);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicPlayService=null;
        }
    };


    private Handler handler=new Handler();

    private Runnable seekRunnable=new Runnable() {
        @Override
        public void run() {
            seekBar.setProgress(musicPlayService.getCurrentProgress());
            handler.postDelayed(seekRunnable,500);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bindMusicPlayService();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                musicPlayService.seekTo(seekBar.getProgress());
            }
        });

    }

    private void bindMusicPlayService(){
        Intent intent=new Intent(this,MusicPlayService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    @OnClick(R.id.btn_play_or_pause)
    void onClickBtnPlayOrPause(){
        musicPlayService.playOrPause();
    }

    @OnClick(R.id.btn_last_song)
    void onClickBtnLastSong(){
        musicPlayService.manualLastSong();
    }

    @OnClick(R.id.btn_next_song)
    void onClickBtnNextSong(){
        musicPlayService.manualNextSong();
    }


    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();

    }
}
