package com.batost.musicPlayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SongPlayerActivity extends AppCompatActivity {

    TextView songNameTextView, currentTimeTextView, totalTimeTextView;
    SeekBar seekBar;
    ImageView playPauseImageView, nextSongImageView, previousSongImageView, playerIconImageView;
    ArrayList<AudioModel> songsList;
    AudioModel currentSong;
    MediaPlayer mediaPlayer = SongPlayer.getInstance();
    private LightAndWalkChangedReceiver lightAndWalkChangedReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_player);

        songNameTextView = findViewById(R.id.song_name_text);
        currentTimeTextView = findViewById(R.id.current_time);
        totalTimeTextView = findViewById(R.id.total_time);
        seekBar = findViewById(R.id.seek_bar);
        playPauseImageView = findViewById(R.id.play_pause_icon);
        nextSongImageView = findViewById(R.id.next_song_icon);
        previousSongImageView = findViewById(R.id.previous_song_icon);
        playerIconImageView = findViewById(R.id.player_icon);

        songNameTextView.setSelected(true);

        lightAndWalkChangedReceiver = new LightAndWalkChangedReceiver();
        IntentFilter intentFilter = new IntentFilter("com.batost.sensorapp");
        registerReceiver(lightAndWalkChangedReceiver, intentFilter);
//
//        songsList = (ArrayList<AudioModel>) getIntent().getSerializableExtra("SONGS");
//
//        setMusicWithResources();
//
//        SongPlayerActivity.this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (mediaPlayer != null) {
//                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
//                    currentTimeTextView.setText(convertTime(Integer.toString(mediaPlayer.getCurrentPosition())));
//
//                    if (mediaPlayer.isPlaying()) {
//                        playPauseImageView.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
//                    } else {
//                        playPauseImageView.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
//                    }
//                }
//
//                new Handler().postDelayed(this, 100);
//            }
//        });
//
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if (mediaPlayer!= null && fromUser) {
//                    mediaPlayer.seekTo(progress);
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        songsList = (ArrayList<AudioModel>) getIntent().getSerializableExtra("SONGS");

        setMusicWithResources();

        SongPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTimeTextView.setText(convertTime(Integer.toString(mediaPlayer.getCurrentPosition())));

                    if (mediaPlayer.isPlaying()) {
                        playPauseImageView.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                    } else {
                        playPauseImageView.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                    }
                }

                new Handler().postDelayed(this, 100);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer!= null && fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    protected void setMusicWithResources() {
        currentSong = songsList.get(SongPlayer.currentIndex);
        songNameTextView.setText(currentSong.getTitle());
        totalTimeTextView.setText(convertTime(currentSong.getDuration()));
        playPauseImageView.setOnClickListener(v -> playPause());
        nextSongImageView.setOnClickListener(v -> playNextSong());
        previousSongImageView.setOnClickListener(v -> playPreviousSong());
        playSong();
    }

    @SuppressLint("DefaultLocale")
    public static String convertTime(String duration) {
        long milliSec = Long.parseLong(duration);

        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliSec) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(milliSec) % TimeUnit.MINUTES.toSeconds(1));
    }

    private void playSong() {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playNextSong() {
        if (SongPlayer.currentIndex == songsList.size() - 1) {
            return;
        }
        SongPlayer.currentIndex += 1;
        mediaPlayer.reset();
        setMusicWithResources();
    }

    private void playPreviousSong() {
        if (SongPlayer.currentIndex == 0) {
            return;
        }
        SongPlayer.currentIndex -= 1;
        mediaPlayer.reset();
        setMusicWithResources();
    }

    private void playPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        unregisterReceiver(lightAndWalkChangedReceiver);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(lightAndWalkChangedReceiver);
    }
}