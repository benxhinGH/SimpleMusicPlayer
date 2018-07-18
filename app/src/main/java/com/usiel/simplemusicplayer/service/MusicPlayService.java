package com.usiel.simplemusicplayer.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.SeekBar;

import com.usiel.simplemusicplayer.activity.PlayActivity;
import com.usiel.simplemusicplayer.engine.PlayControlCenter;
import com.usiel.simplemusicplayer.entity.PlayList;
import com.usiel.simplemusicplayer.entity.Song;
import com.usiel.simplemusicplayer.interfaces.PlayUIControl;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class MusicPlayService extends Service {

    private final String TAG=MusicPlayService.class.getSimpleName();

    /**
     * 播放模式，默认为顺序播放
     */
    private PlayMode mode=PlayMode.order;
    /**
     * 当前播放列表
     */
    private PlayList playList;
    /**
     * 当前歌曲列表
     */
    private List<Song> songList;

    private int currentSongIndex;


    private static final MediaPlayer mediaPlayer=new MediaPlayer();

    private Random random=new Random();

    private PlayUIControl playUIControl;

    private Handler handler=new Handler();

    private Runnable seekBarCtrl=new Runnable() {
        @Override
        public void run() {
            playUIControl.seekBarSeekTo(getCurrentProgress());
            playUIControl.setTimeText(mediaPlayer.getCurrentPosition()+"/"+mediaPlayer.getDuration());
            handler.postDelayed(seekBarCtrl,500);
        }
    };



    public enum PlayMode{
        singleLoop,listLoop,order,random
    }



    private MyBinder mBinder=new MyBinder();



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind........,mediaPlayer is :"+mediaPlayer.toString()+"mBinder is :"+mBinder.toString());
        return mBinder;
    }

    private void loadMusicAndPrepare(Song song){
        try{
            mediaPlayer.setDataSource(song.getPath());
            mediaPlayer.prepare();
            currentSongIndex=songList.indexOf(song);
            playUIControl.setMusicName(song.getName());
            playUIControl.setSingerName(song.getSingerName());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void init(PlayUIControl playUIControl){
        this.playUIControl=playUIControl;
        if(!mediaPlayer.isPlaying()) loadMusicAndPrepare(songList.get(0));
        else handler.post(seekBarCtrl);
        playUIControl.setSeekBarMax(getCurrentDuration());
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                autoNextSong();
            }
        });
        playUIControl.setSeekBarListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekTo(seekBar.getProgress());
            }
        });
        playUIControl.setMusicName(songList.get(currentSongIndex).getName());
        playUIControl.setSingerName(songList.get(currentSongIndex).getSingerName());
    }




    public void playOrPause(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            handler.removeCallbacks(seekBarCtrl);
        }else{
            mediaPlayer.start();
            handler.post(seekBarCtrl);
        }
    }

    private void switchSong(Song song){
        mediaPlayer.reset();
        loadMusicAndPrepare(song);
        playOrPause();
    }

    private void autoNextSong(){
        switch (mode){
            case singleLoop:
                playOrPause();
                break;
            case listLoop:
                switchToNextSongInList();
                break;
            case order:
                if(currentSongIndex!=songList.size()-1){
                    switchToNextSongInList();
                }
                break;
            case random:
                currentSongIndex=getNextRandom();
                switchSong(songList.get(currentSongIndex));
                break;
                default:
                    break;
        }
    }

    public void manualNextSong(){
        if(mode==PlayMode.random){
            currentSongIndex=getNextRandom();
            switchSong(songList.get(currentSongIndex));
        }else{
            switchToNextSongInList();
        }
    }

    public void manualLastSong(){
        currentSongIndex--;
        if(currentSongIndex<0){
            currentSongIndex=songList.size()-1;
        }
        switchSong(songList.get(currentSongIndex));
    }

    private void switchToNextSongInList(){
        currentSongIndex++;
        if(currentSongIndex>=songList.size()){
            currentSongIndex=0;
        }
        switchSong(songList.get(currentSongIndex));
    }

    private int getNextRandom(){
        int ran=random.nextInt(songList.size());
        while(ran==currentSongIndex&&songList.size()!=1){
            ran=random.nextInt(songList.size());
        }
        return ran;
    }

    public void switchPlayList(PlayList playList){
        this.playList=playList;
        this.songList=playList.getSongList();
    }

    public void seekTo(int mesc){
        mediaPlayer.seekTo(mesc);
    }

    public int getCurrentDuration(){
        return songList.get(currentSongIndex).getDuration();
    }

    public int getCurrentProgress(){
        return mediaPlayer.getCurrentPosition();
    }



    public class MyBinder extends Binder {
        public MusicPlayService getService(){
            return MusicPlayService.this;
        }
    }
}
