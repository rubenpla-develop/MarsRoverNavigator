package com.rpla.marsrovernavigator.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providesContext(
        @ApplicationContext context: Context,
    ) = context

    @Provides
    fun providesDispatcher() = Dispatchers.IO
}
