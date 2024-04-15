package com.rizrmdhn.kankerdetection

import android.app.Application
import com.rizrmdhn.kankerdetection.di.databaseModule
import com.rizrmdhn.kankerdetection.di.repositoryModule
import com.rizrmdhn.kankerdetection.di.useCaseModule
import com.rizrmdhn.kankerdetection.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    useCaseModule,
                    viewModelModule,
                    databaseModule,
                    repositoryModule,
                )
            )
        }
    }
}