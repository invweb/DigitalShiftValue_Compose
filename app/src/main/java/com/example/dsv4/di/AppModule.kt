package com.example.dsv4.di

import com.example.dsv4.data.repository.CounterRepository
import com.example.dsv4.data.repository.CounterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindCounterRepository(
        impl: CounterRepositoryImpl
    ): CounterRepository
}
