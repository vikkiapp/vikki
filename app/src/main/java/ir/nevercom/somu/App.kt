package ir.nevercom.somu

import android.app.Application
import ir.nevercom.somu.di.dataModule
import ir.nevercom.somu.di.networkModule
import ir.nevercom.somu.di.uiModule
import ir.nevercom.somu.model.ModelPreferencesManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(dataModule, networkModule, uiModule))
        }
    }
}