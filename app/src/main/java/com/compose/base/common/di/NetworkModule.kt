package com.compose.base.common.di

import android.content.Context
import com.compose.base.BuildConfig
import com.compose.base.common.constants.Constants
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Good for debugging
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        applicationInterceptor: AuthorizationInterceptor,
        @ApplicationContext context: Context,
    ): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor(applicationInterceptor)
            .connectTimeout(Constants.App.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.App.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.App.DEFAULT_TIMEOUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            client.addInterceptor(httpLoggingInterceptor)
        }

        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.HOST_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    // No API service provided here yet, as each feature will provide its own.
    // If you had a truly common API service, you'd provide it here.
}
