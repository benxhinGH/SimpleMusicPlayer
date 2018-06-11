package com.usiel.simplemusicplayer.interfaces;

import android.widget.SeekBar;

public interface PlayUIControl {
    void setSeekBarMax(int max);
    void seekBarSeekTo(int progress);
    void setSeekBarListener(SeekBar.OnSeekBarChangeListener listener);
    void setMusicName(String name);
    void setSingerName(String name);
    void setTimeText(String timeText);
}
