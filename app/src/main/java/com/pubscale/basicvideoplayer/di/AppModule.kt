package com.pubscale.basicvideoplayer.di

import android.content.Context
import com.google.gson.Gson
import com.pubscale.basicvideoplayer.data.api.JsonDataSource
import com.pubscale.basicvideoplayer.data.repository.VideoRepository
import com.pubscale.basicvideoplayer.ui.player.PipHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides dependencies for the application.
 * All dependencies are provided as singletons.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provides the PiP helper
    @Provides
    @Singleton
    fun providePipHelper() = PipHelper()
    
    // Provides Gson for JSON parsing
    @Provides
    @Singleton
    fun provideGson() = Gson()
    
    // Provides the data source for JSON operations
    @Provides
    @Singleton
    fun provideJsonDataSource(
        @ApplicationContext context: Context,
        gson: Gson
    ) = JsonDataSource(context, gson)
    
    // Provides the repository to access video data
    @Provides
    @Singleton
    fun provideVideoRepository(jsonDataSource: JsonDataSource) = 
        VideoRepository(jsonDataSource)
} 