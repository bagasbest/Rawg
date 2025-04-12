package com.project.rawg

import android.app.Application
import com.project.core.di.databaseModule
import com.project.core.di.networkModule
import com.project.core.di.repositoryModule
import com.project.rawg.di.useCaseModule
import com.project.rawg.di.viewModelModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@ExperimentalCoroutinesApi
@FlowPreview
class MyApplication: Application() {

    companion object {
        lateinit var appContext: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }

}