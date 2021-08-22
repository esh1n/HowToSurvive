package com.esh1n.guidtoarchapp.presentation.di

import com.esh1n.guidtoarchapp.data.PreferenceRepository
import com.esh1n.guidtoarchapp.domain.AuthRepositoryImpl
import com.esh1n.guidtoarchapp.domain.IAuthRepo
import com.esh1n.guidtoarchapp.domain.IPrefsRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthRepo(authRepo: AuthRepositoryImpl): IAuthRepo

    @Binds
    abstract fun bindPrefsRepo(prefsRepo: PreferenceRepository): IPrefsRepo
}