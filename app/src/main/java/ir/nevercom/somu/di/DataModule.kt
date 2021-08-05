package ir.nevercom.somu.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.nevercom.somu.repositories.SharedPreferencesUserRepositoryImpl
import ir.nevercom.somu.repositories.UserRepository

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {

    @Binds
    abstract fun provideUserRepository(binds: SharedPreferencesUserRepositoryImpl): UserRepository
}