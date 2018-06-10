package com.usiel.simplemusicplayer.engine;

import com.usiel.simplemusicplayer.entity.PlayList;

public class PlayControlCenter {


    private PlayList playList;


    private PlayControlCenter(){

    }

    private static class SingletonHolder{
        private static final PlayControlCenter INSTANCE=new PlayControlCenter();
    }

    public static PlayControlCenter getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void setPlayList(PlayList playList){
        this.playList=playList;
    }

    public PlayList getPlayList(){
        return playList;
    }


}
