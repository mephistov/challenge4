package com.nicolascastilla.challenge4.di

import android.app.Application
import android.content.Context
import com.nicolascastilla.challenge4.utils.ChallengeAlarmManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object AndroidModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideChallengeAlarmManager(context: Context): ChallengeAlarmManager {
        return ChallengeAlarmManager(context)
    }
}