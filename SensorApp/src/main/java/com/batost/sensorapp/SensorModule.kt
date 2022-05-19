package com.batost.sensorapp

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SensorModule {

    @Provides
    @Singleton
    @Named("LightSensor")
    fun provideLightSensor(app: Application): MeasurableSensor {
        return LightSensor(app)
    }

    @Provides
    @Singleton
    @Named("AccSensor")
    fun provideAccSensor(app: Application): MeasurableSensor {
        return AccSensor(app)
    }
}