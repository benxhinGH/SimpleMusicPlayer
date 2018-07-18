package com.usiel.simplemusicplayer.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.usiel.simplemusicplayer.R;
import com.usiel.simplemusicplayer.engine.PlayControlCenter;
import com.usiel.simplemusicplayer.interfaces.PlayUIControl;
import com.usiel.simplemusicplayer.service.MusicPlayService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayActivity extends AppCompatActivity implements PlayUIControl {

    private final String TAG=PlayActivity.class.getSimpleName();

    private String title="play";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_music_name)
    TextView tvMusicName;
    @BindView(R.id.tv_singer_name)
    TextView tvSingerName;
    @BindView(R.id.tv_time_text)
    TextView tvTimeText;
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
            Log.d(TAG,"binder is :"+service.toString());
            musicPlayService=((MusicPlayService.MyBinder)service).getService();
            musicPlayService.switchPlayList(PlayControlCenter.getInstance().getPlayList());
            musicPlayService.init(PlayActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicPlayService=null;
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
        super.onDestroy();
        unbindService(serviceConnection);
    }

    @Override
    public void setSeekBarMax(int max) {
        seekBar.setMax(max);
    }

    @Override
    public void seekBarSeekTo(int progress) {
        seekBar.setProgress(progress);
    }

    @Override
    public void setSeekBarListener(SeekBar.OnSeekBarChangeListener listener) {
        seekBar.setOnSeekBarChangeListener(listener);
    }

    @Override
    public void setMusicName(String name) {
        tvMusicName.setText(name);
    }

    @Override
    public void setSingerName(String name) {
        tvSingerName.setText(name);
    }

    @Override
    public void setTimeText(String timeText) {
        tvTimeText.setText(timeText);
    }
}
