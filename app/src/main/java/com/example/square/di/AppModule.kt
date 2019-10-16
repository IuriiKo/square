package com.example.square.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
interface AppModule {

    @Binds
    fun provideAppContect(application: Application): Context
}
