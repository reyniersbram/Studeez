package be.ugent.sel.studeez.di

import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.implementation.FirebaseAccountDAO
import be.ugent.sel.studeez.domain.implementation.LogServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {
    @Binds abstract fun provideAccountService(impl: FirebaseAccountDAO): AccountDAO

    @Binds abstract fun provideLogService(impl: LogServiceImpl): LogService

}