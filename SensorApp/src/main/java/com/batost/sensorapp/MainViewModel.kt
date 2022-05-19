package com.batost.sensorapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(
    @Named("LightSensor")
    private val lightSensor: MeasurableSensor,
    @Named("AccSensor")
    private val accSensor: MeasurableSensor
) : ViewModel() {

    var isDark by mutableStateOf(false)
    var isWalking by mutableStateOf(false)

    private var kalmanFilteringFactor = 0.6f
    private var magnitude = 0.0f

    private var gravity = arrayOf(0f, 0f, 0f)
    private var linear_acc = arrayOf(0f, 0f, 0f)

    init {
        lightSensor.startListening()
        lightSensor.setOnSensorValuesChangedListener { values ->
            val lux = values[0]
            isDark = lux < 60f
        }

        accSensor.startListening()
        accSensor.setOnSensorValuesChangedListener { values1 ->
            val fx = values1[0]
            val fy = values1[1]
            val fz = values1[2]

            gravity[0] =
                (fx * kalmanFilteringFactor) + (gravity[0] * (1.0f - kalmanFilteringFactor))
            gravity[1] =
                (fy * kalmanFilteringFactor) + (gravity[1] * (1.0f - kalmanFilteringFactor))
            gravity[2] =
                (fz * kalmanFilteringFactor) + (gravity[2] * (1.0f - kalmanFilteringFactor))

            linear_acc[0] = fx - gravity[0]
            linear_acc[1] = fy - gravity[1]
            linear_acc[2] = fz - gravity[2]

            magnitude = Math.sqrt(
                (linear_acc[0] * linear_acc[0]).toDouble() +
                        (linear_acc[1] * linear_acc[1]).toDouble() +
                        (linear_acc[2] * linear_acc[2]).toDouble()
            ).toFloat()

            isWalking = magnitude > 0.2f
        }
    }
}