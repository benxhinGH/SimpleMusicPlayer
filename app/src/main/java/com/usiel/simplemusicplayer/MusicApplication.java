package com.usiel.simplemusicplayer;

import android.app.Application;

import com.usiel.simplemusicplayer.utils.PreferenceUtil;

import org.litepal.LitePal;

public class MusicApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        PreferenceUtil.getInstance().init(this);
    }
}
