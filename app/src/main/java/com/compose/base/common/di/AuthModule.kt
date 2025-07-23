package com.compose.base.common.di

import com.compose.base.data.remote.AuthApiService
import com.compose.base.data.repository.AuthRepository
import com.compose.base.data.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class) // Or SingletonComponent if you want AuthRepository to be app-wide
object AuthModule {
    @Provides
    @ViewModelScoped // Scope AuthApiService to the ViewModel lifetime
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @ViewModelScoped // Scope AuthRepository to the ViewModel lifetime
    fun provideAuthRepository(apiService: AuthApiService): AuthRepository {
        return AuthRepositoryImpl(apiService)
    }
}
