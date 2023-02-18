package com.rabotyagi.onboarding.hackaton.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rabotyagi.onboarding.hackaton.schedule.AppSchedulers
import com.rabotyagi.onboarding.hackaton.schedule.SchedulersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Date
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideSchedulers(): SchedulersProvider = AppSchedulers()

    @Provides
    @Singleton
    fun provideGson(): Gson = with(GsonBuilder()) {
        serializeNulls()
        create()
    }

}