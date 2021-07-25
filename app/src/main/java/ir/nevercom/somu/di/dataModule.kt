package ir.nevercom.somu.di

import ir.nevercom.somu.repositories.SharedPreferencesUserRepositoryImpl
import ir.nevercom.somu.repositories.UserRepository
import org.koin.dsl.module

val dataModule = module {
    single<UserRepository> { SharedPreferencesUserRepositoryImpl() }
}