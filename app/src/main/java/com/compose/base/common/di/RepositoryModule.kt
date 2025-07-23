package com.compose.base.common.di

import com.compose.base.data.repository.AuthRepository
import com.compose.base.data.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class) // Or SingletonComponent if you want AuthRepository to be app-wide
abstract class RepositoryModule {
    @Binds
    @ViewModelScoped // Scope AuthRepository to the ViewModel lifetime
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}
