package com.aks4125.gorealm

import android.app.Application
import com.aks4125.gorealm.di.applicationModule
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        //koin configuration
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(listOf(applicationModule)) //will add more modules later on
        }

        // realm configuration
        Realm.init(this)
        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder()
                .name("companies")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(2)
                .compactOnLaunch().build()
        )


    }
}