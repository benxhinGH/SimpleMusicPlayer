package com.usiel.simplemusicplayer.engine;

import android.media.MediaPlayer;

public class MyPlayer {

    private MediaPlayer mediaPlayer;



    private MyPlayer(){

    }

    private static class SingletonHolder{
        private static final MyPlayer INSTANCE=new MyPlayer();
    }

    public static MyPlayer getInstance(){
        return SingletonHolder.INSTANCE;
    }
}
