package com.batost.sensorapp

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.batost.sensorapp.ui.theme.SensorAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SensorAppTheme {
                val viewModel = viewModel<MainViewModel>()
                val isDark = viewModel.isDark
                val isWalking = viewModel.isWalking
                val intent = Intent()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            if (isDark) Color.DarkGray else Color.White
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isDark && isWalking) {
                            "It's dark outside and you're walking"
                        } else if (isDark && !isWalking) {
                            "It's just dark outside"
                        } else if (!isDark && isWalking) {
                            "It's bright outside and you're walking"
                        } else {
                            "It's just bright outside"
                        },
                        color = if (isDark) Color.White else Color.DarkGray
                    )
                }

                if (isWalking) {
                    intent.apply {
                        action = "com.batost.sensorapp"
                        putExtra("Walk", true)
                        putExtra("Light", false) //This value could be true too. If walk is ture light does not matter
                        addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                        setComponent(ComponentName("com.batost.musicPlayer", "com.batost.musicPlayer.LightAndWalkChangedReceiver"))
                    }
                    sendBroadcast(intent)
                } else if (!isDark) {
                    intent.apply {
                        action = "com.batost.sensorapp"
                        putExtra("Walk", false)
                        putExtra("Light", false)
                        addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                        setComponent(ComponentName("com.batost.musicPlayer", "com.batost.musicPlayer.LightAndWalkChangedReceiver"))
                    }
                    sendBroadcast(intent)
                }
            }
        }


    }
}