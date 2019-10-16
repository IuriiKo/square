package com.example.square.di.square

import com.example.square.ui.SquareRepositoriesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [SquareRepositoriesModule::class])
abstract class SquareRepositoriesActivityModule {

    @ContributesAndroidInjector
    abstract fun bindRepositoriesActivity(): SquareRepositoriesActivity
}