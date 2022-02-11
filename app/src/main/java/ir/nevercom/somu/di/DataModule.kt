package ir.nevercom.somu.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.nevercom.somu.repositories.SharedPreferencesUserRepositoryImpl
import ir.nevercom.somu.repositories.UserRepository
import ir.nevercom.somu.repositories.VikkiDatabase
import ir.nevercom.somu.repositories.WatchlistRepository
import ir.nevercom.somu.repositories.local.LocalWatchlistRepository
import ir.nevercom.somu.repositories.local.MovieDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {

    @Binds
    abstract fun provideUserRepository(binds: SharedPreferencesUserRepositoryImpl): UserRepository

    @Binds
    abstract fun provideWatchlistRepository(binds: LocalWatchlistRepository): WatchlistRepository

    companion object {
        @Singleton
        @Provides
        fun provideDatabase(@ApplicationContext appContext: Context): VikkiDatabase {
            return Room.databaseBuilder(
                appContext,
                VikkiDatabase::class.java, "vikki"
            ).build()
        }

        @Provides
        fun provideMovieDao(database: VikkiDatabase): MovieDao = database.movieDao()
    }
}