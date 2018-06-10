package com.usiel.simplemusicplayer.tools;

import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.util.Log;

import com.usiel.simplemusicplayer.entity.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 音频文件扫描器
 */
public class MusicScanner {

    private List<File> cache;

    private Set<String> suffix;

    public MusicScanner(){
        suffix=new HashSet<>();
        suffix.add(".mp3");
    }

    public List<Song> scanSongs(){
        scan();
        MediaMetadataRetriever mmr=new MediaMetadataRetriever();
        List<Song> songs=new ArrayList<>();
        for(File file:cache){
            mmr.setDataSource(file.getPath());
            String name=mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String singerName=mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String duration=mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            songs.add(new Song(name,singerName,file.getPath(),file.length(),file.lastModified(),Integer.valueOf(duration)));
        }
        return songs;
    }



    public List<File> scan(){
        if(cache!=null)cache.clear();
        else cache=new ArrayList<>();
        File dir=Environment.getExternalStorageDirectory();
        Log.d("MusicScanner","根目录为："+dir);
        scanDir(dir);

        return cache;
    }

    private void scanDir(File dir){
        File[] files=dir.listFiles();

        for(File file:files){
            if(file.isDirectory()){
                scanDir(file);
            }else if (isMusic(file)){
                cache.add(file);
            }
        }
    }

    private boolean isMusic(File file){
        String name=file.getName();
        //隐藏文件
        if(name.startsWith("."))return false;
        if(suffix.contains(getFileNameSuffix(name)))return true;
        return false;
    }

    private String getFileNameSuffix(String name){
        int index=name.lastIndexOf(".");
        if(index==-1)return null;
        return name.substring(index);
    }
}
