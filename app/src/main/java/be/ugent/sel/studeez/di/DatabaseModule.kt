package be.ugent.sel.studeez.di

import be.ugent.sel.studeez.domain.*
import be.ugent.sel.studeez.domain.implementation.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {
    @Binds
    abstract fun provideAccountDAO(impl: FirebaseAccountDAO): AccountDAO

    @Binds
    abstract fun provideUserDAO(impl: FirebaseUserDAO): UserDAO

    @Binds
    abstract fun provideTimerDAO(impl: FirebaseTimerDAO): TimerDAO

    @Binds
    abstract fun provideLogService(impl: LogServiceImpl): LogService

    @Binds
    abstract fun provideConfigurationService(impl: FirebaseConfigurationService): ConfigurationService

    @Binds
    abstract fun provideSessionDAO(impl: FireBaseSessionDAO): SessionDAO

    @Binds
    abstract fun provideSubjectDAO(impl: FireBaseSubjectDAO): SubjectDAO

    @Binds
    abstract fun provideTaskDAO(impl: FireBaseTaskDAO): TaskDAO
}