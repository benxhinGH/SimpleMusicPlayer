package com.usiel.simplemusicplayer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtil {

    /**
     * key
     */
    private final String SONGS_BUFFERED_TIME="songs_buffered_time";


    private SharedPreferences sharedPreferences;

    private PreferenceUtil(){

    }

    private static class SingletonHolder{
        private static final PreferenceUtil INSTANCE=new PreferenceUtil();
    }

    public static PreferenceUtil getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void init(Context context){
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void updateSongsBufferedTime(long time){
        sharedPreferences.edit().putLong(SONGS_BUFFERED_TIME,time).apply();
    }

    public long getSongsBufferedTime(){
        return sharedPreferences.getLong(SONGS_BUFFERED_TIME,0l);
    }

}
