package com.batost.musicPlayer;

import android.media.MediaPlayer;


public class SongPlayer {
    static MediaPlayer instance;

    public static MediaPlayer getInstance() {
        if (instance == null) {
            instance = new MediaPlayer();
        }

        return instance;
    }

    public static int currentIndex = -1;
}
