package com.compose.base.common.di

import android.content.Context
import com.compose.base.common.helper.DataStoreClient
import com.compose.base.common.helper.LocalDataManager
import com.compose.base.common.helper.ResourceProvider
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class) // Dependencies provided here live as long as the application
object AppModule {

    @Provides
    @Singleton
    fun provideLDataStoreClient(@ApplicationContext context: Context): DataStoreClient {
        return DataStoreClient(context)
    }

    @Provides
    @Singleton
    fun provideLocalDataManager(dataStore: DataStoreClient): LocalDataManager {
        return LocalDataManager(dataStore)
    }

    @Provides
    @Singleton
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider {
        return ResourceProvider(context)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    // You can add other app-wide dependencies here, like a database instance (Room)
    // or other global managers.
}
