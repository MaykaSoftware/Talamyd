package com.mayka.talamyd

import android.app.Application
import com.mayka.talamyd.di.initKoin
import org.koin.android.ext.koin.androidContext

class TalamydApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@TalamydApplication)
        }
    }
}
