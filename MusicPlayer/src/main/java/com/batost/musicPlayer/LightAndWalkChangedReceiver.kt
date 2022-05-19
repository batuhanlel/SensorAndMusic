package com.batost.musicPlayer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi

class LightAndWalkChangedReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onReceive(context: Context?, intent: Intent?) {
        val audio = context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val minVolume = audio.getStreamMinVolume(AudioManager.STREAM_MUSIC)

        val isWalking = intent?.getBooleanExtra("Walk", true) ?: return
        val isDark = intent.getBooleanExtra("Light", true)

        if (isWalking) {
            audio.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0)
            Toast.makeText(context, "Volume up", Toast.LENGTH_LONG).show()
        } else if (!isDark) {
            audio.setStreamVolume(AudioManager.STREAM_MUSIC, minVolume, 0)
            Toast.makeText(context, "Volume down", Toast.LENGTH_LONG).show()
        }
    }

}