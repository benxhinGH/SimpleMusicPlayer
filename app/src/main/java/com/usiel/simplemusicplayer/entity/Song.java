package com.usiel.simplemusicplayer.entity;


import org.litepal.crud.LitePalSupport;

/**
 * 歌曲实体类
 */
public class Song extends LitePalSupport {
    private String name;
    private String singerName;
    private String path;
    /**
     * 歌曲文件大小，字节数
     */
    private long fileSize;
    /**
     * 创建时间，毫秒数
     */
    private long createTime;
    /**
     * 持续时间
     */
    private int duration;

    public Song(String name, String singerName, String path, long fileSize, long createTime, int duration) {
        this.name = name;
        this.singerName = singerName;
        this.path = path;
        this.fileSize = fileSize;
        this.createTime = createTime;
        this.duration = duration;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", singerName='" + singerName + '\'' +
                ", path='" + path + '\'' +
                ", fileSize=" + fileSize +
                ", createTime=" + createTime +
                ", duration=" + duration +
                '}';
    }
}
