package com.rodrigorods.mockapi

import android.app.Application
import com.rodrigorods.event.module.eventLisApiModule
import com.rodrigorods.event.module.eventListDomainModule
import com.rodrigorods.event.module.eventListUiModule
import com.rodrigorods.event.module.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin() {
            androidContext(this@MyApplication)
            modules(listOf(
                eventListUiModule,
                eventListDomainModule,
                eventLisApiModule,
                networkModule
            ))
        }
    }

}