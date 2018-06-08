package com.usiel.simplemusicplayer.entity;

/**
 * 歌曲实体类
 */
public class Song {
    private String name;
    private String singerName;
    private String path;
    /**
     * 歌曲文件大小，字节数
     */
    private int fileSize;
    /**
     * 创建时间，毫秒数
     */
    private long createTime;

    public Song(String name, String singerName, String path, int fileSize, long createTime) {
        this.name = name;
        this.singerName = singerName;
        this.path = path;
        this.fileSize = fileSize;
        this.createTime = createTime;
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

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", singerName='" + singerName + '\'' +
                ", path='" + path + '\'' +
                ", fileSize=" + fileSize +
                ", createTime=" + createTime +
                '}';
    }
}
