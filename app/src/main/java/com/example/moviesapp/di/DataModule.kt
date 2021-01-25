package com.example.moviesapp.di

import com.example.moviesapp.data.MoviesDataRepository
import com.example.moviesapp.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
abstract class DataModule {

    @Binds
    abstract fun MoviesDataRepository.binds(): MoviesRepository
}