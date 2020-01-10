package com.aks4125.gorealm

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
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