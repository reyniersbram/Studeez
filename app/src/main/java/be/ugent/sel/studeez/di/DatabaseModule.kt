package be.ugent.sel.studeez.di

import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.UserDAO
import be.ugent.sel.studeez.domain.implementation.FirebaseAccountDAO
import be.ugent.sel.studeez.domain.implementation.FirebaseUserDAO
import be.ugent.sel.studeez.domain.implementation.LogServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {
    @Binds abstract fun provideAccountDAO(impl: FirebaseAccountDAO): AccountDAO

    @Binds abstract fun provideUserDAO(impl: FirebaseUserDAO): UserDAO

    @Binds abstract fun provideLogService(impl: LogServiceImpl): LogService

}