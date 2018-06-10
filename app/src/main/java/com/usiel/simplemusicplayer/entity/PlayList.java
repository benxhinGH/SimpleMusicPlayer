package com.usiel.simplemusicplayer.entity;

import java.util.List;

/**
 * 播放列表
 */
public class PlayList {

    private String name;
    private final long date;
    private List<Song> songList;


    public PlayList(String name, long date, List<Song> songList) {
        this.name = name;
        this.date = date;
        this.songList = songList;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }



    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }


    @Override
    public String toString() {
        return "PlayList{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", songList=" + songList +
                '}';
    }
}
