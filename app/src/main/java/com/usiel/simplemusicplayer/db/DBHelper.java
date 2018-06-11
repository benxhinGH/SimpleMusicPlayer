package com.usiel.simplemusicplayer.db;

import android.util.Log;

import com.usiel.simplemusicplayer.entity.Song;

import org.litepal.LitePal;

import java.util.List;

public class DBHelper {

    private DBHelper(){

    }

    private static class SingletonHolder{
        private static final DBHelper INSTANCE=new DBHelper();
    }

    public static DBHelper getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public List<Song> getSongsBuffer(){
        List<Song> songs=LitePal.findAll(Song.class);
        Log.d("DBHelper","查询歌曲缓存："+songs.toString());
        return songs;
    }

    public void updateSongsBuffer(List<Song> songs){
        LitePal.deleteAll(Song.class);
        insertSongs(songs);
    }


    private void insertSongs(List<Song> songs){
        for(Song s:songs){
            s.save();
        }
    }
}
